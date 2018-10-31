package yich.base.predicate;


import yich.base.dbc.Require;

import java.util.function.Function;

abstract public class ContentPredicate<T> extends AbsPredicate<T> {
    private Function<T, String> contentSelector;


    public ContentPredicate(String name) {
        super(name);
        init();
    }

    public ContentPredicate(String name, int level) {
        super(name, level);
        init();
    }

    private void init() {
        contentSelector = this::defaultContentSelector;
    }

    public Function<T, String> getContentSelector() {
        return contentSelector;
    }

    public ContentPredicate setContentSelector(Function<T, String> contentSelector) {
        Require.argumentNotNull(contentSelector);
        this.contentSelector = contentSelector;
        return this;
    }

    abstract protected String defaultContentSelector(T target);

}
