package me.thedivazo.libs.database.repo;

import lombok.RequiredArgsConstructor;
import me.thedivazo.libs.database.repo.entity.Identifier;
import me.thedivazo.libs.database.repo.entity.NextIdGenerator;
import me.thedivazo.libs.util.Cloner;
import me.thedivazo.libs.util.IterableUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * Репозиторий, использующий ConcurrentHashMap в качестве хранилища.
 * При добавлении/изменении сущности, в базу сохраняется ее независимая клонированная версия
 * При получении сущности, вы так же получаете независимый от сущности в хранилище клон
 * Это нужно для безопасности и эмуляции базы данных
 *
 * Почему бы принудительно не требовать расширение интерфейса {@link T} от {@link Cloneable}?
 * Потому что реальный репозиторий по факту сам отображает объект в базу данных.
 * Аналогом этого отображения выступает {@link Cloner<T>}
 * @param <T> Сущность
 * @param <ID> Идентификатор сущности
 */
@RequiredArgsConstructor
public class MapMemoryRepo<T, ID> implements CrudRepository<T, ID> {

    private final Map<ID, T> localStorage = new ConcurrentHashMap<>();
    private final NextIdGenerator<ID> nextIdGenerator;
    private final Identifier<T> identifier;
    private final Cloner<T> cloner;


    @Override
    public void delete(T dto) {
        localStorage.values().remove(dto);
    }

    @Override
    public void deleteAll() {
        localStorage.clear();
    }

    @Override
    public void deleteAll(Iterable<? extends T> dtos) {
        localStorage.values().removeAll(IterableUtil.toCollection(dtos));
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        localStorage.keySet().removeAll(IterableUtil.toCollection(ids));
    }

    @Override
    public void deleteById(ID id) {
        localStorage.remove(id);
    }

    @Override
    public boolean existsById(ID id) {
        return localStorage.containsKey(id);
    }

    @Override
    public Iterable<T> findAll() {
        return localStorage.values().stream().map(cloner::<T>clone).toList();
    }

    @Override
    public Iterable<T> findAllById(Iterable<? extends ID> ids) {
        Collection<? extends ID> idCollection = IterableUtil.toCollection(ids);
        return localStorage.entrySet().stream()
                .filter(entry -> idCollection.contains(entry.getKey()))
                .map(entry->cloner.<T>clone(entry.getValue()))
                .toList();
    }

    @Override
    public Iterable<T> findAllByPredicate(Predicate<T> predicate) {
        return localStorage.values().stream().filter(predicate).toList();
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(localStorage.get(id)).map(cloner::clone);
    }

    @Override
    public ID save(T entity) {
        T clonedEntity = cloner.clone(entity);
        Optional<Map.Entry<ID, T>> idtEntry = localStorage.entrySet().stream()
                .filter(entry -> identifier.isIdentical(entity, entry.getValue()))
                .findAny();
        if (idtEntry.isPresent()) {
            idtEntry.get().setValue(clonedEntity);
            return idtEntry.get().getKey();
        } else {
            ID id = nextIdGenerator.generateNextId();
            localStorage.put(id, clonedEntity);
            return id;
        }
    }

    @Override
    public Iterable<ID> saveAll(Iterable<T> entities) {
        return IterableUtil.toStream(entities)
               .map(this::save)
               .toList();
    }
}
