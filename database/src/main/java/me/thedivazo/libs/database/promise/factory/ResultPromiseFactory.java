package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.Promise;
import me.thedivazo.libs.database.promise.ResultPromise;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public interface ResultPromiseFactory extends PromiseFactory<Object> {


    <T> ResultPromise<T> ofPromise(Supplier<T> future);
}
