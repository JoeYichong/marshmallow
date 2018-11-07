package yich.base.topic;


import yich.base.dbc.Require;
import yich.base.logging.JUL;
import yich.base.predicate.AbsPredicate;
import yich.base.predicate.WeightAppender;

import java.util.function.Predicate;
import java.util.logging.Logger;

public class TopicWeightAppender extends WeightAppender<Topic> {
    private static Logger logger = JUL.getLogger(TopicWeightAppender.class);

    private Predicate<Topic> overwrite = null;

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

    public boolean isOverwrite(Topic topic) {
        return overwrite == null ? false : overwrite.test(topic);
    }

    public TopicWeightAppender setOverwrite(boolean overwrite) {
        this.overwrite = topic -> true;
        return this;
    }

    public TopicWeightAppender setOverwrite(Predicate<Topic> predicate) {
        Require.argumentNotNull(predicate);
        this.overwrite = predicate;
        return this;
    }

    @Override
    protected void addWeight(Topic topic, int weight) {
        if (topic == null) {
            logger.warning("** Topic object is null...");
            return;
        }
        if (isOverwrite(topic) || topic.getWeight() < weight) {
            topic.setWeight(weight);
            logger.info("** '" + topic.getUrl() + "' set weight to '" + weight + "'");
        } else {
            logger.info("** '" + topic.getUrl() + "'(weight=" + topic.getWeight() +
                    ") failed to set weight to '" + weight + "'(original weight cannot be overwritten).");
        }
    }



}
