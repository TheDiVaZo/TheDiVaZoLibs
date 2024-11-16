package me.thedivazo.libs.tests.database.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Random;
import java.util.UUID;

/**
 * @author TheDiVaZo
 * created on 16.11.2024
 */
public record PlayerEntity(UUID uuid, String name) {
    private static final String[] names = {"Valera", "Oleg", "Misha", "Nastya", "Igor", "Natasha", "Ruslan", "Dima", "Kirill", "Den"};
    private static final Random random = new Random();

    private static String getRandomName() {
        return names[random.nextInt(names.length)];
    }

    public static PlayerEntity generate() {
        return new PlayerEntity(UUID.randomUUID(), getRandomName());
    }
}
