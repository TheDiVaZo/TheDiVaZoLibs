package me.thedivazo.libs.database.repo;

import java.util.Optional;

/**
 * @author TheDiVaZo
 * created on 27.10.2024
 */
public interface CRUDRepository<T, ID> {
    long count();
    void delete(T entity);
    void deleteAll();
    void deleteAll(Iterable<? extends T> entities);
    void deleteAllById(Iterable<? extends T> ids);
    void deleteById(ID id);
    boolean existsById(ID id);
    Iterable<T> findAll();
    Iterable<T> findAllById(Iterable<? extends ID> ids);
    Optional<T> findById(ID id);
    <E extends T> E save(E entity);
    <E extends T> Iterable<E> saveAll(Iterable<E> entities);
}
