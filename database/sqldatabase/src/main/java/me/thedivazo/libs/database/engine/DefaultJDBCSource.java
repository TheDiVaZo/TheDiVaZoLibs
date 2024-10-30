package me.thedivazo.libs.database.engine;

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
 * created on 08.05.2024
 */
public class DefaultJDBCSource implements DataSource {
    private final String url;
    @Setter
    private String username = null;
    @Setter
    private String password = null;
    @Setter
    private Integer timeOut = null;
    private PrintWriter writer = System.console().writer();

    public DefaultJDBCSource(String url, DriverLoader driverLoader) {
        this.url = url;
        driverLoader.loadDriver();
    }

    public Connection getConnection() throws SQLException {
        return this.getConnection(null, null);
    }

    public Connection getConnection(String username, String password) throws SQLException {
        Properties properties = new Properties();
        if (this.username != null) {
            properties.setProperty("user", this.username);
        }

        if (this.password != null) {
            properties.setProperty("password", this.password);
        }

        if (this.timeOut != null) {
            DriverManager.setLoginTimeout(this.timeOut);
        }

        Connection connection = DriverManager.getConnection(this.url, properties);
        connection.setAutoCommit(true);
        return connection;
    }

    public PrintWriter getLogWriter() throws SQLException {
        return this.writer;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        this.writer = out;
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        this.timeOut = seconds;
    }

    public int getLoginTimeout() throws SQLException {
        return this.timeOut;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        } else {
            throw new SQLException("Wrapped DataSource is not an instance of " + iface);
        }
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }
}
