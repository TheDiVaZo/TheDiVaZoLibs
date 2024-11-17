package me.thedivazo.libs.tests.database.dao;

import me.thedivazo.libs.database.configsource.HikariConfigParamImpl;
import me.thedivazo.libs.database.dao.Dao;
import me.thedivazo.libs.database.sql.connection.ConnectionPool;
import me.thedivazo.libs.database.sql.connection.ConnectionPoolFactory;
import me.thedivazo.libs.database.sql.connection.HikariConnectionPoolFactory;
import me.thedivazo.libs.database.sql.connection.factory.PostgreSqlDataSource;
import me.thedivazo.libs.tests.database.dao.entity.UuidPlayerEntity;
import me.thedivazo.libs.tests.database.dao.impl.PostgreSqlJdbcDaoImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * @author TheDiVaZo
 * created on 18.11.2024
 */
public class PostgreSqlDaoTest extends UuidAbstractDaoTest {
    private static final GenericContainer<?> mysqlContainer = new GenericContainer<>(DockerImageName.parse("postgres:14-alpine"))
            .withEnv("POSTGRES_USER", "test")
            .withEnv("POSTGRES_PASSWORD", "test")
            .withEnv("POSTGRES_DB", "test")
            .withExposedPorts(5432)
            .waitingFor(Wait.forListeningPort());

    @AfterAll
    public static void stopContainer() {
        mysqlContainer.stop();
    }

    @BeforeAll
    public static void initApp() {
        mysqlContainer.start();
        String jdbcURL = String.format(
                "jdbc:postgresql://%s:%d/test",
                mysqlContainer.getHost(),
                mysqlContainer.getMappedPort(5432)
        );
        ConnectionPoolFactory connectionPoolFactory = new HikariConnectionPoolFactory(new HikariConfigParamImpl());

        PostgreSqlDataSource dataSource = new PostgreSqlDataSource();
        dataSource.setUrl(jdbcURL);
        dataSource.setUsername("test");
        dataSource.setPassword("test");

        dataSource.installDriver();

        ConnectionPool connectionPool = connectionPoolFactory.create(dataSource);

        Dao<UuidPlayerEntity, UUID> mysqlDao = new PostgreSqlJdbcDaoImpl(connectionPool);

        String createTableSQL = """
                CREATE TABLE player_entity (
                    id UUID PRIMARY KEY,
                    name VARCHAR(255) NOT NULL
                );
                """;

        try (Connection connection = connectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(createTableSQL);
            System.out.println("Таблица player_entity успешно создана.");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
        }

        UuidAbstractDaoTest.dao = mysqlDao;
    }
}
