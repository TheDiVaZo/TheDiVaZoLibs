package me.thedivazo.libs.util.function;

import java.util.Objects;

/**
 * @author TheDiVaZo
 * created on 08.11.2024
 **/
public interface CheckedPredicate<T, E extends Throwable> {
    boolean test(T t) throws E;

    default CheckedPredicate<T, E> and(CheckedPredicate<? super T, ? extends E> other) {
        Objects.requireNonNull(other);
        return t -> test(t) && other.test(t);
    }


    default CheckedPredicate<T, E> negate() {
        return t -> !test(t);
    }

    default CheckedPredicate<T, E> or(CheckedPredicate<? super T, ? extends E> other) {
        Objects.requireNonNull(other);
        return t -> test(t) || other.test(t);
    }

    static <T, E extends Throwable> CheckedPredicate<T, E> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : targetRef::equals;
    }
}
