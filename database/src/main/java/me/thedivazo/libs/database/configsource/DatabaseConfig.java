package me.thedivazo.libs.database.configsource;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @author TheDiVaZo
 * created on 27.10.2024
 *
 * Интерфейс конфигурации, используемая для подключения к базе данных
 */
@Nullable
public interface DatabaseConfig {
    /**
     * Явно установленный URL. Если метод возвращает не null, по этому адресу должно проводится подключение
     * @return Возвращает прямую ссылку на подключение к бд, либо null
     */
    String getUrl();

    default boolean explicitUrl() {
        return getUrl()!= null && !getUrl().isBlank() && !getUrl().isEmpty();
    }

    String getUsername();
    String getPassword();
}
