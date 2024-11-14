package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.Promise;
import me.thedivazo.libs.util.execut.AsyncExecutor;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 **/
public abstract class PromisePipelineFactory<E, P extends Promise<? extends E>> implements PromiseFactory<E, P> {
    protected final SyncExecutor syncExecutor;
    protected final AsyncExecutor asyncExecutor;
    protected final Logger logger;

    protected PromisePipelineFactory(SyncExecutor syncExecutor, AsyncExecutor asyncExecutor, Logger logger) {
        this.syncExecutor = syncExecutor;
        this.asyncExecutor = asyncExecutor;
        this.logger = logger;
    }
}
