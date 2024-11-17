package me.thedivazo.libs.tests.database.dao;

import me.thedivazo.libs.database.dao.Dao;
import me.thedivazo.libs.tests.database.dao.entity.BinaryIdPlayerEntity;
import me.thedivazo.libs.tests.database.dao.entity.UuidPlayerEntity;
import me.thedivazo.libs.tests.database.dao.entity.UuidPlayerEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TheDiVaZo
 * created on 18.11.2024
 */
public abstract class UuidAbstractDaoTest {
    protected static Dao<UuidPlayerEntity, UUID> dao;

    @AfterEach
    public void cleanUp() {
        dao.deletesAll();
    }

    @Test
    public void insertAndGet() {
        UuidPlayerEntity insertablePlayer = new UuidPlayerEntity(UUID.fromString("00000000-0000-0000-0000-000000000000"), "Abobus");
        dao.insert(insertablePlayer);
        UuidPlayerEntity retrievedPlayer = dao.get(insertablePlayer.uuid());
        assertEquals(insertablePlayer, retrievedPlayer);
    }

    @Test
    public void insertAndDelete() {
        UuidPlayerEntity insertablePlayer = UuidPlayerEntity.generate();
        dao.insert(insertablePlayer);
        UuidPlayerEntity retrievedPlayer = dao.get(insertablePlayer.uuid());
        assertEquals(insertablePlayer, retrievedPlayer);
        dao.delete(insertablePlayer.uuid());
        UuidPlayerEntity removedPlayer = dao.get(insertablePlayer.uuid());
        assertNull(removedPlayer);
    }

    @Test
    public void insertDoubleKeyException() {
        UUID uuid = UUID.randomUUID();
        UuidPlayerEntity playerEntity = new UuidPlayerEntity(uuid, "SigmaMan");
        UuidPlayerEntity playerEntity2 = new UuidPlayerEntity(uuid, "SigmaMan");
        dao.insert(playerEntity);
        try {
            dao.insert(playerEntity2);
        } catch (Throwable ignore) {

        }
        assertEquals(1, dao.getsAll().toList().size());
    }

    @Test
    public void findOneByPredicate() {
        UuidPlayerEntity playerEntity = new UuidPlayerEntity(UUID.randomUUID(), "Valera");
        UuidPlayerEntity playerEntity2 = new UuidPlayerEntity(UUID.randomUUID(), "SigmaMan");
        dao.insert(playerEntity);
        dao.insert(playerEntity2);
        Optional<UuidPlayerEntity> foundPlayer = dao.getsAll().filter(entity->entity.name().equals("Valera")).findFirst();
        assertTrue(foundPlayer.isPresent());
        assertEquals(playerEntity, foundPlayer.get());
    }

    @Test
    public void findMoreByPredicate() {
        UuidPlayerEntity playerEntity = new UuidPlayerEntity(UUID.randomUUID(), "Valera");
        UuidPlayerEntity playerEntity2 = new UuidPlayerEntity(UUID.randomUUID(), "SigmaMan");
        UuidPlayerEntity playerEntity3 = new UuidPlayerEntity(UUID.randomUUID(), "SigmaMan");
        UuidPlayerEntity playerEntity4 = new UuidPlayerEntity(UUID.randomUUID(), "SigmaMan");
        dao.insert(playerEntity);
        dao.insert(playerEntity2);
        dao.insert(playerEntity3);
        dao.insert(playerEntity4);
        List<UuidPlayerEntity> foundPlayers = dao.getsAll().filter(entity->entity.name().equals("SigmaMan")).toList();
        assertEquals(3, foundPlayers.size());
        assertTrue(foundPlayers.stream().allMatch(entity->entity.name().equals("SigmaMan")));
    }

    @Test
    public void upsertAndGet() {
        UuidPlayerEntity playerEntity = new UuidPlayerEntity(UUID.randomUUID(), "Valera");
        dao.upsert(playerEntity);
        UuidPlayerEntity retrievedPlayer = dao.get(playerEntity.uuid());
        assertEquals(playerEntity, retrievedPlayer);
        UuidPlayerEntity newPlayerEntity = new UuidPlayerEntity(playerEntity.uuid(), "Mama");
        dao.upsert(newPlayerEntity);
        retrievedPlayer = dao.get(playerEntity.uuid());
        assertEquals(newPlayerEntity, retrievedPlayer);
    }
}
