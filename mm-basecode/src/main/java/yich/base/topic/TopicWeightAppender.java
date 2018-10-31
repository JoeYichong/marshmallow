package yich.base.topic;


import yich.base.logging.JUL;
import yich.base.predicate.AbsPredicate;
import yich.base.predicate.WeightAppender;

import java.util.logging.Logger;

public class TopicWeightAppender extends WeightAppender<Topic> {
    private static Logger logger = JUL.getLogger(TopicWeightAppender.class);

    private boolean overwrite = false;

    public TopicWeightAppender(String name, AbsPredicate<Topic> filter) {
        super(name, filter);
    }

    public TopicWeightAppender(String name, int level, AbsPredicate<Topic> filter) {
        super(name, level, filter);
    }

    @Override
    public TopicWeightAppender setWeight(int weight) {
        super.setWeight(weight);
        return this;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public TopicWeightAppender setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
        return this;
    }

    @Override
    protected void addWeight(Topic topic, int weight) {
        if (topic != null && (overwrite || topic.getWeight() < weight)) {
            topic.setWeight(weight);
            logger.info("'" + topic.getUrl() + "' set weight to '" + weight + "'");
        }
    }

}
