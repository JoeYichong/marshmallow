package yich.base.predicate;

import yich.base.logging.JUL;

import java.util.function.Predicate;
import java.util.logging.Logger;

abstract public class LengthPredicate<T> extends AltContentPredicate<T> {
    final private static Logger logger = JUL.getLogger(LengthPredicate.class);

    private Predicate<Integer> bounds = null;

    private String hintMsg = null;

    public LengthPredicate(String name) {
        super(name);
    }

    public LengthPredicate(String name, int level) {
        super(name, level);
    }

    public LengthPredicate(String name, Predicate<Integer> bounds) {
        super(name);
        this.bounds = bounds;
    }

    public LengthPredicate(String name, int level, Predicate<Integer> bounds) {
        super(name, level);
        this.bounds = bounds;
    }

    public Predicate<Integer> getBounds() {
        return bounds;
    }

    public LengthPredicate<T> setBounds(Predicate<Integer> bounds) {
        this.bounds = bounds;
        return this;
    }

    public String getHintMsg() {
        return hintMsg;
    }

    public LengthPredicate<T> setHintMsg(String hintMsg) {
        this.hintMsg = hintMsg;
        return this;
    }

    private String isWithinBounds(T target) {
        if (bounds == null) {
            return "";
        }

        String content = getContentSelector().apply(target);
        int len = content == null ? 0 : content.length();
        if (bounds.test(len)) {
            return null;
        } else {
            return "Length Out of Bounds"
                    + (hintMsg == null ? "" : ": " + hintMsg);
        }
    }

    @Override
    public Object altTest(T target) {
        return isWithinBounds(target);
    }



}
