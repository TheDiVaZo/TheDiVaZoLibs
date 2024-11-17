package me.thedivazo.libs.util;

/**
 * @author TheDiVaZo
 * created on 17.11.2024
 */
public interface Mapper<T, T1> {
    T mapTo(T1 value);
    T1 mapFrom(T value);
}
