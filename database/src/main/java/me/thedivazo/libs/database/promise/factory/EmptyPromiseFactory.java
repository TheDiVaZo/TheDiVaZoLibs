package me.thedivazo.libs.database.promise.factory;

import me.thedivazo.libs.database.promise.EmptyPromise;

import java.util.function.Consumer;

/**
 * @author TheDiVaZo
 * created on 12.11.2024
 */
public interface EmptyPromiseFactory extends PromiseFactory<Void> {

    EmptyPromise ofPromise(Runnable runnable);
}
