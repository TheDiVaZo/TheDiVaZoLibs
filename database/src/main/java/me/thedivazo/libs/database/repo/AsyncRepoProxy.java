package me.thedivazo.libs.database.repo;

import lombok.AllArgsConstructor;
import me.thedivazo.libs.database.promise.EmptyPromise;
import me.thedivazo.libs.database.promise.ResultPromise;
import me.thedivazo.libs.database.promise.factory.EmptyPromiseFactory;
import me.thedivazo.libs.database.promise.factory.ResultPromiseFactory;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author TheDiVaZo
 * created on 11.11.2024
 */
@AllArgsConstructor
public class AsyncRepoProxy<T, ID> implements AsyncCrudRepository<T, ID> {
    protected final CrudRepository<T, ID> proxiedRepository;
    protected final EmptyPromiseFactory emptyPromiseFactory;
    protected final ResultPromiseFactory resultPromiseFactory;

    @Override
    public EmptyPromise delete(T dto) {
        return emptyPromiseFactory.ofPromise(() -> proxiedRepository.delete(dto));
    }

    @Override
    public EmptyPromise deleteAll() {
        return emptyPromiseFactory.ofPromise(proxiedRepository::deleteAll);
    }

    @Override
    public EmptyPromise deleteAll(Iterable<? extends T> dtos) {
        return emptyPromiseFactory.ofPromise(() -> proxiedRepository.deleteAll(dtos));
    }

    @Override
    public EmptyPromise deleteAllById(Iterable<? extends ID> ids) {
        return emptyPromiseFactory.ofPromise(() -> proxiedRepository.deleteAllById(ids));
    }

    @Override
    public EmptyPromise deleteById(ID id) {
        return emptyPromiseFactory.ofPromise(() -> proxiedRepository.deleteById(id));
    }

    @Override
    public ResultPromise<Boolean> existsById(ID id) {
        return resultPromiseFactory.ofPromise(() -> proxiedRepository.existsById(id));
    }

    @Override
    public ResultPromise<Iterable<T>> findAll() {
        return resultPromiseFactory.ofPromise(proxiedRepository::findAll);
    }

    @Override
    public ResultPromise<Iterable<T>> findAllById(Iterable<? extends ID> ids) {
        return resultPromiseFactory.ofPromise(() -> proxiedRepository.findAllById(ids));
    }

    @Override
    public ResultPromise<Iterable<T>> findAllByPredicate(Predicate<T> predicate) {
        return resultPromiseFactory.ofPromise(() -> proxiedRepository.findAllByPredicate(predicate));
    }

    @Override
    public ResultPromise<Optional<T>> findById(ID id) {
        return resultPromiseFactory.ofPromise(() -> proxiedRepository.findById(id));
    }

    @Override
    public ResultPromise<ID> save(T dto) {
        return resultPromiseFactory.ofPromise(() -> proxiedRepository.save(dto));
    }

    @Override
    public ResultPromise<Iterable<ID>> saveAll(Iterable<T> dtos) {
        return resultPromiseFactory.ofPromise(() -> proxiedRepository.saveAll(dtos));
    }
}
