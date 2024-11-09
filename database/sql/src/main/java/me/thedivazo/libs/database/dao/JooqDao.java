package me.thedivazo.libs.database.dao;

import me.thedivazo.libs.database.sql.connection.ConnectionPool;
import me.thedivazo.libs.util.LazyUncheckedSpliterator;
import org.jetbrains.annotations.Nullable;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.apache.commons.dbutils.DbUtils.close;

/**
 * @author TheDiVaZo
 * created on 08.11.2024
 * <p>
 * Реализация Dao с использованием jOOQ DLS.
 * Поддерживает поиск сущностей с фильтрами.
 * Используйте только если у вас нет кодогенератора jOOQ
 * В противном случае, расширение этого абстрактного бессмысленно, т.к.
 * jOOQ предоставляет свой Dao интерфейс {@link DAO} и генерирует на его основе
 * Dao классы для ваших сущностей.
 **/
public abstract class JooqDao<T, ID> implements ConditionJooqDao<T, ID> {
    protected final ConnectionPool pool;
    protected final SQLDialect sqlDialect;
    protected final RecordMapper<? super Record, T> recordMapper;
    protected final Table<?> tableName;
    protected final Field<ID> keyIdentifier;

    protected JooqDao(Table<?> tableName, Field<ID> keyIdentifier, ConnectionPool pool, SQLDialect sqlDialect, RecordMapper<? super Record, T> recordMapper) {
        this.pool = pool;
        this.sqlDialect = sqlDialect;
        this.recordMapper = recordMapper;
        this.tableName = tableName;
        this.keyIdentifier = keyIdentifier;
    }

    protected JooqDao(String tableName, String keyIdentifier, Class<ID> clazzId, ConnectionPool pool, SQLDialect sqlDialect, RecordMapper<? super Record, T> recordMapper) {
        this(DSL.table(DSL.name(tableName)), DSL.field(DSL.name(keyIdentifier), clazzId), pool, sqlDialect, recordMapper);
    }

    protected DSLContext getDslContext(Connection connection) {
        return DSL.using(connection, sqlDialect);
    }

    @Override
    public void deleteAll(Condition condition) {
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            dslContext.deleteFrom(tableName).where(condition).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<T> getAllInStream(Condition condition) {
        try {
            final Connection connection = pool.getConnection();
            final DSLContext dslContext = getDslContext(connection);
            Cursor<?> cursor = dslContext.selectFrom(tableName).where(condition).fetchLazy();
            return StreamSupport.stream(new LazyUncheckedSpliterator<T>(action -> {
                if (!cursor.hasNext()) {
                    close(connection);
                    cursor.close();
                    return false;
                }
                final T value = cursor.fetchNext(recordMapper);
                action.accept(value);
                return true;

            }), false).onClose(() -> {
                try {
                    cursor.close();
                    close(connection);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> findOne(@Nullable Condition predicate) {
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            Select<?> query = dslContext.selectFrom(tableName);
            if (predicate != null) {
                query = ((SelectWhereStep<?>) query).where(predicate);
            }
            T entity = query.fetchOne(recordMapper);
            return Optional.ofNullable(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            dslContext.deleteFrom(tableName).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<T> getAllInStream() {
        return getAllInStream(null);
    }

    @Override
    public void delete(ID key) {
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            dslContext.deleteFrom(tableName).where(keyIdentifier.eq(key)).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
