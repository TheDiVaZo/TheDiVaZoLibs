package me.thedivazo.libs.database.repo.entity;

import java.util.concurrent.atomic.AtomicLong;

public class LongIdGenerator implements NextIdGenerator<Long> {
    protected AtomicLong atomicLong = new AtomicLong();

    @Override
    public Long generateNextId() {
        return atomicLong.getAndIncrement();
    }
}
