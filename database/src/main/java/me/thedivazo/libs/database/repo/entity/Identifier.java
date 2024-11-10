package me.thedivazo.libs.database.repo.entity;

public interface Identifier<T> {
    boolean isIdentical(T first, T second);
}
