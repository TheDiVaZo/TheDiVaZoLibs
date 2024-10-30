package me.thedivazo.libs.util;

public interface Cloner<T> {
    <E extends T> E clone(T original);
}
