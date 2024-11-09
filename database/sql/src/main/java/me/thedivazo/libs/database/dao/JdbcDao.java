package me.thedivazo.libs.database.dao;

import me.thedivazo.libs.database.sql.connection.ConnectionPool;
import me.thedivazo.libs.util.LazyUncheckedSpliterator;
import me.thedivazo.libs.util.function.CheckedFunction;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.apache.commons.dbutils.DbUtils.close;

/**
 * @author TheDiVaZo
 * created on 08.11.2024
 * <p>
 * Данный тип Dao использует JDBC на прямую
 * Из-за того, что использование чистого SQL из вне не безопасно,
 * этот Dao изначально не содержит общих методов для поиска сущностей с условиями.
 * Вам нужно самим создать и реализовать все методы поиска, которые вам нужны.
 * <p>
 * Если вы не хотите реализовывать свои методы поиска, воспользуйтесь
 * реализацией с использованием DLS. Например: {@link JooqDao<T,ID> }
 **/
public abstract class JdbcDao<T, ID> implements Dao<T, ID> {
    protected final ConnectionPool pool;
    protected final QueryRunner runner;
    protected final ResultSetHandler<T> resultSetHandler;
    protected final String tableName;
    protected final String keyIdentifier;

    protected JdbcDao(String tableName, String keyIdentifier, ConnectionPool pool, ResultSetHandler<T> resultSetHandler) {
        this.pool = pool;
        this.runner = new QueryRunner(pool.getPooledDataSource());
        this.resultSetHandler = resultSetHandler;
        this.tableName = tableName;
        this.keyIdentifier = keyIdentifier;
    }

    @Override
    public @Nullable T get(ID id) {
        try {
            return runner.query("SELECT * FROM " + tableName + " WHERE " + keyIdentifier + "= ?", resultSetHandler, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<@Nullable T> gets(Iterable<? extends ID> ids) {
        return getAllFromQuery(con->{
            StringBuilder query = new StringBuilder( "SELECT * FROM ").append(tableName).append(" WHERE ").append(keyIdentifier).append(" IN ");
            List<ID> preparedIds = new LinkedList<>();
            query.append("(");
            boolean isFirst = true;
            for (ID id : ids) {
                preparedIds.add(id);
                if (!isFirst) {
                    query.append(", ");
                }
                else {
                    isFirst = false;
                }
                query.append("?");
            }
            query.append(")");
            PreparedStatement preparedStatement = con.prepareStatement(query.toString());
            runner.fillStatement(preparedStatement, preparedIds.toArray());
            return preparedStatement;
        });
    }

    @Override
    public Stream<T> getAll() {
        return getAllFromQuery( con-> con.prepareStatement("SELECT * FROM " + tableName));
    }

    protected Stream<T> getAllFromQuery(CheckedFunction<Connection, PreparedStatement, SQLException> preparedStatementGetter) {
        try {
            final Connection connection = pool.getConnection();
            final PreparedStatement ps = preparedStatementGetter.apply(connection);
            final ResultSet rs = ps.executeQuery();
            return StreamSupport.stream(new LazyUncheckedSpliterator<T>(action -> {
                if (!rs.next()) {
                    close(connection);
                    close(ps);
                    close(rs);
                    return false;
                }
                final T value = resultSetHandler.handle(rs);
                action.accept(value);
                return true;
            }), false).onClose(() -> {
                try {
                    close(connection);
                    close(ps);
                    close(rs);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(ID id) {
        try {
            runner.update("DELETE FROM " + tableName + " WHERE " + keyIdentifier + "= ?", id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletes(Iterable<? extends ID> ids) {
        try {
            StringBuilder query = new StringBuilder( "DELETE FROM ").append(tableName).append(" WHERE ").append(keyIdentifier).append(" IN ");
            List<ID> preparedIds = new LinkedList<>();
            query.append("(");
            boolean isFirst = true;
            for (ID id : ids) {
                preparedIds.add(id);
                if (!isFirst) {
                    query.append(", ");
                }
                else {
                    isFirst = false;
                }
                query.append("?");
            }
            query.append(")");
            runner.update(query.toString(), preparedIds.toArray());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            runner.update("DELETE FROM " + tableName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
