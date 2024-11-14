package me.thedivazo.libs.database.promise;

import java.util.function.Consumer;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public interface EmptyPromise extends ResultPromise<Void> {

    void thenAsync(Runnable callback);

    void thenSync(Runnable callback);

    void thenAsyncThr(Consumer<Throwable> throwableConsumer);

    void thenSyncThr(Consumer<Throwable> throwableConsumer);
}
