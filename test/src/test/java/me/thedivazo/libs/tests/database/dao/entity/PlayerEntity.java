package me.thedivazo.libs.tests.database.dao.entity;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

/**
 * @author TheDiVaZo
 * created on 16.11.2024
 */
public record PlayerEntity(byte[] uuidBytes, String name) {
    private static final String[] names = {"Valera", "Oleg", "Misha", "Nastya", "Igor", "Natasha", "Ruslan", "Dima", "Kirill", "Den"};
    private static final Random random = new Random();

    private static String getRandomName() {
        return names[random.nextInt(names.length)];
    }

    public static PlayerEntity generate() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return new PlayerEntity(byteBuffer.array(), getRandomName());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        PlayerEntity player = (PlayerEntity) object;

        if (!Arrays.equals(uuidBytes, player.uuidBytes)) return false;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(uuidBytes);
        result = 31 * result + name.hashCode();
        return result;
    }
}
