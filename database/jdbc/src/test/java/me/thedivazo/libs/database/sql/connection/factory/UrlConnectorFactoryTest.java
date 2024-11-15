package me.thedivazo.libs.database.sql.connection.factory;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlConnectorFactoryTest {
    private final TestSqlConfig configWithNoUrl = new TestSqlConfig(
            "database",
            null,
            "username",
            "password",
            new String[]{"host1", "host2"},
            new String[]{"port1", "port2"},
            "host",
            "port",
            Map.of("anyoneParam", "anyoneValue")
    );

    @Test
    void testMysqlUrlGenerated() {
        MysqlJdbcUrlFactory<TestSqlConfig> sourceFactory = new MysqlJdbcUrlFactory<>();
        String actual = sourceFactory.createUrlConnection(configWithNoUrl);
        String expected = "jdbc:mysql://host:port/database?anyoneParam=anyoneValue";
        assertEquals(expected, actual);
    }

    @Test
    void testH2UrlGenerated() {
        H2JdbcUrlFactory<TestSqlConfig> sourceFactory = new H2JdbcUrlFactory<>();
        String actual = sourceFactory.createUrlConnection(configWithNoUrl);
        String expected = "jdbc:h2:tcp://host:port/database;anyoneParam=anyoneValue";
        assertEquals(expected, actual);
    }

    @Test
    void testPostgreSqlUrlGenerated() {
        PostgreSqlJdbcUrlFactory<TestSqlConfig> sourceFactory = new PostgreSqlJdbcUrlFactory<>();
        String actual = sourceFactory.createUrlConnection(configWithNoUrl);
        String expected = "jdbc:postgresql://host1:port1,host2:port2/database?anyoneParam=anyoneValue";
        assertEquals(expected, actual);
    }

    @Test
    void testSQLiteUrlGenerated() {
        SQLiteJdbcUrlFactory<TestSqlConfig> sourceFactory = new SQLiteJdbcUrlFactory<>();
        String actual = sourceFactory.createUrlConnection(configWithNoUrl);
        String expected = "jdbc:sqlite:database?anyoneParam=anyoneValue";
        assertEquals(expected, actual);
    }
}