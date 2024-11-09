package me.thedivazo.libs.util.function;

import java.util.Objects;

/**
 * @author TheDiVaZo
 * created on 08.11.2024
 **/
public interface UncheckedPredicate<T> {
    boolean test(T t) throws Exception;

    default UncheckedPredicate<T> and(UncheckedPredicate<? super T> other) {
        Objects.requireNonNull(other);
        return t -> test(t) && other.test(t);
    }


    default UncheckedPredicate<T> negate() {
        return t -> !test(t);
    }

    default UncheckedPredicate<T> or(UncheckedPredicate<? super T> other) {
        Objects.requireNonNull(other);
        return t -> test(t) || other.test(t);
    }

    static <T> UncheckedPredicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : targetRef::equals;
    }
}
