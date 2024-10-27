package me.thedivazo.libs.database.repo;

/**
 * @author TheDiVaZo
 * created on 27.10.2024
 */
public interface PagingRepository<T, ID> extends Repository<T, ID> {
    Iterable<T> findAll(int pageNumber, int pageSize);
}
