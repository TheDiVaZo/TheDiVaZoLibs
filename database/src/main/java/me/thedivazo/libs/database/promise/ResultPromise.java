package me.thedivazo.libs.database.promise;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Async pipeline in the case your database operation return a result
 *
 * @param <E> Your Entity class
 */
public interface ResultPromise<E> extends Promise<E> {

    /**
     * retrieve data async and consume it in the same async thread once it's available.
     * This method doesn't handle exception, so if an Exception occurred during
     * the data retrieving, your consumer will be ignored and won't be called.
     * <pre>{@code
     *     .thenAsync((entity) -> {
     *         // handle entity
     *         // will not be called if an exception occurred
     *     })
     * }</pre>
     * Note that if an exception occurred NDatabase with handle and log a warning message for you
     * telling that you didn't handle the exception and the code line will be specified.
     *
     * @param valueConsumer your entity value consumer.
     *                      Will be called only if there is no exception.
     *                      E will be null if no result was found
     */
    void thenAsync(Consumer<E> valueConsumer);

    /**
     * retrieve data async and consume it in the main thread once it's available.
     * This method doesn't handle exception, so if an Exception occurred during
     * the data retrieving, your consumer will be ignored and won't be called.
     * <pre>{@code
     *     .thenSync((entity) -> {
     *         // handle entity
     *         // will not be called if an exception occurred
     *     })
     * }</pre>
     * Note that if an exception occurred NDatabase with handle and log a warning message for you
     * telling that you didn't handle the exception and the code line will be specified.
     *
     * @param valueConsumer your entity value consumer.
     *                      Will be called only if there is no exception.
     *                      E will be null if no result was found
     */
    void thenSync(Consumer<E> valueConsumer);

    /**
     * retrieve data async and consume it in the same async thread once it's available.
     * This method handle exception if an exception occurred.
     * In case where the task ended exceptionally, E value will be null and Throwable
     * will contain your exception.
     * <pre>{@code
     *     .thenAsync((entity, throwable) -> {
     *         if(throwable != null) {
     *             // Handle exception
     *             return;
     *         }
     *         // handle entity
     *     })
     * }</pre>
     *
     * @param valueConsumer your entity, exception value consumer.
     *                      E will be null if no result was found
     */
    void thenAsync(BiConsumer<E, Throwable> valueConsumer);

    /**
     * retrieve data async and consume it in the main thread once it's available.
     * This method handle exception if an exception occurred.
     * In case where the task ended exceptionally, E value will be null and Throwable
     * will contain your exception.
     * <pre>{@code
     *     .thenSync((entity, throwable) -> {
     *         if(throwable != null) {
     *             // Handle exception
     *             return;
     *         }
     *         // handle entity
     *     })
     * }</pre>
     *
     * @param valueConsumer your entity, exception value consumer.
     *                      E will be null if no result was found
     */
    void thenSync(BiConsumer<E, Throwable> valueConsumer);
}
