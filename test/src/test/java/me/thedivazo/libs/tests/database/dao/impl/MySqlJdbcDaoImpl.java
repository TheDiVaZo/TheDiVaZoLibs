package me.thedivazo.libs.tests.database.dao.impl;

import me.thedivazo.libs.database.dao.Dao;
import me.thedivazo.libs.database.dao.JdbcDao;
import me.thedivazo.libs.database.sql.connection.ConnectionPool;
import me.thedivazo.libs.tests.database.dao.entity.BinaryIdPlayerEntity;
import me.thedivazo.libs.util.IterableUtil;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.SQLException;

/**
 * @author TheDiVaZo
 * created on 16.11.2024
 */
public class MySqlJdbcDaoImpl extends JdbcDao<BinaryIdPlayerEntity, byte[]> {
    private static final ResultSetHandler<BinaryIdPlayerEntity> resultSetHandler = resultSet-> {
        try {
            if (resultSet.next()) {
                return new BinaryIdPlayerEntity(resultSet.getBytes("id"), resultSet.getString("name"));
            }
            else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public MySqlJdbcDaoImpl(ConnectionPool pool) {
        super("player_entity", "id", pool, resultSetHandler);
    }

    @Override
    public byte[] insert(BinaryIdPlayerEntity entity) {
        try {
            runner.execute("INSERT INTO " + tableName + " (" + keyIdentifier + "," + "name" + ") VALUES(?, ?)", entity.uuidBytes(), entity.name());
            return entity.uuidBytes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<byte[]> inserts(Iterable<? extends BinaryIdPlayerEntity> entities) {
        return IterableUtil.toStream(entities).map(this::insert).toList();
    }

    @Override
    public byte[] upsert(BinaryIdPlayerEntity entity) {
        try {
            runner.update("INSERT INTO " + tableName + " (" + keyIdentifier + "," + "name" + ") VALUES(?, ?) ON DUPLICATE KEY UPDATE name = VALUES(name)", entity.uuidBytes(), entity.name());
            return entity.uuidBytes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<byte[]> upserts(Iterable<? extends BinaryIdPlayerEntity> entities) {
        return IterableUtil.toStream(entities).map(this::upsert).toList();
    }

    @Override
    public boolean update(BinaryIdPlayerEntity entity) {
        try {
            return runner.update("UPDATE " + tableName + " SET name = ? WHERE " + keyIdentifier + " = ?", entity.name(), entity.uuidBytes()) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updates(Iterable<? extends BinaryIdPlayerEntity> entities) {
        return IterableUtil.toStream(entities).mapToInt(entity-> update(entity) ? 1 : 0).sum();
    }

    @Override
    public byte[] getId(BinaryIdPlayerEntity entity) {
        return entity.uuidBytes();
    }
}
