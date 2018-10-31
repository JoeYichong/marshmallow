package yich.base.predicate;


import yich.base.dbc.Require;

abstract public class WeightAppender<T> extends AbsPredicate<T> {

    private int weight = 0;

    private AbsPredicate<T> corePredicate;

    public WeightAppender(String name, AbsPredicate<T> filter) {
        super(name);
        setCorePredicate(filter);
    }

    public WeightAppender(String name, int level, AbsPredicate<T> filter) {
        super(name, level);
        setCorePredicate(filter);
    }

    public int getWeight() {
        return weight;
    }

    public WeightAppender<T> setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public AbsPredicate<T> getCorePredicate() {
        return corePredicate;
    }

    public WeightAppender<T> setCorePredicate(AbsPredicate<T> filter) {
        Require.argumentNotNull(filter, "Filter<T> filter");
        this.corePredicate = filter;
        return this;
    }

    abstract protected void addWeight(T target, int weight);

    private void addWeight(T target) {
        addWeight(target, this.weight);
    }

    private void match(T target) {
        if (corePredicate.test(target)) {
            addWeight(target);
        }
//        for (String keyword : getKeywords()) {
//            if (getContentSelector().apply(target).contains(keyword)) {
//                addWeight(target);
//                break;
//            }
//        }
    }

    @Override
    public boolean test(T target) {
        match(target);
        return true;
    }

}
