package me.thedivazo.libs.database.repo.entity;

public interface EntityIdentifier<T> {
    boolean isIdentical(T firstEntity, T secondEntity);
}
