package me.thedivazo.libs.database.promise.pipeline;


import me.thedivazo.libs.database.promise.ResultPromise;
import me.thedivazo.libs.database.promise.callback.PromiseResultCallback;
import me.thedivazo.libs.util.execut.AsyncThreadPool;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Logger;

@SuppressWarnings("unchecked")
public class PromiseResultPipeline<P extends PromiseResultCallback<E>, E> extends PromisePipeline<P, E> implements ResultPromise<E> {

    public PromiseResultPipeline(CompletableFuture<E> databaseResultFuture,
                                 SyncExecutor syncExecutor,
                                 AsyncThreadPool asyncThreadPool,
                                 Logger dbLogger) {
        super(databaseResultFuture, syncExecutor, asyncThreadPool, dbLogger);
    }


    @Override
    public CompletableFuture<E> getResultFuture() {
        return resultFuture;
    }

    @Override
    public void thenAsync(Consumer<E> valueConsumer) {
        setCallbackAndHandlePromise((P) new PromiseResultCallback<E>(true, valueConsumer));
    }

    @Override
    public void thenSync(Consumer<E> valueConsumer) {
        setCallbackAndHandlePromise((P) new PromiseResultCallback<>(false, valueConsumer));
    }

    @Override
    public void thenAsync(BiConsumer<E, Throwable> valueConsumer) {
        setCallbackAndHandlePromise((P) new PromiseResultCallback<>(true, valueConsumer));
    }

    @Override
    public void thenSync(BiConsumer<E, Throwable> valueConsumer) {
        setCallbackAndHandlePromise((P) new PromiseResultCallback<>(true, valueConsumer));
    }

    @Override
    public void handleDatabasePromise() {
        asyncThreadPool.getExecutor().execute(() -> {
            E entityResult;
            P promiseCallback = promiseCallbackRef.get();

            try {
                entityResult = resultFuture.get();
                if (promiseCallback.isAsync()) {
                    promiseCallback.getResultCallback().accept(entityResult, null);
                } else { // SYNC
                    syncExecutor.runSync(() -> promiseCallback.getResultCallback().accept(entityResult, null));
                }
            } catch (Throwable e) {
                handleDatabaseException(promiseCallback, e);
            }
        });
    }

    @Override
    protected void handleDatabaseException(P bukkitCallback, Throwable throwable) {
        if (!bukkitCallback.isProvidedExceptionHandler()) {
            dbLogger.warning(
                    String.format("Async database result promise ended with an" +
                                    " exception and you didn't handled the exception, error message: '%s'." +
                                    "If you want to handle the exception, you can use the " +
                                    "then or thenAsync((entity, throwable) -> ) method. location: %s",
                            throwable.getMessage(), stackTraceElementCaller.toString()));
            throwable.printStackTrace();
            return;
        }
        if (bukkitCallback.isAsync()) {
            try {
                bukkitCallback.getResultCallback().accept(null, throwable);
            } catch (Throwable internalThrowable) {
                internalThrowable.printStackTrace();
            }
        } else { // SYNC
            syncExecutor.runSync(() -> bukkitCallback.getResultCallback().accept(null, throwable));
        }
    }
}
