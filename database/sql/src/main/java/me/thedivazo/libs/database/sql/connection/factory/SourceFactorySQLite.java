package me.thedivazo.libs.database.sql.connection.factory;

import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;
import me.thedivazo.libs.database.util.UrlDatabaseUtil;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public class SourceFactorySQLite<T extends SQLDatabaseConfig> implements DataSourceFactory<T> {
    @Override
    public DataSource toDataSource(T config) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        String url = config.getUrl();
        if (!config.explicitUrl()) {
            StringBuilder urlBuilder = new StringBuilder(String.format("jdbc:sqlite:%s", config.getDatabaseName()));
            urlBuilder.append(UrlDatabaseUtil.generateUrlParams(config.getParams()));
            url = urlBuilder.toString();
        }
        dataSource.setUrl(url);
        return dataSource;
    }
}
