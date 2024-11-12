package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.EmptyPromise;
import me.thedivazo.libs.database.promise.Promise;
import me.thedivazo.libs.database.promise.pipeline.PromiseEmptyPipeline;
import me.thedivazo.libs.util.execut.AsyncThreadPool;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 **/
public class EmptyPromisePipelineFactory extends PromisePipelineFactory<Void> implements EmptyPromiseFactory {
    public EmptyPromisePipelineFactory(SyncExecutor syncExecutor, AsyncThreadPool asyncThreadPool, Logger dbLogger) {
        super(syncExecutor, asyncThreadPool, dbLogger);
    }

    @Override
    public EmptyPromise ofPromise(Runnable runnable) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(runnable);
        return (EmptyPromise) ofPromise(future);
    }

    @Override
    public <T extends Void> Promise<T> ofPromise(CompletableFuture<T> future) {
        return (Promise<T>) new PromiseEmptyPipeline<>(future, syncExecutor, asyncThreadPool, dbLogger);
    }
}
