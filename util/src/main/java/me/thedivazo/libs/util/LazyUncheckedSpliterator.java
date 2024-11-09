package me.thedivazo.libs.util;

import me.thedivazo.libs.util.function.UncheckedPredicate;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * @author TheDiVaZo
 * created on 08.11.2024
 **/
public class LazyUncheckedSpliterator<T> extends Spliterators.AbstractSpliterator<T> {
    protected final UncheckedPredicate<Consumer<? super T>> tryAdvancer;

    public LazyUncheckedSpliterator(UncheckedPredicate<Consumer<? super T>> tryAdvancer) {
        super(Long.MAX_VALUE, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE);
        this.tryAdvancer = tryAdvancer;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        try {
            return tryAdvancer.test(action);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
