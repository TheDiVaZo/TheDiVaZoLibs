package me.thedivazo.libs.database.dao;

import org.jooq.Condition;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author TheDiVaZo
 * created on 08.11.2024
 **/
public interface ConditionJooqDao<T, ID> extends Dao<T, ID> {
    void deleteAll(Condition condition);

    Stream<T> getAllInStream(Condition condition);

    Optional<T> findOne(Condition predicate);
}
