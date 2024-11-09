package me.thedivazo.libs.database.dao;

import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

/**
 * @author TheDiVaZo
 * created on 08.11.2024
 * <p>
 * Представляет собой интерфейс для прямого доступа и взаимодействия с базой данных
 *
 * @param <T> Entity объект. Представляет собой отображение записи в базе
 * @param <ID> Объект идентификатора
 **/
public interface Dao<T, ID> {
    ID insert(T entity);
    Iterable<ID> inserts(Iterable<? extends T> entities);

    ID upsert(T entity);
    Iterable<ID> upserts(Iterable<? extends T> entities);

    boolean delete(ID key);
    int deletes(Iterable<? extends ID> keys);
    int deletesAll();

    boolean update(T entity);
    int updates(Iterable<? extends T> entities);

    @Nullable
    T get(ID id);

    /**
     * Реализация данного метода возвращает {@link Stream<T>},
     * который лениво получает данные из базы и не закрывает соединение до тех пор,
     * пока будет явно не вызван метод {@link Stream#close()}
     * или пока данные сами не закончат поступать.
     * @param ids объект содержащий идентификаторы.
     * @return стрим с сущностями в порядке получения идентификаторов из аргумента. Если сущность не найдена, она будет остутствовать в итераторе.
     */
    Stream<T> gets(Iterable<? extends ID> ids);

    ID getId(T entity);

    /**
     * Реализация данного метода возвращает {@link Stream<T>},
     * который лениво получает данные из базы и не закрывает соединение до тех пор,
     * пока будет явно не вызван метод {@link Stream#close()}
     * или пока данные сами не закончат поступать
     *
     * @return {@link Stream<T>} с сущностями
     */
    Stream<T> getsAll();
}
