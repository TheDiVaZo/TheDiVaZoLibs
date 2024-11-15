package me.thedivazo.libs.database.execut;

import java.util.concurrent.CompletableFuture;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
public interface AsyncExecutor {
    void execute(Runnable task);
}
