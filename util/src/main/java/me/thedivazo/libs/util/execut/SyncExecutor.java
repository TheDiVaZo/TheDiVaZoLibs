package me.thedivazo.libs.util.execut;

public interface SyncExecutor {
    /**
     * Run a task on the main thread and is safe to mute game state
     */
    void runSync(Runnable runnable);
}
