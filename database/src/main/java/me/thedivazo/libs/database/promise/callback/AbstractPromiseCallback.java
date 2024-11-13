package me.thedivazo.libs.database.promise.callback;

public abstract class AbstractPromiseCallback<E> implements PromiseCallback<E> {

    protected final boolean isAsync;
    protected final boolean isProvidedExceptionHandler;

    protected AbstractPromiseCallback(boolean isAsync, boolean isProvidedExceptionHandler) {
        this.isAsync = isAsync;
        this.isProvidedExceptionHandler = isProvidedExceptionHandler;
    }

    public boolean isAsync() {
        return isAsync;
    }

    public boolean isProvidedExceptionHandler() {
        return isProvidedExceptionHandler;
    }
}