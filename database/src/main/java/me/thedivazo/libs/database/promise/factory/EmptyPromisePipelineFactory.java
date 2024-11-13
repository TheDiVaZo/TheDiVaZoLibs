package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.EmptyPromise;
import me.thedivazo.libs.database.promise.ResultPromise;
import me.thedivazo.libs.database.promise.executor.AsyncExecutor;
import me.thedivazo.libs.database.promise.pipeline.EmptyPromisePipeline;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 **/
public class EmptyPromisePipelineFactory extends PromisePipelineFactory<Void> implements EmptyPromiseFactory {
    public EmptyPromisePipelineFactory(SyncExecutor syncExecutor, AsyncExecutor asyncExecutor, Logger dbLogger) {
        super(syncExecutor, asyncExecutor, dbLogger);
    }

    @Override
    public EmptyPromise ofPromise(Runnable runnable) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(runnable);
        return (EmptyPromise) ofPromise(future);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Void> ResultPromise<T> ofPromise(CompletableFuture<T> future) {
        return (ResultPromise<T>) new EmptyPromisePipeline<>((CompletableFuture<Void>) future, asyncExecutor, syncExecutor, logger);
    }
}
