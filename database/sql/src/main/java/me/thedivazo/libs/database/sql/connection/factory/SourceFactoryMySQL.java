package me.thedivazo.libs.database.sql.connection.factory;

import com.mysql.cj.jdbc.MysqlDataSource;
import me.thedivazo.libs.database.configsource.MonoDatabaseConfig;
import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public class SourceFactoryMySQL<T extends SQLDatabaseConfig & MonoDatabaseConfig> implements DataSourceFactory<T> {

    @Override
    public DataSource toDataSource(T config) {
        MysqlDataSource dataSource = new MysqlDataSource();
        String url = config.getUrl();
        if (!config.explicitUrl()) {
            StringBuilder urlBuilder = new StringBuilder(String.format("jdbc:mysql://%s:%s/%s", config.getHost(), config.getPort(), config.getDatabaseName()));
            String prevSign = "?";
            if (!config.getParams().isEmpty()) urlBuilder.append("?");
            for (Map.Entry<String, String> param : config.getParams().entrySet()) {
                urlBuilder.append(String.format("%s%s=%s", prevSign, param.getKey(), param.getValue()));
                prevSign = "&";
            }

            url = urlBuilder.toString();
        }
        dataSource.setURL(url);
        dataSource.setUser(config.getUsername());
        dataSource.setPassword(config.getPassword());
        return dataSource;
    }
}
