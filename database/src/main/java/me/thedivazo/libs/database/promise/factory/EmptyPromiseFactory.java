package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.EmptyPromise;
import me.thedivazo.libs.database.promise.Promise;

import java.util.concurrent.CompletableFuture;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 */
public interface EmptyPromiseFactory extends PromiseFactory {
    EmptyPromise ofPromise(Runnable runnable);
}
