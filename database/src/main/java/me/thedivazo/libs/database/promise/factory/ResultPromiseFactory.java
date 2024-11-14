package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.Promise;
import me.thedivazo.libs.database.promise.ResultPromise;
import me.thedivazo.libs.util.execut.AsyncExecutor;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public interface ResultPromiseFactory extends PromiseFactory<Object, ResultPromise<?>> {
    <T> ResultPromise<T> ofPromise(Supplier<T> future);
}
