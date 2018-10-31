package yich.base.selenium.loader;

import java.util.function.Supplier;

public interface PageLoader<T> extends AutoCloseable, Supplier<T> {
    T page();

    String url();

    PageLoader<T> setUrl(String url);

}
