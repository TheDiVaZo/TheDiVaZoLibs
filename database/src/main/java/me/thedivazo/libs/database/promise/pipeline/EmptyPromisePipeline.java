package me.thedivazo.libs.database.promise.pipeline;

import me.thedivazo.libs.database.promise.EmptyPromise;
import me.thedivazo.libs.database.promise.callback.PromiseEmptyCallback;
import me.thedivazo.libs.database.promise.callback.PromiseEmptyResultCallback;
import me.thedivazo.libs.database.promise.executor.AsyncExecutor;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public class EmptyPromisePipeline<P extends PromiseEmptyCallback> extends ResultPromisePipeline<P,Void> implements EmptyPromise {
    public EmptyPromisePipeline(CompletableFuture<Void> future, AsyncExecutor asyncExecutor, SyncExecutor syncExecutor, Logger logger) {
        super(future, asyncExecutor, syncExecutor, logger);
    }

    @Override
    public void thenAsync(Runnable callback) {
        setHandler(true, (v)->callback.run());
    }

    @Override
    public void thenSync(Runnable callback) {
        setHandler(false, (v)->callback.run());
    }

    @Override
    public void thenAsyncThr(Consumer<Throwable> throwableConsumer) {
        setHandlerThr(true, (v, t)-> throwableConsumer.accept(t));
    }

    @Override
    public void thenSyncThr(Consumer<Throwable> throwableConsumer) {
        setHandlerThr(false, (v, t)-> throwableConsumer.accept(t));
    }
}
