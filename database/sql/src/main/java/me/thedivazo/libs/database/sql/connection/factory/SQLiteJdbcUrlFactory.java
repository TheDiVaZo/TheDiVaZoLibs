package me.thedivazo.libs.database.sql.connection.factory;

import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;
import me.thedivazo.libs.database.sql.connection.DefaultJDBCSource;
import me.thedivazo.libs.util.UrlDatabaseUtil;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public class SQLiteJdbcUrlFactory<T extends SQLDatabaseConfig> extends DefaultJDBCSource implements JdbcUrlFactory<T> {

    @Override
    public String createUrlConnection(T config) {
        String url = config.getUrl();
        if (!config.explicitUrl()) {
            StringBuilder urlBuilder = new StringBuilder(String.format("jdbc:sqlite:%s", config.getDatabaseName()));
            urlBuilder.append(UrlDatabaseUtil.generateUrlParams(config.getParams()));
            url = urlBuilder.toString();
        }
        return url;
    }

    @Override
    public String getDriverClassName() {
        return "org.sqlite.JDBC";
    }
}
