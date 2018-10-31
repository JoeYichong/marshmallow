package yich.base.predicate;


import yich.base.dbc.Require;
import yich.base.logging.JUL;

import java.util.function.Predicate;
import java.util.logging.Logger;

abstract public class AbsPredicate<T> implements Predicate<T> {
//    final private static Logger logger = JUL.getLogger(AbsPredicate.class);

    private String name;
    private int level = 0;
    private PredicateAction type = PredicateAction.ACCEPT;


//    public AbsPredicate() {
//        this.name = StrUtil.randomAlphaNumeric(10);
//    }

    public AbsPredicate(String name) {
        setName(name);
    }

    public AbsPredicate(String name, int level) {
        setName(name);
        setLevel(level);
    }


//    abstract protected boolean test0(T target);

//    @Override
//    public boolean test(T target) {
//        if (rule == FilterRule.ACCEPT) {
//            return test0(target);
//        } else if (rule == FilterRule.BLOCK) {
//            return !test0(target);
//        }
//        return false;
//    }

    public String getName() {
        return name;
    }

    public AbsPredicate setName(String name) {
        Require.argumentNotNullAndNotEmpty(name, "String name");
        this.name = name;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public AbsPredicate<T> setLevel(int level) {
        this.level = level;
        return this;
    }

    public PredicateAction getType() {
        return type;
    }

    public AbsPredicate<T> setType(PredicateAction type) {
        this.type = type;
        return this;
    }

//    public Filter<T> and(Filter<? super T> other) {
//        Require.argumentNotNull(other);
//        return new Filter<>() {
//            @Override
//            protected boolean test0(T target) {
//                return test(target) && other.test(target);
//            }
//        };
//    }
//
//    public Filter<T> or(Filter<? super T> other) {
//        Require.argumentNotNull(other);
//        return new Filter<>() {
//            @Override
//            protected boolean test0(T target) {
//                return test(target) || other.test(target);
//            }
//        };
//    }
//
//    public Filter<T> negate() {
//        return new Filter<>() {
//            @Override
//            protected boolean test0(T target) {
//                return !test(target);
//            }
//        };
//    }

//    public static void main(String[] args) {
//        Filter filter = o -> true;
//    }

    public static <T> AbsPredicate<T> of(String name, Predicate<T> predicate) {
        Require.argumentNotNull(predicate, "Predicate<T> predicate");
        return new AbsPredicate<T>(name) {
            @Override
            public boolean test(T t) {
                return predicate.test(t);
            }
        };
    }

    public static <T> AbsPredicate<T> of(String name, int level, Predicate<T> predicate) {
        Require.argumentNotNull(predicate, "Predicate<T> predicate");
        return (new AbsPredicate<T>(name, level) {
            @Override
            public boolean test(T t) {
                return predicate.test(t);
            }
        });
    }


}
