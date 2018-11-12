package yich.base.predicate;

import yich.base.logging.JUL;

import java.util.logging.Logger;

abstract public class AltContentPredicate<T> extends ContentPredicate<T> implements AltPredicate<T>{
    final private static Logger logger = JUL.getLogger(AltContentPredicate.class);

    public AltContentPredicate(String name) {
        super(name);
    }

    public AltContentPredicate(String name, int level) {
        super(name, level);
    }

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
