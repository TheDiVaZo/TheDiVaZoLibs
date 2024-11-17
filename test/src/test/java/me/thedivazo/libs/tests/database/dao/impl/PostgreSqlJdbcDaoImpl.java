package me.thedivazo.libs.tests.database.dao.impl;

import me.thedivazo.libs.database.dao.JdbcDao;
import me.thedivazo.libs.database.sql.connection.ConnectionPool;
import me.thedivazo.libs.tests.database.dao.entity.BinaryIdPlayerEntity;
import me.thedivazo.libs.tests.database.dao.entity.UuidPlayerEntity;
import me.thedivazo.libs.util.IterableUtil;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.SQLException;
import java.util.UUID;

/**
 * @author TheDiVaZo
 * created on 18.11.2024
 */
public class PostgreSqlJdbcDaoImpl extends JdbcDao<UuidPlayerEntity, UUID> {
    private static final ResultSetHandler<UuidPlayerEntity> resultSetHandler = resultSet-> {
        try {
            if (resultSet.next()) {
                return new UuidPlayerEntity(resultSet.getObject("id", UUID.class), resultSet.getString("name"));
            }
            else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public PostgreSqlJdbcDaoImpl(ConnectionPool pool) {
        super("player_entity", "id", pool, resultSetHandler);
    }

    @Override
    public UUID insert(UuidPlayerEntity entity) {
        try {
            runner.execute("INSERT INTO " + tableName + " (" + keyIdentifier + "," + "name" + ") VALUES(?, ?)", entity.uuid(), entity.name());
            return entity.uuid();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<UUID> inserts(Iterable<? extends UuidPlayerEntity> entities) {
        return IterableUtil.toStream(entities).map(this::insert).toList();
    }

    @Override
    public UUID upsert(UuidPlayerEntity entity) {
        try {
            runner.update("INSERT INTO " + tableName + " (" + keyIdentifier + "," + "name" + ") VALUES(?, ?) ON CONFLICT ("+keyIdentifier+") DO UPDATE SET name = EXCLUDED.name", entity.uuid(), entity.name());
            return entity.uuid();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<UUID> upserts(Iterable<? extends UuidPlayerEntity> entities) {
        return IterableUtil.toStream(entities).map(this::upsert).toList();
    }

    @Override
    public boolean update(UuidPlayerEntity entity) {
        try {
            return runner.update("UPDATE " + tableName + " SET name = ? WHERE " + keyIdentifier + " = ?", entity.name(), entity.uuid()) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updates(Iterable<? extends UuidPlayerEntity> entities) {
        return IterableUtil.toStream(entities).mapToInt(entity-> update(entity) ? 1 : 0).sum();
    }

    @Override
    public UUID getId(UuidPlayerEntity entity) {
        return entity.uuid();
    }
}
