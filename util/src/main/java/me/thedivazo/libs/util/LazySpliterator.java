package me.thedivazo.libs.util;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author TheDiVaZo
 * created on 08.11.2024
 **/
public class LazySpliterator<T> extends Spliterators.AbstractSpliterator<T> {
    protected final Predicate<Consumer<? super T>> tryAdvancer;

    public LazySpliterator(Predicate<Consumer<? super T>> tryAdvancer) {
        super(Long.MAX_VALUE, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE);
        this.tryAdvancer = tryAdvancer;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return tryAdvancer.test(action);
    }
}
