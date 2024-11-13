package me.thedivazo.libs.database.promise.callback;

import java.util.function.BiConsumer;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public interface PromiseCallback<E> {
    boolean isAsync();
    boolean isProvidedExceptionHandler();

    BiConsumer<E, Throwable> getCallback();
}
