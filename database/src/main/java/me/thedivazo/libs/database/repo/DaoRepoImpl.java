package me.thedivazo.libs.database.repo;

import lombok.AllArgsConstructor;
import me.thedivazo.libs.database.dao.Dao;

import java.util.Optional;

/**
 * @author TheDiVaZo
 * created on 09.11.2024
 */
@AllArgsConstructor
public class DaoRepoImpl<T, ID> implements Repository<T, ID> {
    protected final Dao<T, ID> dao;

    @Override
    public void delete(T entity) {
        deleteById(dao.getId(entity));
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public void deleteById(ID id) {
        dao.delete(id);
    }

    @Override
    public boolean existsById(ID id) {
        return dao.get(id) != null;
    }

    @Override
    public Iterable<T> findAll() {
        return  dao.getAll()::iterator;
    }

    @Override
    public Iterable<T> findAllById(Iterable<? extends ID> ids) {
        return dao.getAll().filter(entity-> entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.empty();
    }

    @Override
    public <E extends T> E save(E entity) {
        return null;
    }

    @Override
    public <E extends T> Iterable<E> saveAll(Iterable<E> entities) {
        return null;
    }
}
