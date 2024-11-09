package me.thedivazo.libs.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author TheDiVaZo
 * created on 09.11.2024
 */
public class EmptyIterable<T> implements Iterable<T> {
    public static class EmptyIterator<T> implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            throw new NoSuchElementException("This is a empty iterator");
        }
    }

    private static final EmptyIterator<?> EMPTY_ITERATOR = new EmptyIterator<>();

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return (Iterator<T>) EMPTY_ITERATOR;
    }
}
