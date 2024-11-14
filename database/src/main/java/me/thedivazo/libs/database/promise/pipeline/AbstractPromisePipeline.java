package me.thedivazo.libs.database.promise.pipeline;

import me.thedivazo.libs.database.promise.Promise;
import me.thedivazo.libs.database.promise.callback.PromiseCallback;
import me.thedivazo.libs.util.execut.AsyncExecutor;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public abstract class AbstractPromisePipeline<P extends PromiseCallback<E>, E> implements Promise<E> {

    protected final CompletableFuture<? extends E> future;
    protected final AsyncExecutor asyncExecutor;
    protected final SyncExecutor syncExecutor;
    protected final Logger logger;

    protected AtomicReference<P> promiseCallbackRef;

    protected StackTraceElement stackTraceElementCaller;

    protected AbstractPromisePipeline(CompletableFuture<? extends E> future, AsyncExecutor asyncExecutor, SyncExecutor syncExecutor, Logger logger) {
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

    protected abstract void handlePromise();

    protected abstract void handleException(P promiseCallback, Throwable throwable);

    @Override
    public CompletableFuture<? extends E> getResultFuture() {
        return future;
    }
}
