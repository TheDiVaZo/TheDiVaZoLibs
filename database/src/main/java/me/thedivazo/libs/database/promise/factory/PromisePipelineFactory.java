package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.executor.AsyncExecutor;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 **/
public abstract class PromisePipelineFactory<T> implements PromiseFactory<T> {
    protected final SyncExecutor syncExecutor;
    protected final AsyncExecutor asyncExecutor;
    protected final Logger logger;

    protected PromisePipelineFactory(SyncExecutor syncExecutor, AsyncExecutor asyncExecutor, Logger logger) {
        this.syncExecutor = syncExecutor;
        this.asyncExecutor = asyncExecutor;
        this.logger = logger;
    }
}
