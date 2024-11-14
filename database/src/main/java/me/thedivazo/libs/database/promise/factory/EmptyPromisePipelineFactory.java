package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.EmptyPromise;
import me.thedivazo.libs.database.promise.ResultPromise;
import me.thedivazo.libs.util.execut.AsyncExecutor;
import me.thedivazo.libs.database.promise.pipeline.EmptyPromisePipeline;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 **/
public class EmptyPromisePipelineFactory extends PromisePipelineFactory<Void, EmptyPromise> implements EmptyPromiseFactory {
    public EmptyPromisePipelineFactory(SyncExecutor syncExecutor, AsyncExecutor asyncExecutor, Logger dbLogger) {
        super(syncExecutor, asyncExecutor, dbLogger);
    }

    @Override
    public <T extends Void> EmptyPromise ofPromise(CompletableFuture<T> future) {
        return new EmptyPromisePipeline<>(future, asyncExecutor, syncExecutor, logger);
    }

    @Override
    public EmptyPromise ofPromise(Runnable runnable) {
        return ofPromise(CompletableFuture.runAsync(runnable));
    }
}
