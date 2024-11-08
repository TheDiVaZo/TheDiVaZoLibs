package me.thedivazo.libs.database.dao;

import me.thedivazo.libs.database.sql.connection.ConnectionPool;
import me.thedivazo.libs.util.LazyUncheckedSpliterator;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void delete(ID key) {
        try {
            runner.update("DELETE FROM " + tableName + " WHERE " + keyIdentifier + "= ?", key);
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

    @Override
    public Stream<T> getAllInStream() {
        return getAllInStreamFromQuery("SELECT * FROM " + tableName);
    }

    protected Stream<T> getAllInStreamFromQuery(String query) {
        try {
            final Connection connection = pool.getConnection();
            final PreparedStatement ps = connection.prepareStatement(query);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
