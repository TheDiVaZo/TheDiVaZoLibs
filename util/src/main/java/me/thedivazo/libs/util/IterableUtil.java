package me.thedivazo.libs.util;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class IterableUtil {
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
}
