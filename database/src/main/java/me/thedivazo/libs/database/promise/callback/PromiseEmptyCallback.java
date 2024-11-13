package me.thedivazo.libs.database.promise.callback;

import java.util.function.Consumer;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public interface PromiseEmptyCallback extends PromiseCallback<Void> {
    Consumer<Throwable> getEmptyCallback();
}
