package me.thedivazo.libs.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IterableUtil {
    public static final Iterable<?> EMPY_ITERABLE = new EmptyIterable<>();

    @SuppressWarnings("unchecked")
    public static <T> Iterable<T> emptyIterator() {
        return (Iterable<T>) EMPY_ITERABLE;
    }

    public static <T> Collection<T> toCollection(Iterable<T> iterable) {
        return toCollection(iterable, false);
    }

    public static <T> Collection<T> toCollection(Iterable<T> iterable, boolean isParallel) {
        return toCollection(iterable, isParallel, Collectors.toList());
    }

    public static <T, C extends Collection<T>> C toCollection(Iterable<T> iterable, Collector<T, ?, C> collector) {
        return toCollection(iterable, false, collector);
    }

    public static <T, C extends Collection<T>> C toCollection(Iterable<T> iterable, boolean isParallel, Collector<T, ?, C> collector) {
        return toStream(iterable, isParallel).collect(collector);
    }

    public static <T> Stream<T> toStream(Iterable<T> iterable) {
        return toStream(iterable, false);
    }

    public static <T> Stream<T> toStream(Iterable<T> iterable, boolean isParallel) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static  <T> T[] toArray(Iterable<? extends T> iterable, IntFunction<T[]> generator) {
        List<T> list = new LinkedList<>();
        iterable.forEach(list::add);
        return list.toArray(generator);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Iterable<? extends T> iterable, Class<T> clazz) {
        return toArray(iterable, size-> (T[]) Array.newInstance(clazz, size));
    }
}
