package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.Promise;
import me.thedivazo.libs.database.promise.ResultPromise;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 */
public interface ResultPromiseFactory extends PromiseFactory {
    <E> ResultPromise<E> ofPromise(Supplier<E> supplier);
}
