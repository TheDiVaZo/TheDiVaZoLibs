package me.thedivazo.libs.tests.database.dao;

import lombok.NoArgsConstructor;
import me.thedivazo.libs.database.dao.Dao;
import me.thedivazo.libs.tests.database.dao.entity.PlayerEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
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
    protected static Dao<PlayerEntity, byte[]> dao;

    @AfterEach
    public void cleanUp() {
        dao.deletesAll();
    }

    protected byte[] toBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

    protected UUID fromBytes(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }

    @Test
    public void insertAndGet() {
        PlayerEntity insertablePlayer = new PlayerEntity(toBytes(UUID.fromString("00000000-0000-0000-0000-000000000000")), "Abobus");
        dao.insert(insertablePlayer);
        PlayerEntity retrievedPlayer = dao.get(insertablePlayer.uuidBytes());
        assertEquals(insertablePlayer, retrievedPlayer);
    }

    @Test
    public void insertAndDelete() {
        PlayerEntity insertablePlayer = PlayerEntity.generate();
        dao.insert(insertablePlayer);
        PlayerEntity retrievedPlayer = dao.get(insertablePlayer.uuidBytes());
        assertEquals(insertablePlayer, retrievedPlayer);
        dao.delete(insertablePlayer.uuidBytes());
        PlayerEntity removedPlayer = dao.get(insertablePlayer.uuidBytes());
        assertNull(removedPlayer);
    }

    @Test
    public void insertDoubleKeyException() {
        UUID uuid = UUID.randomUUID();
        PlayerEntity playerEntity = new PlayerEntity(toBytes(uuid), "SigmaMan");
        PlayerEntity playerEntity2 = new PlayerEntity(toBytes(uuid), "SigmaMan");
        dao.insert(playerEntity);
        try {
            dao.insert(playerEntity2);
        } catch (Throwable ignore) {

        }
        assertEquals(1, dao.getsAll().toList().size());
    }

    @Test
    public void findOneByPredicate() {
        PlayerEntity playerEntity = new PlayerEntity(toBytes(UUID.randomUUID()), "Valera");
        PlayerEntity playerEntity2 = new PlayerEntity(toBytes(UUID.randomUUID()), "SigmaMan");
        dao.insert(playerEntity);
        dao.insert(playerEntity2);
        Optional<PlayerEntity> foundPlayer = dao.getsAll().filter(entity->entity.name().equals("Valera")).findFirst();
        assertTrue(foundPlayer.isPresent());
        assertEquals(playerEntity, foundPlayer.get());
    }

    @Test
    public void findMoreByPredicate() {
        PlayerEntity playerEntity = new PlayerEntity(toBytes(UUID.randomUUID()), "Valera");
        PlayerEntity playerEntity2 = new PlayerEntity(toBytes(UUID.randomUUID()), "SigmaMan");
        PlayerEntity playerEntity3 = new PlayerEntity(toBytes(UUID.randomUUID()), "SigmaMan");
        PlayerEntity playerEntity4 = new PlayerEntity(toBytes(UUID.randomUUID()), "SigmaMan");
        dao.insert(playerEntity);
        dao.insert(playerEntity2);
        dao.insert(playerEntity3);
        dao.insert(playerEntity4);
        List<PlayerEntity> foundPlayers = dao.getsAll().filter(entity->entity.name().equals("SigmaMan")).toList();
        assertEquals(3, foundPlayers.size());
        assertTrue(foundPlayers.stream().allMatch(entity->entity.name().equals("SigmaMan")));
    }
}
