package me.thedivazo.libs.database.configsource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 31.10.2024
 **/
public interface JDBCSource extends DataSource {
    PrintWriter getLogWriter() throws SQLException;

    int getLoginTimeout() throws SQLException;

    Logger getParentLogger() throws SQLFeatureNotSupportedException;

    <T> T unwrap(Class<T> iface) throws SQLException;

    boolean isWrapperFor(Class<?> iface) throws SQLException;
}
