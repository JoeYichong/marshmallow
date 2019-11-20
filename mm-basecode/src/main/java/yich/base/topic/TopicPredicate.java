package yich.base.topic;


import yich.base.predicate.*;

import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.regex.Pattern;

public class TopicPredicate extends PredicateNode<Topic> {

    public TopicPredicate(String name) {
        super(name);
    }

    public TopicPredicate(String name, int level) {
        super(name, level);
    }

    @Override
    protected String getHint(Topic topic) {
        return topic == null ? null : topic.getUrl();
    }

    public class TopicKeywordFilter extends KeywordPredicate<Topic> {
        public TopicKeywordFilter(String name, int level) {
            super(name, level);
        }

        public TopicKeywordFilter keywords(String... keywords) {
            super.addKeyword(keywords);
            return this;
        }

        @Override
        public TopicKeywordFilter setLevel(int level) {
            super.setLevel(level);
            return this;
        }

        @Override
        public TopicKeywordFilter setType(PredicateAction type) {
            super.setType(type);
            return this;
        }

        @Override
        public TopicKeywordFilter setContentSelector(Function<Topic, String> contentSelector) {
            super.setContentSelector(contentSelector);
            return this;
        }

        public TopicKeywordFilter setSelectorToTitle() {
            setContentSelector(this::defaultContentSelector);
            return this;
        }

        public TopicKeywordFilter setSelectorToContent() {
            setContentSelector(topic -> topic == null ? null : topic.getPostContent());
            return this;
        }

        @Override
        protected String defaultContentSelector(Topic topic) {
            return topic == null ? null : topic.getPostTitle();
        }

        public TopicPredicate add() {
            TopicPredicate.this.addAbsPredicate(TopicKeywordFilter.this);
            return TopicPredicate.this;
        }
    }

    public TopicKeywordFilter keywordFilter() {
        return new TopicKeywordFilter("keyword-filter", 0);
    }

    public class TopicPatternFilter extends PatternPredicate<Topic> {
        public TopicPatternFilter(String name, int level) {
            super(name, level);
        }

        public TopicPatternFilter patterns(Pattern... patterns) {
            super.addPattern(patterns);
            return this;
        }

        public TopicPatternFilter patterns(String... regs) {
            super.addPattern(regs);
            return this;
        }

        @Override
        public TopicPatternFilter setLevel(int level) {
            super.setLevel(level);
            return this;
        }

        @Override
        public TopicPatternFilter setType(PredicateAction type) {
            super.setType(type);
            return this;
        }

        @Override
        public TopicPatternFilter setContentSelector(Function<Topic, String> contentSelector) {
            super.setContentSelector(contentSelector);
            return this;
        }

        public TopicPatternFilter setSelectorToTitle() {
            setContentSelector(this::defaultContentSelector);
            return this;
        }

        public TopicPatternFilter setSelectorToContent() {
            setContentSelector(topic -> topic == null ? null : topic.getPostContent());
            return this;
        }

        @Override
        protected String defaultContentSelector(Topic topic) {
            return topic == null ? null : topic.getPostTitle();
        }

        public TopicPredicate add() {
            TopicPredicate.this.addAbsPredicate(TopicPatternFilter.this);
            return TopicPredicate.this;
        }
    }

    public TopicPatternFilter patternFilter() {
        return new TopicPatternFilter("pattern-filter", 0);
    }

    public class TopicTimeFilter extends TimePredicate<Topic> {
        public TopicTimeFilter(String name, int level) {
            super(name, level);
        }

        @Override
        public TopicTimeFilter setTime(String start, String end) {
            super.setTime(start, end);
            return this;
        }

        @Override
        public TopicTimeFilter setTime(LocalDateTime start, LocalDateTime end) {
            super.setTime(start, end);
            return this;
        }

        @Override
        protected Object getTargetTime(Topic topic) {
            return topic == null ? null : topic.getPostTime();
        }

        @Override
        public TopicTimeFilter setLevel(int level) {
            super.setLevel(level);
            return this;
        }

        @Override
        public TopicTimeFilter setType(PredicateAction type) {
            super.setType(type);
            return this;
        }

        public TopicPredicate add() {
            TopicPredicate.this.addAbsPredicate(TopicTimeFilter.this);
            return TopicPredicate.this;
        }
    }

    public TopicTimeFilter timeFilter() {
        return new TopicTimeFilter("time-filter", 0);
    }

    public static TopicPredicate newFilter(String name) {
        return new TopicPredicate(name);
    }

    public static void main(String[] args) {
        TopicPredicate filter = TopicPredicate.newFilter("topic-filter");
          filter.keywordFilter()
                 .keywords("A", "B", "C")
                 .add()
                .keywordFilter()
                 .keywords("D", "E", "F")
                 .setType(PredicateAction.BLOCK)
                 .setLevel(0)
                 .add()
                .patternFilter()
                  .patterns(".*?hello.*?")
                  .add()
                .timeFilter()
                  .setTime("2018-01-01", "2018-09-01")
                  .add();


    }

}
