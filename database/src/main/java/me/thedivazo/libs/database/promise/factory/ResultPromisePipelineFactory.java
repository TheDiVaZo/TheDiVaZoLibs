package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.ResultPromise;
import me.thedivazo.libs.database.promise.executor.AsyncExecutor;
import me.thedivazo.libs.database.promise.pipeline.ResultPromisePipeline;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 **/
public class ResultPromisePipelineFactory extends PromisePipelineFactory<Object> implements ResultPromiseFactory<Object> {
    public ResultPromisePipelineFactory(SyncExecutor syncExecutor, AsyncExecutor asyncExecutor, Logger logger) {
        super(syncExecutor, asyncExecutor, logger);
    }

    @Override
    public <T> ResultPromise<T> ofPromise(CompletableFuture<T> future) {
        return new ResultPromisePipeline<>(future, asyncExecutor, syncExecutor, logger);
    }

    @Override
    public <T> ResultPromise<T> ofPromise(Supplier<T> future) {
        CompletableFuture<T> result = CompletableFuture.supplyAsync(future);
        return ofPromise(result);
    }
}
