package me.thedivazo.libs.database.repo;

import lombok.AllArgsConstructor;
import me.thedivazo.libs.database.dao.Dao;
import me.thedivazo.libs.database.repo.mapper.DTOEntityMapper;
import me.thedivazo.libs.util.IterableUtil;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author TheDiVaZo
 * created on 15.11.2024
 */
@AllArgsConstructor
public class DaoRepository<T, ID, E, D extends Dao<E, ID>> implements CrudRepository<T, ID> {
    protected final D dao;
    protected final DTOEntityMapper<T, E> dtoEntityMapper;
    protected final Function<T, ID> getterId;

    @Override
    public void delete(T dto) {
        dao.delete(getterId.apply(dto));
    }

    @Override
    public void deleteAll() {
        dao.deletesAll();
    }

    @Override
    public void deleteAll(Iterable<? extends T> dtos) {
        dao.deletes(IterableUtil.toStream(dtos).map(getterId).toList());
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
        return dao.getsAll().map(dtoEntityMapper::toDTO).toList();
    }

    @Override
    public Iterable<T> findAllById(Iterable<? extends ID> ids) {
        return dao.gets(ids).map(dtoEntityMapper::toDTO).toList();
    }

    @Override
    public Iterable<T> findAllByPredicate(Predicate<T> predicate) {
        return dao.getsAll().map(dtoEntityMapper::toDTO).filter(predicate).toList();
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(dtoEntityMapper.toDTO(dao.get(id)));
    }

    @Override
    public ID save(T dto) {
        return dao.upsert(dtoEntityMapper.toEntity(dto));
    }

    @Override
    public Iterable<ID> saveAll(Iterable<T> dtos) {
        return dao.upserts(IterableUtil.toStream(dtos).map(dtoEntityMapper::toEntity).toList());
    }
}
