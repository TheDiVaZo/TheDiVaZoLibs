package me.thedivazo.libs.database.repo.entity;

import java.util.UUID;

public final class UUIDGenerator implements NextIdGenerator<UUID> {
    @Override
    public UUID generateNextId() {
        return UUID.randomUUID();
    }
}
