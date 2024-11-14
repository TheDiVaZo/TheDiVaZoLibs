package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.EmptyPromise;
import me.thedivazo.libs.database.promise.Promise;
import me.thedivazo.libs.util.execut.AsyncExecutor;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 */
public interface EmptyPromiseFactory extends PromiseFactory<Void, EmptyPromise> {
    EmptyPromise ofPromise(Runnable runnable);
}
