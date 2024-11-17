package me.thedivazo.libs.tests.database.dao;

import me.thedivazo.libs.database.dao.Dao;
import me.thedivazo.libs.tests.database.dao.entity.BinaryIdPlayerEntity;
import org.junit.jupiter.api.AfterEach;
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
public abstract class BinaryAbstractDaoTest {
    protected static Dao<BinaryIdPlayerEntity, byte[]> dao;

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
        BinaryIdPlayerEntity insertablePlayer = new BinaryIdPlayerEntity(toBytes(UUID.fromString("00000000-0000-0000-0000-000000000000")), "Abobus");
        dao.insert(insertablePlayer);
        BinaryIdPlayerEntity retrievedPlayer = dao.get(insertablePlayer.uuidBytes());
        assertEquals(insertablePlayer, retrievedPlayer);
    }

    @Test
    public void insertAndDelete() {
        BinaryIdPlayerEntity insertablePlayer = BinaryIdPlayerEntity.generate();
        dao.insert(insertablePlayer);
        BinaryIdPlayerEntity retrievedPlayer = dao.get(insertablePlayer.uuidBytes());
        assertEquals(insertablePlayer, retrievedPlayer);
        dao.delete(insertablePlayer.uuidBytes());
        BinaryIdPlayerEntity removedPlayer = dao.get(insertablePlayer.uuidBytes());
        assertNull(removedPlayer);
    }

    @Test
    public void insertDoubleKeyException() {
        UUID uuid = UUID.randomUUID();
        BinaryIdPlayerEntity playerEntity = new BinaryIdPlayerEntity(toBytes(uuid), "SigmaMan");
        BinaryIdPlayerEntity playerEntity2 = new BinaryIdPlayerEntity(toBytes(uuid), "SigmaMan");
        dao.insert(playerEntity);
        try {
            dao.insert(playerEntity2);
        } catch (Throwable ignore) {

        }
        assertEquals(1, dao.getsAll().toList().size());
    }

    @Test
    public void findOneByPredicate() {
        BinaryIdPlayerEntity playerEntity = new BinaryIdPlayerEntity(toBytes(UUID.randomUUID()), "Valera");
        BinaryIdPlayerEntity playerEntity2 = new BinaryIdPlayerEntity(toBytes(UUID.randomUUID()), "SigmaMan");
        dao.insert(playerEntity);
        dao.insert(playerEntity2);
        Optional<BinaryIdPlayerEntity> foundPlayer = dao.getsAll().filter(entity->entity.name().equals("Valera")).findFirst();
        assertTrue(foundPlayer.isPresent());
        assertEquals(playerEntity, foundPlayer.get());
    }

    @Test
    public void findMoreByPredicate() {
        BinaryIdPlayerEntity playerEntity = new BinaryIdPlayerEntity(toBytes(UUID.randomUUID()), "Valera");
        BinaryIdPlayerEntity playerEntity2 = new BinaryIdPlayerEntity(toBytes(UUID.randomUUID()), "SigmaMan");
        BinaryIdPlayerEntity playerEntity3 = new BinaryIdPlayerEntity(toBytes(UUID.randomUUID()), "SigmaMan");
        BinaryIdPlayerEntity playerEntity4 = new BinaryIdPlayerEntity(toBytes(UUID.randomUUID()), "SigmaMan");
        dao.insert(playerEntity);
        dao.insert(playerEntity2);
        dao.insert(playerEntity3);
        dao.insert(playerEntity4);
        List<BinaryIdPlayerEntity> foundPlayers = dao.getsAll().filter(entity->entity.name().equals("SigmaMan")).toList();
        assertEquals(3, foundPlayers.size());
        assertTrue(foundPlayers.stream().allMatch(entity->entity.name().equals("SigmaMan")));
    }
}
