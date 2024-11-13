package me.thedivazo.libs.database.promise.pipeline;

import me.thedivazo.libs.database.promise.Promise;
import me.thedivazo.libs.database.promise.ResultPromise;
import me.thedivazo.libs.database.promise.callback.PromiseCallback;
import me.thedivazo.libs.database.promise.executor.AsyncExecutor;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public abstract class AbstractPromisePipeline<P extends PromiseCallback<E>, E> implements Promise<E> {

    protected final CompletableFuture<E> future;
    protected final AsyncExecutor asyncExecutor;
    protected final SyncExecutor syncExecutor;
    protected final Logger logger;

    protected AtomicReference<P> promiseCallbackRef;

    protected StackTraceElement stackTraceElementCaller;

    public AbstractPromisePipeline(CompletableFuture<E> future, AsyncExecutor asyncExecutor, SyncExecutor syncExecutor, Logger logger) {
        this.future = future;
        this.asyncExecutor = asyncExecutor;
        this.syncExecutor = syncExecutor;
        this.promiseCallbackRef = new AtomicReference<>();
        this.logger = logger;
    }

    protected void setCallbackAndHandlePromise(P promiseCallback) {
        if(this.promiseCallbackRef.get() != null) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[1];
            logger.warning(String.format("%s%nAsync promise callback called twice, the async result as already been consumed", stackTraceElement.toString()));
            return;
        }
        stackTraceElementCaller = Thread.currentThread().getStackTrace()[3];
        this.promiseCallbackRef.set(promiseCallback);
        handlePromise();
    }

    protected void handlePromise() {
        asyncExecutor.execute(() -> {
            E result;
            P promiseCallback = this.promiseCallbackRef.get();

            try {
                result = future.get();
                if(promiseCallback.isAsync()) {
                    promiseCallback.getCallback().accept(result, null);
                } else { // SYNC
                    syncExecutor.runSync(() -> promiseCallback.getCallback().accept(result, null));
                }
            } catch (Throwable e) {
                handleException(promiseCallback, e);
            }
        });
    }

    protected void handleException(P promiseCallback, Throwable e) {
        if (!promiseCallback.isProvidedExceptionHandler()) {
            logger.warning(String.format("%s%nAsync promise ended with an exception, error message: '%s'.", stackTraceElementCaller.toString(), e.getMessage()));
            e.printStackTrace();
            return;
        }
        if(promiseCallback.isAsync()) {
            promiseCallback.getCallback().accept(null, e);
        } else { // SYNC
            syncExecutor.runSync(() -> promiseCallback.getCallback().accept(null, e));
        }

    }

    @Override
    public CompletableFuture<E> getResultFuture() {
        return future;
    }
}
