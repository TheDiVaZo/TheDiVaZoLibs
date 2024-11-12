package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.Promise;

import java.util.concurrent.CompletableFuture;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 */
public interface PromiseFactory<E> {
    /**
     * Оборачиваете CompletableFuture в промис. CompletableFuture запускаете самостоятельно.
     * @param future
     * @return
     * @param <T>
     */
    <T extends E> Promise<T> ofPromise(CompletableFuture<T> future);
}
