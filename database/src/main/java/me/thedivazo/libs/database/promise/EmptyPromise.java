package me.thedivazo.libs.database.promise;

import java.util.function.Consumer;

/**
 * Async pipeline in the case your database operation doesn't return a result
 */
public interface EmptyPromise extends ResultPromise<Void> {

    void thenAsync(Runnable callback);

    void thenSync(Runnable callback);

    void thenAsyncThr(Consumer<Throwable> throwableConsumer);

    void thenSyncThr(Consumer<Throwable> throwableConsumer);
}
