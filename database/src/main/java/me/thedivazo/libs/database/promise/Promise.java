package me.thedivazo.libs.database.promise;

import java.util.concurrent.CompletableFuture;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public interface Promise<E> {
    CompletableFuture<? extends E> getResultFuture();
}
