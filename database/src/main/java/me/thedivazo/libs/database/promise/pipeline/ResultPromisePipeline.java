package me.thedivazo.libs.database.promise.pipeline;

import me.thedivazo.libs.database.promise.ResultPromise;
import me.thedivazo.libs.database.promise.callback.PromiseCallback;
import me.thedivazo.libs.database.promise.callback.PromiseEmptyResultCallback;
import me.thedivazo.libs.database.promise.callback.PromiseResultCallback;
import me.thedivazo.libs.database.promise.executor.AsyncExecutor;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public class ResultPromisePipeline<P extends PromiseCallback<E>, E> extends AbstractPromisePipeline<P, E> implements ResultPromise<E> {
    public ResultPromisePipeline(CompletableFuture<E> future, AsyncExecutor asyncExecutor, SyncExecutor syncExecutor, Logger logger) {
        super(future, asyncExecutor, syncExecutor, logger);
    }

    @SuppressWarnings("unchecked")
    protected void setHandler(boolean isAsync, Consumer<E> consumer) {
        setCallbackAndHandlePromise((P) new PromiseResultCallback<>(isAsync, consumer));
    }

    @SuppressWarnings("unchecked")
    protected void setHandlerThr(boolean isAsync, BiConsumer<E, Throwable> consumer) {
        setCallbackAndHandlePromise((P) new PromiseResultCallback<>(isAsync, consumer));
    }

    @Override
    public void thenAsync(Consumer<E> valueConsumer) {
        setHandler(true, valueConsumer);
    }

    @Override
    public void thenSync(Consumer<E> valueConsumer) {
        setHandler(false, valueConsumer);
    }

    @Override
    public void thenAsyncThr(BiConsumer<E, Throwable> valueConsumer) {
        setHandlerThr(true, valueConsumer);
    }

    @Override
    public void thenSyncThr(BiConsumer<E, Throwable> valueConsumer) {
        setHandlerThr(false, valueConsumer);
    }
}
