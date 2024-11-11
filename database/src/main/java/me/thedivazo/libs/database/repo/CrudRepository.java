package me.thedivazo.libs.database.repo;

import me.thedivazo.libs.database.dao.Dao;
import me.thedivazo.libs.database.promise.ResultPromise;

import java.util.Optional;
import java.util.function.Predicate;

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
 * доступ к базе и это не безопастно.
 *
 * А метод findBy(DtoCondition condition), принимающий некий объект отвечающий за
 * фильтрацию поиска, можно, т.к. доступ к базе контролируется и такой способ
 * является безопасным.
 *
 * @param <T> DTO объект
 * @param <ID> Объект идентификатора
 */
public interface CrudRepository<T,ID> extends Repository<T, ID> {
    void delete(T dto);

    void deleteAll();

    void deleteAll(Iterable<? extends T> dtos);

    void deleteAllById(Iterable<? extends ID> ids);

    void deleteById(ID id);

    boolean existsById(ID id);

    Iterable<T> findAll();

    Iterable<T> findAllById(Iterable<? extends ID> ids);

    Iterable<T> findAllByPredicate(Predicate<T> predicate);

    Optional<T> findById(ID id);

    ID save(T dto);

    Iterable<ID> saveAll(Iterable<T> dtos);
}
