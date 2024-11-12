package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.util.execut.AsyncThreadPool;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 **/
public abstract class PromisePipelineFactory<T> implements PromiseFactory<T> {
    protected final SyncExecutor syncExecutor;
    protected final AsyncThreadPool asyncThreadPool;
    protected final Logger dbLogger;

    public PromisePipelineFactory(SyncExecutor syncExecutor, AsyncThreadPool asyncThreadPool, Logger dbLogger) {
        this.syncExecutor = syncExecutor;
        this.asyncThreadPool = asyncThreadPool;
        this.dbLogger = dbLogger;
    }
}
