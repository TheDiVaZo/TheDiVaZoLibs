package me.thedivazo.libs.database.sql.connection;

import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
@Getter
@Setter
public class DefaultJDBCSource implements DataSource {
    protected PrintWriter logWriter;
    protected int loginTimeout;
    private String username;
    private String password;

    private String url;

    public DefaultJDBCSource(String driverClassName) {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver has not been loaded",e);
        }
    }

    public DefaultJDBCSource() {

    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);

        DriverManager.setLoginTimeout(this.loginTimeout);

        Connection connection = DriverManager.getConnection(this.url, properties);
        connection.setAutoCommit(true);
        return connection;
    }

    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        } else {
            throw new SQLException("Wrapped DataSource is not an instance of " + iface);
        }
    }

    public boolean isWrapperFor(Class<?> iface) {
        return iface.isInstance(this);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }
}
