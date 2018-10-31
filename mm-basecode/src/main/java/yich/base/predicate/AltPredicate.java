package yich.base.predicate;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface AltPredicate<T> {
    Object test(T t);

//    default AltPredicate<T> and(AltPredicate<? super T> other) {
//        Objects.requireNonNull(other);
//        return (t) -> test(t) && other.test(t);
//    }
//
//    default AltPredicate<T> negate() {
//        return (t) -> !test(t);
//    }
//
//    default AltPredicate<T> or(AltPredicate<? super T> other) {
//        Objects.requireNonNull(other);
//        return (t) -> test(t) || other.test(t);
//    }


}
