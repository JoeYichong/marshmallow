package yich.base.topic;

import yich.base.predicate.AbsPredicate;
import yich.base.predicate.WeightAppender;

public class TopicWeightAdjustor extends WeightAppender<Topic> {
    public TopicWeightAdjustor(String name, AbsPredicate<Topic> filter) {
        super(name, filter);
    }

    public TopicWeightAdjustor(String name, int level, AbsPredicate<Topic> filter) {
        super(name, level, filter);
    }

    @Override
    protected void addWeight(Topic target, int weight) {

    }
}
