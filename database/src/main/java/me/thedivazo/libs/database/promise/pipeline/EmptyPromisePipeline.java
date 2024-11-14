package me.thedivazo.libs.database.promise.pipeline;

import me.thedivazo.libs.database.promise.EmptyPromise;
import me.thedivazo.libs.database.promise.callback.PromiseEmptyCallback;
import me.thedivazo.libs.database.promise.callback.PromiseEmptyResultCallback;
import me.thedivazo.libs.util.execut.AsyncExecutor;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public class EmptyPromisePipeline<P extends PromiseEmptyCallback> extends AbstractPromisePipeline<P, Void> implements EmptyPromise {
    public EmptyPromisePipeline(CompletableFuture<? extends Void> future, AsyncExecutor asyncExecutor, SyncExecutor syncExecutor, Logger logger) {
        super(future, asyncExecutor, syncExecutor, logger);
    }
    @Override
    protected void handlePromise() {
        asyncExecutor.execute(() -> {
            P promiseCallback = this.promiseCallbackRef.get();

            try {
                future.get();
                if(promiseCallback.isAsync()) {
                    promiseCallback.getEmptyCallback().accept(null);
                } else { // SYNC
                    syncExecutor.runSync(() -> promiseCallback.getEmptyCallback().accept(null));
                }
            } catch (Throwable e) {
                handleException(promiseCallback, e);
            }
        });
    }

    @Override
    protected void handleException(P promiseCallback, Throwable e) {
        if (promiseCallback.isProvidedExceptionHandler()) {
            logger.warning(String.format("%s%nAsync promise ended with an exception, error message: '%s'.", stackTraceElementCaller.toString(), e.getMessage()));
            e.printStackTrace();
            return;
        }
        if(promiseCallback.isAsync()) {
            promiseCallback.getCallback().accept(null, e);
        } else { // SYNC
            syncExecutor.runSync(() -> promiseCallback.getEmptyCallback().accept(e));
        }
    }

    @SuppressWarnings("unchecked")
    protected void setHandlerThr(boolean isAsync, Consumer<Throwable> consumer) {
        setCallbackAndHandlePromise((P) new PromiseEmptyResultCallback(isAsync, consumer));
    }

    @SuppressWarnings("unchecked")
    protected void setHandler(boolean isAsync, Runnable runnable) {
        setCallbackAndHandlePromise((P) new PromiseEmptyResultCallback(isAsync, runnable));
    }

    @Override
    public void thenAsync(Runnable callback) {
        setHandler(true, callback);
    }

    @Override
    public void thenSync(Runnable callback) {
        setHandler(false, callback);
    }

    @Override
    public void thenAsyncThr(Consumer<Throwable> throwableConsumer) {
        setHandlerThr(true, throwableConsumer);
    }

    @Override
    public void thenSyncThr(Consumer<Throwable> throwableConsumer) {
        setHandlerThr(false, throwableConsumer);
    }

    @Override
    public void thenAsync(Consumer<Void> valueConsumer) {
        setHandler(true, () -> valueConsumer.accept(null));
    }

    @Override
    public void thenSync(Consumer<Void> valueConsumer) {
        setHandler(false, () -> valueConsumer.accept(null));
    }

    @Override
    public void thenAsyncThr(BiConsumer<Void, Throwable> valueConsumer) {
        setHandlerThr(true, t->valueConsumer.accept(null, t));
    }

    @Override
    public void thenSyncThr(BiConsumer<Void, Throwable> valueConsumer) {
        setHandlerThr(false, t->valueConsumer.accept(null, t));
    }
}
