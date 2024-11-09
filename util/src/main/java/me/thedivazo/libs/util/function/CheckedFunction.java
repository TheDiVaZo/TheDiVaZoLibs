package me.thedivazo.libs.util.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author TheDiVaZo
 * created on 09.11.2024
 */
public interface CheckedFunction<T, R, E extends Throwable> {
    R apply(T t) throws E;

    default <V> CheckedFunction<V, R, E> compose(CheckedFunction<? super V, ? extends T, ? extends E> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    default <V> CheckedFunction<T, V, E> andThen(CheckedFunction<? super R, ? extends V, E> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    static <T, E extends Throwable> CheckedFunction<T, T, E> identity() {
        return t -> t;
    }
}
