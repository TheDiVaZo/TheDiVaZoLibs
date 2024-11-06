package me.thedivazo.libs.database.configsource;

import java.util.Map;
import java.util.Properties;

/**
 * @author TheDiVaZo
 * created on 27.10.2024
 *
 * Интерфейс конфигурации, используемая для подключения к базе данных
 */
public interface ConfigSource {
    String getHost();
    String getPort();

    String getUsername();
    String getPassword();

    Map<String, String> getParams();

    String toURL();
}
