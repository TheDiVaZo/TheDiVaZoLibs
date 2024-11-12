package me.thedivazo.libs.database.promise.pipeline;


import me.thedivazo.libs.database.promise.EmptyPromise;
import me.thedivazo.libs.database.promise.callback.PromiseEmptyResultCallback;
import me.thedivazo.libs.util.execut.AsyncThreadPool;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.logging.Logger;

@SuppressWarnings("unchecked")
public class PromiseEmptyPipeline<P extends PromiseEmptyResultCallback, E> extends PromisePipeline<P,E> implements EmptyPromise {

    public PromiseEmptyPipeline(CompletableFuture<E> databaseResultFuture,
                                SyncExecutor syncExecutor,
                                AsyncThreadPool asyncThreadPool,
                                Logger dbLogger) {
        super(databaseResultFuture, syncExecutor, asyncThreadPool, dbLogger);
    }

    public CompletableFuture<Void> getResultFuture() {
        return (CompletableFuture<Void>) resultFuture;
    }


    @Override
    public void thenAsync(Runnable callback) {
        setCallbackAndHandlePromise((P) new PromiseEmptyResultCallback(true, callback));
    }

    @Override
    public void thenSync(Runnable callback) {
        setCallbackAndHandlePromise((P) new PromiseEmptyResultCallback(false, callback));
    }

    @Override
    public void thenAsync(Consumer<Throwable> throwableConsumer) {
        setCallbackAndHandlePromise((P) new PromiseEmptyResultCallback(true, throwableConsumer));
    }

    @Override
    public void thenSync(Consumer<Throwable> throwableConsumer) {
        setCallbackAndHandlePromise((P) new PromiseEmptyResultCallback(false, throwableConsumer));
    }


    @Override
    public void handleDatabasePromise() {
        asyncThreadPool.getExecutor().execute(() -> {
            P promiseCallback = promiseCallbackRef.get();

            try {
                resultFuture.get();
                if(promiseCallback.isAsync()) {
                    promiseCallback.getEmptyResultCallback().accept(null);
                }
                else { // SYNC
                    syncExecutor.runSync(() -> promiseCallback.getEmptyResultCallback().accept(null));
                }
            } catch (Throwable e) {
                handleDatabaseException(promiseCallback, e);
            }
        });
    }

    @Override
    protected void handleDatabaseException(P bukkitCallback, Throwable throwable) {
        if(!bukkitCallback.isProvidedExceptionHandler()) {
            dbLogger.warning(
                    String.format("Async database result promise ended with an" +
                            " exception and you didn't handled the exception, error message: '%s'." +
                            "If you want to handle the exception, you can use the " +
                            "then or thenAsync((throwable) -> ) method. location: %s",
                            throwable.getMessage(), stackTraceElementCaller.toString()));
            throwable.printStackTrace();
            return;
        }
        if(bukkitCallback.isAsync()) {
            try {
                bukkitCallback.getEmptyResultCallback().accept(throwable);
            } catch (Throwable internalThrowable) {
                internalThrowable.printStackTrace();
            }
        }
        else { // SYNC
            syncExecutor.runSync(() -> bukkitCallback.getEmptyResultCallback().accept(throwable));
        }
    }
}
