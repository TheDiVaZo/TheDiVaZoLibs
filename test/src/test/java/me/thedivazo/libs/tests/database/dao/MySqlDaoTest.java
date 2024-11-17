package me.thedivazo.libs.tests.database.dao;

import lombok.NoArgsConstructor;
import me.thedivazo.libs.database.configsource.HikariConfigParamImpl;
import me.thedivazo.libs.database.dao.Dao;
import me.thedivazo.libs.database.sql.connection.ConnectionPool;
import me.thedivazo.libs.database.sql.connection.DefaultJDBCSource;
import me.thedivazo.libs.database.sql.connection.factory.ConnectionPoolFactory;
import me.thedivazo.libs.database.sql.connection.factory.HikariConnectionPoolFactory;
import me.thedivazo.libs.tests.database.dao.entity.PlayerEntity;
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
 * created on 16.11.2024
 */
@NoArgsConstructor
public class MySqlDaoTest extends AbstractDaoTest {
    private static final GenericContainer<?> mysqlContainer = new GenericContainer<>(DockerImageName.parse("mysql:8.3.0"))
            .withEnv("MYSQL_USER", "test")
            .withEnv("MYSQL_PASSWORD", "test")
            .withEnv("MYSQL_DATABASE", "test")
            .withEnv("MYSQL_ROOT_PASSWORD", "test")
            .withExposedPorts(3306)
            .waitingFor(Wait.forListeningPort());

    @AfterAll
    public static void stopContainer() {
        mysqlContainer.stop();
    }

    @BeforeAll
    public static void initApp() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        mysqlContainer.start();
        String jdbcURL = String.format(
                "jdbc:mysql://%s:%d/test",
                mysqlContainer.getHost(),
                mysqlContainer.getMappedPort(3306)
        );
        ConnectionPoolFactory connectionPoolFactory = new HikariConnectionPoolFactory(new HikariConfigParamImpl());

        DefaultJDBCSource dataSource = new DefaultJDBCSource();
        dataSource.setUrl(jdbcURL);
        dataSource.setUsername("test");
        dataSource.setPassword("test");

        ConnectionPool connectionPool = connectionPoolFactory.create(dataSource);

        Dao<PlayerEntity, byte[]> mysqlDao = new JdbcDaoImpl(connectionPool);

        String createTableSQL = """
                CREATE TABLE player_entity (
                    id BINARY(16) PRIMARY KEY,
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

        AbstractDaoTest.dao = mysqlDao;
    }
}
