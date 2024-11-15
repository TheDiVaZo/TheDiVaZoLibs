package me.thedivazo.libs.database.dao;

import me.thedivazo.libs.database.sql.connection.ConnectionPool;
import me.thedivazo.libs.util.IterableUtil;
import me.thedivazo.libs.database.util.LazyCheckedSpliterator;
import org.jetbrains.annotations.Nullable;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
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
    protected final Class<ID> idClass;

    protected JooqDao(Table<?> tableName, Field<ID> keyIdentifier, Class<ID> idClass, ConnectionPool pool, SQLDialect sqlDialect, RecordMapper<? super Record, T> recordMapper) {
        this.pool = pool;
        this.sqlDialect = sqlDialect;
        this.recordMapper = recordMapper;
        this.tableName = tableName;
        this.keyIdentifier = keyIdentifier;
        this.idClass = idClass;
    }

    protected JooqDao(String tableName, String keyIdentifier, Class<ID> idClass, ConnectionPool pool, SQLDialect sqlDialect, RecordMapper<? super Record, T> recordMapper) {
        this(DSL.table(DSL.name(tableName)), DSL.field(DSL.name(keyIdentifier), idClass), idClass, pool, sqlDialect, recordMapper);
    }

    protected DSLContext getDslContext(Connection connection) {
        return DSL.using(connection, sqlDialect);
    }

    @Override
    public @Nullable T get(ID id) {
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            return dslContext.selectFrom(tableName).where(keyIdentifier.eq(id)).fetchOne(recordMapper);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<@Nullable T> gets(Iterable<? extends ID> ids) {
        return getsBy(keyIdentifier.in(IterableUtil.toArray(ids, idClass)));
    }

    @Override
    public T getBy(@Nullable Condition predicate) {
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            Select<?> query = dslContext.selectFrom(tableName);
            if (predicate != null) {
                query = ((SelectWhereStep<?>) query).where(predicate);
            }
            return query.fetchOne(recordMapper);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<T> getsBy(Condition condition) {
        try {
            final Connection connection = pool.getConnection();
            final DSLContext dslContext = getDslContext(connection);
            Cursor<?> cursor = dslContext.selectFrom(tableName).where(condition).fetchLazy();
            return StreamSupport.stream(new LazyCheckedSpliterator<T, SQLException>(action -> {
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
    public Stream<T> getsAll() {
        return getsBy(null);
    }

    @Override
    public boolean delete(ID key) {
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            int count = dslContext.deleteFrom(tableName).where(keyIdentifier.eq(key)).execute();
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deletes(Iterable<? extends ID> ids) {
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            return dslContext.deleteFrom(tableName).where(keyIdentifier.in(IterableUtil.toArray(ids, idClass))).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deletesAll() {
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            return dslContext.deleteFrom(tableName).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deletesBy(Condition condition) {
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            return dslContext.deleteFrom(tableName).where(condition).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
