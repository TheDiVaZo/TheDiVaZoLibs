package me.thedivazo.libs.database.repo;

import me.thedivazo.libs.database.promise.EmptyPromise;
import me.thedivazo.libs.database.promise.ResultPromise;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author TheDiVaZo
 * created on 11.11.2024
 */
public interface AsyncCrudRepository<T, ID> extends Repository<T,ID> {
    EmptyPromise delete(T dto);

    EmptyPromise deleteAll();

    EmptyPromise deleteAll(Iterable<? extends T> dtos);

    EmptyPromise deleteAllById(Iterable<? extends ID> ids);

    EmptyPromise deleteById(ID id);

    ResultPromise<Boolean> existsById(ID id);

    ResultPromise<Iterable<T>> findAll();

    ResultPromise<Iterable<T>> findAllById(Iterable<? extends ID> ids);

    ResultPromise<Iterable<T>> findAllByPredicate(Predicate<T> predicate);

    ResultPromise<Optional<T>> findById(ID id);

    ResultPromise<ID> save(T dto);

    ResultPromise<Iterable<ID>> saveAll(Iterable<T> dtos);
}
