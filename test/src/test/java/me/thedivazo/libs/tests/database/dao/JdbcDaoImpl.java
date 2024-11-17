package me.thedivazo.libs.tests.database.dao;

import me.thedivazo.libs.database.dao.Dao;
import me.thedivazo.libs.database.dao.JdbcDao;
import me.thedivazo.libs.database.sql.connection.ConnectionPool;
import me.thedivazo.libs.tests.database.dao.entity.PlayerEntity;
import me.thedivazo.libs.util.IterableUtil;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.SQLException;

/**
 * @author TheDiVaZo
 * created on 16.11.2024
 */
public class JdbcDaoImpl extends JdbcDao<PlayerEntity, byte[]> implements Dao<PlayerEntity, byte[]> {
    private static final ResultSetHandler<PlayerEntity> resultSetHandler = resultSet-> {
        try {
            if (resultSet.next()) {
                return new PlayerEntity(resultSet.getBytes("id"), resultSet.getString("name"));
            }
            else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    protected JdbcDaoImpl(ConnectionPool pool) {
        super("player_entity", "id", pool, resultSetHandler);
    }

    @Override
    public byte[] insert(PlayerEntity entity) {
        try {
            runner.execute("INSERT INTO " + tableName + " (" + keyIdentifier + "," + "name" + ") VALUES(?, ?)", entity.uuidBytes(), entity.name());
            return entity.uuidBytes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<byte[]> inserts(Iterable<? extends PlayerEntity> entities) {
        return IterableUtil.toStream(entities).map(this::insert).toList();
    }

    @Override
    public byte[] upsert(PlayerEntity entity) {
        try {
            runner.update("INSERT INTO " + tableName + " (" + keyIdentifier + "," + "name" + ") VALUES(?, ?) ON DUBLICATE KEY UPDATE " + keyIdentifier + " = VALUE(" + keyIdentifier + "), name = VALUE(name)", entity.uuidBytes(), entity.name());
            return entity.uuidBytes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<byte[]> upserts(Iterable<? extends PlayerEntity> entities) {
        return IterableUtil.toStream(entities).map(this::upsert).toList();
    }

    @Override
    public boolean update(PlayerEntity entity) {
        try {
            return runner.update("UPDATE " + tableName + " SET name = ? WHERE " + keyIdentifier + " = ?", entity.name(), entity.uuidBytes()) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updates(Iterable<? extends PlayerEntity> entities) {
        return IterableUtil.toStream(entities).mapToInt(entity-> update(entity) ? 1 : 0).sum();
    }

    @Override
    public byte[] getId(PlayerEntity entity) {
        return entity.uuidBytes();
    }
}
