package me.thedivazo.libs.database.promise;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * NDatabase - KeyValue store database
 * Report any issue or contribute here https://github.com/NivixX/NDatabase
 *
 * Promise interface to handle the Async to Sync mechanism
 * You can easily do async call to generate a CompletableFuture
 * and handle this future task to schedule callback in the main thread
 */
public interface Promise<E> {
    /**
     * @return future task, handle it as your needs
     */
    CompletableFuture<E> getResultFuture();

}
