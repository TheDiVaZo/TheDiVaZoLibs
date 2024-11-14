package me.thedivazo.libs.database.promise.callback;

import lombok.Getter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Getter
public class PromiseEmptyResultCallback extends AbstractPromiseCallback<Void> implements PromiseEmptyCallback {
    private final Consumer<Throwable> emptyCallback;
    private final BiConsumer<Void, Throwable> resultEmptyCallback;

    public PromiseEmptyResultCallback(boolean isAsync, Runnable runnable) {
        super(isAsync, false);
        this.emptyCallback = t -> runnable.run();
        this.resultEmptyCallback = (v, t) -> runnable.run();
    }

    public PromiseEmptyResultCallback(boolean isAsync, Consumer<Throwable> callback) {
        super(isAsync, true);
        this.emptyCallback = callback;
        this.resultEmptyCallback = (v, t) -> callback.accept(t);
    }

    @Override
    public BiConsumer<Void, Throwable> getCallback() {
        return resultEmptyCallback;
    }
}