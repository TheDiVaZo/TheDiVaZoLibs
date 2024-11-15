package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.ResultPromise;

import java.util.function.Supplier;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public interface ResultPromiseFactory extends PromiseFactory<Object, ResultPromise<?>> {
    <T> ResultPromise<T> ofPromise(Supplier<T> future);
}
