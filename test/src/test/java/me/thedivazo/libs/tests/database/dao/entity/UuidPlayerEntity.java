package me.thedivazo.libs.tests.database.dao.entity;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

/**
 * @author TheDiVaZo
 * created on 18.11.2024
 */
public record UuidPlayerEntity(UUID uuid, String name) {
    private static final String[] names = {"Valera", "Oleg", "Misha", "Nastya", "Igor", "Natasha", "Ruslan", "Dima", "Kirill", "Den"};
    private static final Random random = new Random();

    private static String getRandomName() {
        return names[random.nextInt(names.length)];
    }

    public static UuidPlayerEntity generate() {
        UUID uuid = UUID.randomUUID();
        return new UuidPlayerEntity(uuid, getRandomName());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        UuidPlayerEntity that = (UuidPlayerEntity) object;

        if (!uuid.equals(that.uuid)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
