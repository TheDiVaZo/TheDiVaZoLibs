package me.thedivazo.libs.tests.database.dao.impl;

import me.thedivazo.libs.database.dao.ConditionJooqDao;
import me.thedivazo.libs.database.dao.JooqDao;
import me.thedivazo.libs.database.sql.connection.ConnectionPool;
import me.thedivazo.libs.tests.database.dao.entity.BinaryIdPlayerEntity;
import me.thedivazo.libs.util.IterableUtil;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author TheDiVaZo
 * created on 19.11.2024
 **/
public class MySqlJooqDaoImpl extends JooqDao<BinaryIdPlayerEntity, byte[]> implements ConditionJooqDao<BinaryIdPlayerEntity, byte[]> {
    protected Field<String> name = DSL.field("name", String.class);

    public MySqlJooqDaoImpl(ConnectionPool pool) {
        super("player_entity", "id", byte[].class, pool, SQLDialect.MYSQL, record -> new BinaryIdPlayerEntity(record.get("id", byte[].class), record.get("name", String.class)));
    }

    @Override
    public byte[] insert(BinaryIdPlayerEntity entity) {
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            dslContext.insertInto(tableName)
                    .set(keyIdentifier, entity.uuidBytes())
                    .set(name, entity.name())
                    .execute();
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
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            dslContext.insertInto(tableName)
                    .set(keyIdentifier, entity.uuidBytes())
                    .set(name, entity.name())
                    .onDuplicateKeyUpdate()
                    .set(name, entity.name())
                    .execute();
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
        try (Connection connection = pool.getConnection()) {
            DSLContext dslContext = getDslContext(connection);
            return dslContext.update(tableName)
                    .set(name, entity.name())
                    .where(keyIdentifier.eq(entity.uuidBytes()))
                    .execute() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updates(Iterable<? extends BinaryIdPlayerEntity> entities) {
        return IterableUtil.toStream(entities).map(this::update).mapToInt(updated -> updated ? 1 : 0).sum();
    }

    @Override
    public byte[] getId(BinaryIdPlayerEntity entity) {
        return entity.uuidBytes();
    }
}
