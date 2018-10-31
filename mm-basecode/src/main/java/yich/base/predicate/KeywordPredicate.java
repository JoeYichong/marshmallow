package yich.base.predicate;


import yich.base.dbc.Require;
import yich.base.logging.JUL;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

abstract public class KeywordPredicate<T> extends ContentPredicate<T> {
    final private static Logger logger = JUL.getLogger(KeywordPredicate.class);

    private Set<String> keywords;

    public KeywordPredicate(String name) {
        super(name);
        init();
    }

    public KeywordPredicate(String name, int level) {
        super(name, level);
        init();
    }

    private void init() {
        keywords = new HashSet<>();
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public KeywordPredicate addKeyword(String... keywords) {
        Require.argumentNotNullAndNotEmpty(keywords, "String... keywords");
        for (String keyword : keywords) {
            if (keyword == null || keyword.length() == 0) {
                logger.warning("** Attempt to add 'null' value or empty keyword!");
                continue;
            }
            this.keywords.add(keyword);
        }
        return this;
    }

//    @Override
//    protected String defaultContentSelector(Topic topic) {
//        return topic == null ? null : topic.getPostTitle();
//    }

    public boolean isKeywordMatched(T target) {
        String content = getContentSelector().apply(target);
        if (content == null)
            return false;
        if (keywords.size() == 0)
            return true;
        for (String keyword : keywords) {
            if (content.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean test(T target) {
        return isKeywordMatched(target);
    }
}
