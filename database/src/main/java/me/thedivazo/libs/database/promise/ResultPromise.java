package me.thedivazo.libs.database.promise;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ResultPromise<E> extends Promise<E> {

    void thenAsync(Consumer<E> valueConsumer);

    void thenSync(Consumer<E> valueConsumer);

    void thenAsyncThr(BiConsumer<E, Throwable> valueConsumer);

    void thenSyncThr(BiConsumer<E, Throwable> valueConsumer);

}
