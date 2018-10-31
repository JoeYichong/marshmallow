package yich.base.predicate;

import yich.base.dbc.Require;
import yich.base.logging.JUL;

import java.util.function.Predicate;
import java.util.logging.Logger;

abstract public class AltPredicate<T> extends AbsPredicate<T>{
    final private static Logger logger = JUL.getLogger(AltPredicate.class);

    public AltPredicate(String name) {
        super(name);
    }

    public AltPredicate(String name, int level) {
        super(name, level);
    }

    public static <T> AltPredicate<T> of(String name, Predicate<T> predicate) {
        Require.argumentNotNull(predicate, "Predicate<T> predicate");
        return new AltPredicate<T>(name) {
            @Override
            public Object altTest(T t) {
                return predicate.test(t) ? "" : null;
            }
        };
    }

    public static <T> AltPredicate<T> of(String name, int level, Predicate<T> predicate) {
        Require.argumentNotNull(predicate, "Predicate<T> predicate");
        return (new AltPredicate<T>(name, level) {
            @Override
            public Object altTest(T t) {
                return predicate.test(t) ? "" : null;
            }
        });
    }

    abstract protected Object altTest(T target);

    @Override
    public boolean test(T target) {
        Object re = altTest(target);
        if (re == null) {
            return false;
        } else if (!"".equals(String.valueOf(re))){
            logger.info("** " + String.valueOf(re));
        }
        return true;
    }
}
