package me.thedivazo.libs.database.dao;

import org.jetbrains.annotations.Nullable;
import org.jooq.Condition;

import java.util.stream.Stream;

/**
 * @author TheDiVaZo
 * created on 08.11.2024
 **/
public interface ConditionJooqDao<T, ID> extends Dao<T, ID> {
    int deletesBy(Condition condition);

    Stream<T> getsBy(Condition condition);

    @Nullable
    T getBy(Condition predicate);
}
