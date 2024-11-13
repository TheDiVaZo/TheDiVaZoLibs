package me.thedivazo.libs.database.promise.executor;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public interface AsyncExecutor {
    void execute(Runnable task);
}
