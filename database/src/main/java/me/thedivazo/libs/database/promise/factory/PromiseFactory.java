package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.Promise;

import java.util.concurrent.CompletableFuture;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 */
public interface PromiseFactory<E, P extends Promise<? extends E>> {
    /**
     * Оборачиваете CompletableFuture в промис. CompletableFuture запускаете самостоятельно.
     * @param future
     * @return Возврашает промис
     * @param <T>
     */
    <T extends E> P ofPromise(CompletableFuture<T> future);
}
