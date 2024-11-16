package me.thedivazo.libs.tests.database.dao;

import lombok.NoArgsConstructor;
import me.thedivazo.libs.database.dao.Dao;
import me.thedivazo.libs.tests.database.dao.entity.PlayerEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TheDiVaZo
 * created on 16.11.2024
 */
@NoArgsConstructor()
public abstract class AbstractDaoTest {
    protected static Dao<PlayerEntity, UUID> dao;

    @AfterEach
    public void cleanUp() {
        dao.deletesAll();
    }

    @Test
    public void insertAndGet() {
        PlayerEntity insertablePlayer = PlayerEntity.generate();
        dao.insert(insertablePlayer);
        PlayerEntity retrievedPlayer = dao.get(insertablePlayer.uuid());
        assertEquals(insertablePlayer, retrievedPlayer);
    }

    @Test
    public void insertAndDelete() {
        PlayerEntity insertablePlayer = PlayerEntity.generate();
        dao.insert(insertablePlayer);
        PlayerEntity retrievedPlayer = dao.get(insertablePlayer.uuid());
        assertEquals(insertablePlayer, retrievedPlayer);
        dao.delete(insertablePlayer.uuid());
        PlayerEntity removedPlayer = dao.get(insertablePlayer.uuid());
        assertNull(removedPlayer);
    }

    @Test
    public void insertDoubleKeyException() {
        UUID uuid = UUID.randomUUID();
        PlayerEntity playerEntity = new PlayerEntity(uuid, "SigmaMan");
        PlayerEntity playerEntity2 = new PlayerEntity(uuid, "SigmaMan");
        dao.insert(playerEntity);
        try {
            dao.insert(playerEntity2);
        } catch (Throwable ignore) {

        }
        assertEquals(1, dao.getsAll().toList().size());
    }

    @Test
    public void findOneByPredicate() {
        PlayerEntity playerEntity = new PlayerEntity(UUID.randomUUID(), "Valera");
        PlayerEntity playerEntity2 = new PlayerEntity(UUID.randomUUID(), "SigmaMan");
        dao.insert(playerEntity);
        dao.insert(playerEntity2);
        Optional<PlayerEntity> foundPlayer = dao.getsAll().filter(entity->entity.name().equals("Valera")).findFirst();
        assertTrue(foundPlayer.isPresent());
        assertEquals(playerEntity, foundPlayer.get());
    }

    @Test
    public void findMoreByPredicate() {
        PlayerEntity playerEntity = new PlayerEntity(UUID.randomUUID(), "Valera");
        PlayerEntity playerEntity2 = new PlayerEntity(UUID.randomUUID(), "SigmaMan");
        PlayerEntity playerEntity3 = new PlayerEntity(UUID.randomUUID(), "SigmaMan");
        PlayerEntity playerEntity4 = new PlayerEntity(UUID.randomUUID(), "SigmaMan");
        dao.insert(playerEntity);
        dao.insert(playerEntity2);
        dao.insert(playerEntity3);
        dao.insert(playerEntity4);
        List<PlayerEntity> foundPlayers = dao.getsAll().filter(entity->entity.name().equals("SigmaMan")).toList();
        assertEquals(3, foundPlayers.size());
        assertTrue(foundPlayers.stream().allMatch(entity->entity.name().equals("SigmaMan")));
    }
}
