package me.thedivazo.libs.database.repo;

import lombok.AllArgsConstructor;
import me.thedivazo.libs.database.promise.EmptyPromise;
import me.thedivazo.libs.database.promise.ResultPromise;
import me.thedivazo.libs.database.promise.pipeline.PromiseEmptyPipeline;
import me.thedivazo.libs.database.promise.pipeline.PromiseResultPipeline;
import me.thedivazo.libs.util.execut.AsyncThreadPool;
import me.thedivazo.libs.util.execut.SyncExecutor;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 11.11.2024
 */
@AllArgsConstructor
public class AsyncRepoProxy<T, ID> implements AsyncCrudRepository<T, ID> {
    protected final CrudRepository<T, ID> proxiedRepository;
    private final SyncExecutor syncExecutor;
    private final AsyncThreadPool asyncThreadPool;
    private final Logger logger;

    protected EmptyPromise runInEmptyPromise(Runnable runnable) {
        return new PromiseEmptyPipeline<>(CompletableFuture.runAsync(runnable), syncExecutor, asyncThreadPool, logger);
    }

    protected <E> ResultPromise<E> runInResultPromise(Supplier<E> supplier) {
        CompletableFuture<E> future = CompletableFuture.supplyAsync(supplier);
        return new PromiseResultPipeline<>(future, syncExecutor, asyncThreadPool, logger);
    }

    @Override
    public EmptyPromise delete(T dto) {
        return runInEmptyPromise(()->proxiedRepository.delete(dto));
    }

    @Override
    public EmptyPromise deleteAll() {
        return runInEmptyPromise(proxiedRepository::deleteAll);
    }

    @Override
    public EmptyPromise deleteAll(Iterable<? extends T> dtos) {
        return runInEmptyPromise(()->proxiedRepository.deleteAll(dtos));
    }

    @Override
    public EmptyPromise deleteAllById(Iterable<? extends ID> ids) {
        return runInEmptyPromise(()->proxiedRepository.deleteAllById(ids));
    }

    @Override
    public EmptyPromise deleteById(ID id) {
        return runInEmptyPromise(()->proxiedRepository.deleteById(id));
    }

    @Override
    public ResultPromise<Boolean> existsById(ID id) {
        return runInResultPromise(()->proxiedRepository.existsById(id));
    }

    @Override
    public ResultPromise<Iterable<T>> findAll() {
        return runInResultPromise(proxiedRepository::findAll);
    }

    @Override
    public ResultPromise<Iterable<T>> findAllById(Iterable<? extends ID> ids) {
        return runInResultPromise(()->proxiedRepository.findAllById(ids));
    }

    @Override
    public ResultPromise<Iterable<T>> findAllByPredicate(Predicate<T> predicate) {
        return runInResultPromise(()->proxiedRepository.findAllByPredicate(predicate));
    }

    @Override
    public ResultPromise<Optional<T>> findById(ID id) {
        return runInResultPromise(()->proxiedRepository.findById(id));
    }

    @Override
    public ResultPromise<ID> save(T dto) {
        return runInResultPromise(()->proxiedRepository.save(dto));
    }

    @Override
    public ResultPromise<Iterable<ID>> saveAll(Iterable<T> dtos) {
        return runInResultPromise(()->proxiedRepository.saveAll(dtos));
    }
}
