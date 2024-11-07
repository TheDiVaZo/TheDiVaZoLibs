package me.thedivazo.libs.database.configsource;

import java.util.Map;

/**
 * @author TheDiVaZo
 * created on 27.10.2024
 *
 * Интерфейс конфигурации, используемая для подключения к базе данных
 */
public interface DatabaseConfig {
    String getHost();
    String getPort();

    String getUsername();
    String getPassword();
}
