package me.thedivazo.libs.tests.database.dao;

import me.thedivazo.libs.database.dao.Dao;
import me.thedivazo.libs.database.dao.JdbcDao;
import me.thedivazo.libs.database.sql.connection.ConnectionPool;
import me.thedivazo.libs.tests.database.dao.entity.PlayerEntity;
import me.thedivazo.libs.util.IterableUtil;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.SQLException;
import java.util.UUID;

/**
 * @author TheDiVaZo
 * created on 16.11.2024
 */
public class JdbcDaoImpl extends JdbcDao<PlayerEntity, UUID> implements Dao<PlayerEntity, UUID> {
    private static final ResultSetHandler<PlayerEntity> resultSetHandler = resultSet-> {
        try {
            if (resultSet.next()) {
                return new PlayerEntity(UUID.fromString(resultSet.getString("id")), resultSet.getString("name"));
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
    public UUID insert(PlayerEntity entity) {
        try {
            runner.execute("INSERT INTO " + tableName + " (" + keyIdentifier + "," + "name" + ") VALUES(?, ?)", entity.uuid().toString(), entity.name());
            return entity.uuid();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<UUID> inserts(Iterable<? extends PlayerEntity> entities) {
        return IterableUtil.toStream(entities).map(this::insert).toList();
    }

    @Override
    public UUID upsert(PlayerEntity entity) {
        try {
            runner.update("INSERT INTO " + tableName + " (" + keyIdentifier + "," + "name" + ") VALUES(?, ?) ON DUBLICATE KEY UPDATE " + keyIdentifier + " = VALUE(" + keyIdentifier + "), name = VALUE(name)", entity.uuid().toString(), entity.name());
            return entity.uuid();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<UUID> upserts(Iterable<? extends PlayerEntity> entities) {
        return IterableUtil.toStream(entities).map(this::upsert).toList();
    }

    @Override
    public boolean update(PlayerEntity entity) {
        try {
            return runner.update("UPDATE " + tableName + " SET name = ? WHERE " + keyIdentifier + " = ?", entity.name(), entity.uuid().toString()) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updates(Iterable<? extends PlayerEntity> entities) {
        return IterableUtil.toStream(entities).mapToInt(entity-> update(entity) ? 1 : 0).sum();
    }

    @Override
    public UUID getId(PlayerEntity entity) {
        return entity.uuid();
    }
}
