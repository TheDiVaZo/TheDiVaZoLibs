package me.thedivazo.libs.database.repo;

import lombok.RequiredArgsConstructor;
import me.thedivazo.libs.database.repo.entity.EntityIdentifier;
import me.thedivazo.libs.database.repo.entity.NextIdGenerator;
import me.thedivazo.libs.util.Cloner;
import me.thedivazo.libs.util.IterableUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

/**
 * Репозиторий, использующий ConcurrentSkipListMap в качестве хранилища.
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
public class MapMemoryRepo<T, ID> implements CRUDRepository<T, ID>, PagingRepository<T, ID> {

    private final Map<ID, T> localStorage = new ConcurrentSkipListMap<>();
    private final NextIdGenerator<ID> nextIdGenerator;
    private final EntityIdentifier<T> entityIdentifier;
    private final Cloner<T> cloner;

    @Override
    public long count() {
        return localStorage.size();
    }

    @Override
    public void delete(T entity) {
        localStorage.values().remove(entity);
    }

    @Override
    public void deleteAll() {
        localStorage.clear();
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        localStorage.values().removeAll(IterableUtil.toCollection(entities));
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
        return localStorage.values().stream().map(cloner::<T>clone).collect(Collectors.toList());
    }

    @Override
    public Iterable<T> findAllById(Iterable<? extends ID> ids) {
        Collection<? extends ID> idCollection = IterableUtil.toCollection(ids);
        return localStorage.entrySet().stream()
                .filter(entry -> idCollection.contains(entry.getKey()))
                .map(entry->cloner.<T>clone(entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(localStorage.get(id)).map(cloner::clone);
    }

    @Override
    public <E extends T> E save(E entity) {
        E clonedEntity = cloner.clone(entity);
        Optional<Map.Entry<ID, T>> idtEntry = localStorage.entrySet().stream()
                .filter(entry -> entityIdentifier.isIdentical(entity, entry.getValue()))
                .findAny();
        if (idtEntry.isPresent()) {
            idtEntry.get().setValue(clonedEntity);
        } else {
            ID id = nextIdGenerator.generateNextId();
            localStorage.put(id, clonedEntity);
        }
        return cloner.clone(clonedEntity);
    }

    @Override
    public <E extends T> Iterable<E> saveAll(Iterable<E> entities) {
        return IterableUtil.toStream(entities)
               .map(entity->cloner.<E>clone(save(entity)))
               .collect(Collectors.toList());
    }

    @Override
    public Iterable<T> findAll(int pageNumber, int pageSize) {
        return localStorage.entrySet().stream()
                .skip((long) (pageNumber - 1) * pageSize)
                .limit(pageSize)
                .map(entry->cloner.<T>clone(entry.getValue()))
                .collect(Collectors.toList());
    }
}
