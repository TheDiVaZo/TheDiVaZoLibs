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
        MysqlUrlFactory<TestSqlConfig> sourceFactory = new MysqlUrlFactory<>();
        String actual = sourceFactory.createUrlConnection(configWithNoUrl);
        String expected = "jdbc:mysql://host:port/database?anyoneParam=anyoneValue";
        assertEquals(expected, actual);
    }

    @Test
    void testH2UrlGenerated() {
        H2UrlFactory<TestSqlConfig> sourceFactory = new H2UrlFactory<>();
        String actual = sourceFactory.createUrlConnection(configWithNoUrl);
        String expected = "jdbc:h2:tcp://host:port/database;anyoneParam=anyoneValue";
        assertEquals(expected, actual);
    }

    @Test
    void testPostgreSqlUrlGenerated() {
        PostgreSqlUrlFactory<TestSqlConfig> sourceFactory = new PostgreSqlUrlFactory<>();
        String actual = sourceFactory.createUrlConnection(configWithNoUrl);
        String expected = "jdbc:postgresql://host1:port1,host2:port2/database?anyoneParam=anyoneValue";
        assertEquals(expected, actual);
    }

    @Test
    void testSQLiteUrlGenerated() {
        SQLiteUrlFactory<TestSqlConfig> sourceFactory = new SQLiteUrlFactory<>();
        String actual = sourceFactory.createUrlConnection(configWithNoUrl);
        String expected = "jdbc:sqlite:database?anyoneParam=anyoneValue";
        assertEquals(expected, actual);
    }
}