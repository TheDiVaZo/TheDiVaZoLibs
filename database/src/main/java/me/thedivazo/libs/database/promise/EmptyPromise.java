package me.thedivazo.libs.database.promise;

import java.util.function.Consumer;

/**
 * Async pipeline in the case your database operation doesn't return a result
 */
public interface EmptyPromise extends Promise<Void> {

    /**
     * process your database operation async and run a callback in the same async thread when finished
     * This method doesn't handle exception, so if an Exception occurred during
     * the operation, your callback will be ignored and won't be called.
     * <pre>{@code
     *     .thenAsync(() -> {
     *         // execute callback
     *         // will not be called if an exception occurred
     *     })
     * }</pre>
     * Note that if an exception occurred NDatabase with handle and log a warning message for you
     * telling that you didn't handle the exception and the code line will be specified.
     *
     * @param callback that will be called only if there is no exception.
     */
    void thenAsync(Runnable callback);

    /**
     * process your database operation async and run a callback in the main thread when finished
     * This method doesn't handle exception, so if an Exception occurred during
     * the operation, your callback will be ignored and won't be called.
     * <pre>{@code
     *     .thenAsync(() -> {
     *         // execute callback
     *         // will not be called if an exception occurred
     *     })
     * }</pre>
     * Note that if an exception occurred NDatabase with handle and log a warning message for you
     * telling that you didn't handle the exception and the code line will be specified.
     *
     * @param callback that will be called only if there is no exception.
     */
    void thenSync(Runnable callback);

    /**
     * process your database operation async and run a callback in the same async thread when finished
     * This method handle exception if an exception occurred.
     * <pre>{@code
     *     .thenAsync((throwable) -> {
     *         if(throwable != null) {
     *             // handle exception
     *             return;
     *         }
     *         // execute callback
     *     })
     * }</pre>
     *
     * @param throwableConsumer throwable consumer.
     *                          will be null if no exception occurred
     */
    void thenAsync(Consumer<Throwable> throwableConsumer);

    /**
     * process your database operation async and run a callback in the main thread when finished
     * This method handle exception if an exception occurred.
     * <pre>{@code
     *     .thenSync((throwable) -> {
     *         if(throwable != null) {
     *             // handle exception
     *             return;
     *         }
     *         // execute callback
     *     })
     * }</pre>
     *
     * @param throwableConsumer throwable consumer.
     *                          will be null if no exception occurred
     */
    void thenSync(Consumer<Throwable> throwableConsumer);
}
