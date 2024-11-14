package me.thedivazo.libs.util.execut;

import lombok.AllArgsConstructor;

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
