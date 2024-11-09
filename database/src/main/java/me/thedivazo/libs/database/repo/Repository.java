package me.thedivazo.libs.database.repo;

import me.thedivazo.libs.database.dao.Dao;

import java.util.Optional;
/**
 * @author TheDiVaZo
 * created on 27.10.2024
 *
 * Интерфейс, инкапсулирующий работу с базой (через объект  {@link Dao>})
 * В своих методах может содержать бизнес-логику.
 *
 * Не должен предоставлять прямой доступ к базе данных.
 * Например: создание метода findBy(String sql) который принимает строку sql
 * нельзя, т.к. при таком подходе репозиторий не сможет контролировать
 * доступ к базе и это не типобезопастно.
 *
 * А метод findBy(EntityCondition condition), принимающий некий объект отвечающий за
 * фильтрацию поиска, можно, т.к. доступ к базе контролируется и такой способ
 * является типобезопастным.
 */
public interface Repository<T,ID> {
    void delete(T entity);

    void deleteAll();

    void deleteAll(Iterable<? extends T> entities);

    void deleteAllById(Iterable<? extends ID> ids);

    void deleteById(ID id);

    boolean existsById(ID id);

    Iterable<T> findAll();

    Iterable<T> findAllById(Iterable<? extends ID> ids);

    Optional<T> findById(ID id);

    void save(T entity);

    void saveAll(Iterable<T> entities);
}
