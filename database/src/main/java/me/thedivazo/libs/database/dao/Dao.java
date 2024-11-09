package me.thedivazo.libs.database.dao;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author TheDiVaZo
 * created on 08.11.2024
 * <p>
 * Представляет собой интерфейс для прямого доступа и взаимодействия с базой данных
 **/
public interface Dao<T, ID> {
    void insert(T entity);

    void upsert(T entity);

    void delete(ID key);

    void update(T entity);

    void deleteAll();

    @Nullable
    T get(ID key);

    ID getId(T entity);

    /**
     * Реализация данного метода возвращает {@link Stream<T>},
     * который лениво получает данные из базы и не закрывает соединение до тех пор,
     * пока будет явно не вызван метод {@link Stream#close()}
     * или пока данные сами не закончат поступать
     *
     * @return {@link Stream<T>} с сущностями
     */
    Stream<T> getAllInStream();

    default Optional<T> findOne(Predicate<T> predicate) {
        return getAllInStream().filter(predicate).findFirst();
    }
}
