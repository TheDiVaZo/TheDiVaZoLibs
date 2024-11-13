package me.thedivazo.libs.database.promise.executor;

import lombok.AllArgsConstructor;
import me.thedivazo.libs.util.execut.AsyncThreadPool;

/**
 * @author TheDiVaZo
 * created on 13.11.2024
 */
@AllArgsConstructor
public class ThreadPoolExecutor implements AsyncExecutor {
    protected final AsyncThreadPool asyncThreadPool;

    @Override
    public void execute(Runnable task) {
        asyncThreadPool.getExecutor().execute(task);
    }
}
