package me.thedivazo.libs.tests.database.dao.impl;

import me.thedivazo.libs.database.dao.ConditionJooqDao;
import me.thedivazo.libs.database.dao.JooqDao;
import me.thedivazo.libs.database.sql.connection.ConnectionPool;
import me.thedivazo.libs.tests.database.dao.entity.UuidPlayerEntity;
import me.thedivazo.libs.util.IterableUtil;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author TheDiVaZo
 * created on 19.11.2024
 **/
public class PostgresJooqDaoImpl extends JooqDao<UuidPlayerEntity, UUID> implements ConditionJooqDao<UuidPlayerEntity, UUID> {
    protected Field<String> name = DSL.field(DSL.name("name"), String.class);

    public PostgresJooqDaoImpl(ConnectionPool pool) {
        super("player_entity", "id", UUID.class, pool, SQLDialect.POSTGRES, record -> new UuidPlayerEntity(record.get("id", UUID.class), record.get("name", String.class)));
    }

    @Override
    public UUID insert(UuidPlayerEntity entity) {
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            dslContext.insertInto(tableName)
                    .set(keyIdentifier, entity.uuid())
                    .set(name, entity.name())
                    .execute();
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
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            dslContext.insertInto(tableName)
                    .set(keyIdentifier, entity.uuid())
                    .set(name, entity.name())
                    .onConflict(keyIdentifier)
                    .doUpdate()
                    .set(name, entity.name())
                    .execute();
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
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            return dslContext.update(tableName)
                    .set(name, entity.name())
                    .where(keyIdentifier.eq(entity.uuid()))
                    .execute() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updates(Iterable<? extends UuidPlayerEntity> entities) {
        return IterableUtil.toStream(entities).map(this::update).mapToInt(updated -> updated ? 1 : 0).sum();
    }

    @Override
    public UUID getId(UuidPlayerEntity entity) {
        return entity.uuid();
    }
}
