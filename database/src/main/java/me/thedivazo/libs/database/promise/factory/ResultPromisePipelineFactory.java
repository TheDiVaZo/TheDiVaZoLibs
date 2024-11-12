package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.Promise;
import me.thedivazo.libs.database.promise.ResultPromise;
import me.thedivazo.libs.database.promise.pipeline.PromiseResultPipeline;
import me.thedivazo.libs.util.execut.AsyncThreadPool;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 **/
public class ResultPromisePipelineFactory extends PromisePipelineFactory<Object> implements ResultPromiseFactory {
    public ResultPromisePipelineFactory(SyncExecutor syncExecutor, AsyncThreadPool asyncThreadPool, Logger dbLogger) {
        super(syncExecutor, asyncThreadPool, dbLogger);
    }

    @Override
    public <E> ResultPromise<E> ofPromise(Supplier<E> supplier) {
        CompletableFuture<E> future = CompletableFuture.supplyAsync(supplier);
        return (ResultPromise<E>) ofPromise(future);
    }

    @Override
    public <T> Promise<T> ofPromise(CompletableFuture<T> future) {
        return new PromiseResultPipeline<>(future, syncExecutor, asyncThreadPool, dbLogger);
    }
}
