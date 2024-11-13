package me.thedivazo.libs.database.promise.executor;

import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
@AllArgsConstructor
public class FutureThreadExecutor implements AsyncExecutor {
    protected final CompletableFuture<?> completableFuture;

    @Override
    public void execute(Runnable task) {
        completableFuture.thenAccept((v)->task.run());
    }
}
