package me.thedivazo.libs.database.configsource;

/**
 * @author TheDiVaZo
 * created on 27.10.2024
 *
 * Интерфейс конфигурации, используемая для подключения к базе данных
 */
public interface ConfigSource {
    String getUrl();
    String getPort();

    String getUsername();
    String getPassword();
}
