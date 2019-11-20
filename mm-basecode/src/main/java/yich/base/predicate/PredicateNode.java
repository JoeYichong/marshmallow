package yich.base.predicate;


import yich.base.dbc.Require;
import yich.base.logging.JUL;
import yich.base.util.StrUtil;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.logging.Logger;

abstract public class PredicateNode<T> extends AbsPredicate<T> {
    final private static Logger logger = JUL.getLogger(PredicateNode.class);

    private List<AbsPredicate<T>> predicates;

    private BiFunction<T, List<AbsPredicate<T>>, Boolean> combiner;

    public PredicateNode(String name) {
        super(name);
        init();
    }

    public PredicateNode(String name, int level) {
        super(name, level);
        init();
    }

    private void init() {
        predicates = new CopyOnWriteArrayList<>();
        setCombinerAnd();
    }

    public PredicateNode<T> setCombinerAnd() {
        combiner = this::andPredicates;
        return this;
    }

    public PredicateNode<T> setCombinerOr() {
        combiner = this::orPredicates;
        return this;
    }

    public BiFunction<T, List<AbsPredicate<T>>, Boolean> getCombiner() {
        return combiner;
    }

    public PredicateNode<T> setCombiner(BiFunction<T, List<AbsPredicate<T>>, Boolean> combiner) {
        Require.argumentNotNull(combiner);
        this.combiner = combiner;
        return this;
    }

    public PredicateNode<T> addAbsPredicate(AbsPredicate<T>... filters) {
        Require.argumentNotNullAndNotEmpty(filters, "AbsPredicate<T>... predicates");
        for (AbsPredicate<T> filter : filters) {
            if (filter != null) {
                this.predicates.add(filter);
            }
        }
        return this;
    }

    public PredicateNode<T> addPredicate(Predicate<T>... filters) {
        Require.argumentNotNullAndNotEmpty(filters, "Predicate<T>... predicates");
        for (Predicate<T> filter : filters) {
            if (filter != null) {
                this.predicates.add(AbsPredicate.of("Predicate@"
                                      + StrUtil.randomAlphaNumeric(10), filter));
            }
        }
        return this;
    }


    public PredicateNode<T> addPredicate(String name, Predicate<T> predicate) {
        Require.argumentNotNullAndNotEmpty(name, "String name");
        Require.argumentNotNull(predicate, "Predicate<T> predicate");
        predicates.add(AbsPredicate.of(name, predicate));
        return this;
    }

    public PredicateNode<T> addPredicate(String name, int level, Predicate<T> predicate) {
        Require.argumentNotNullAndNotEmpty(name, "String name");
        Require.argumentNotNull(predicate, "Predicate<T> predicate");
        predicates.add(AbsPredicate.of(name, level, predicate));
        return this;
    }

    public PredicateNode<T> removePredicate(AbsPredicate<T> predicate) {
        Require.argumentNotNull(predicate);
        predicates.remove(predicate);
        return this;
    }

    public PredicateNode<T> removePredicate(int index) {
        if (index >= 0 && index < predicates.size()) {
            predicates.remove(index);
        }
        return this;
    }

    public PredicateNode<T> removePredicate(String name) {
        Require.argumentNotNullAndNotEmpty(name, "String name");
        Iterator<T> iterator = (Iterator<T>) predicates.iterator();
        AbsPredicate<T> predicate;
        while (iterator.hasNext()) {
            predicate = (AbsPredicate<T>) iterator.next();
            if (predicate.getName().equals(name)) {
                iterator.remove();
            }
        }
        return this;
    }

    public boolean containPredicate(String name) {
        Require.argumentNotNull(name);
        return predicates.stream().anyMatch(predicate -> name.equals(predicate.getName()));
    }

    public PredicateNode<T> clearPredicates() {
        predicates.clear();
        return this;
    }


//    public Predicate<T> accumulate(BinaryOperator<Predicate<T>> accumulator) {
//        Require.argumentNotNull(accumulator);
//        return predicates.stream()
//                .map(predicate -> (Predicate<T>) predicate)
//                .reduce(accumulator).orElse(x->true);
//    }

    abstract protected String getHint(T target);

    private boolean andPredicates(T target, List<AbsPredicate<T>> filters) {
        for (AbsPredicate<T> filter : filters) {
            // block target if filter type is BLOCK & target matches
            if (filter.getType() == PredicateAction.BLOCK) {
                if (filter.test(target)) {
                    logger.info("** Target " + getHint(target) + " Rejected..");
                    return false;
                } else {
                    continue;
                }
            } else if (!filter.test(target)) {
                return false;
            }
        }
        return true;
    }

    private boolean orPredicates(T target, List<AbsPredicate<T>> filters) {
        for (AbsPredicate<T> filter : filters) {
            // block target if filter type is BLOCK & target matches
            if (filter.getType() == PredicateAction.BLOCK) {
                if (filter.test(target)) {
                    logger.info("** Target " + getHint(target) + " Rejected..");
                    return false;
                } else {
                    continue;
                }
            } else if (filter.test(target)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean test(T target) {
        return combiner.apply(target, predicates);
    }

}
