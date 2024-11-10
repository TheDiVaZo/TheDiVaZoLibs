package me.thedivazo.libs.database.repo.entity;

import java.util.concurrent.atomic.AtomicLong;

public class LongIdGenerator implements NextIdGenerator<Long> {
    protected AtomicLong atomicLong = new AtomicLong(1);

    @Override
    public Long generateNextId() {
        return atomicLong.getAndIncrement();
    }
}
