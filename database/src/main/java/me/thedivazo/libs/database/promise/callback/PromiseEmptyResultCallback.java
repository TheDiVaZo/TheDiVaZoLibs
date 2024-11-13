package me.thedivazo.libs.database.promise.callback;

import lombok.Getter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Getter
public class PromiseEmptyResultCallback extends PromiseResultCallback<Void> implements PromiseEmptyCallback {

    private final Consumer<Throwable> emptyCallback;

    public PromiseEmptyResultCallback(boolean isAsync, Consumer<Void> callback) {
        super(isAsync, callback);
        this.emptyCallback = (t) -> callback.accept(null);
    }

    public PromiseEmptyResultCallback(boolean isAsync, BiConsumer<Void, Throwable> callback) {
        super(isAsync, callback);
        this.emptyCallback = (t) -> callback.accept(null , t);
    }
}