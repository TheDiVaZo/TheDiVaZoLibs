package me.thedivazo.libs.database.repo;

import lombok.AllArgsConstructor;
import me.thedivazo.libs.database.dao.Dao;
import me.thedivazo.libs.util.IterableUtil;

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
        deleteAllById(IterableUtil.toStream(entities).map(dao::getId).toList());
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        dao.deletes(ids);
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
        return dao.gets(ids).toList();
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(dao.get(id));
    }

    @Override
    public void save(T entity) {
        dao.upsert(entity);
    }

    @Override
    public void saveAll(Iterable<T> entities) {
        dao.upserts(entities);
    }
}
