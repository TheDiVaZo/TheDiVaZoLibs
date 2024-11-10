package me.thedivazo.libs.database.dao;

import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

/**
 * @author TheDiVaZo
 * created on 08.11.2024
 * <p>
 * Представляет собой интерфейс для прямого доступа и взаимодействия с базой данных
 *
 * @param <T> Entity объект. Представляет собой отображение записи в базе
 * @param <ID> Объект идентификатора
 **/
public interface Dao<T, ID> {

    /**
     * Вставить запись в таблицу
     * @param entity объект записи для вставки.
     * @return Возвращает ID вставленной записи
     */
    ID insert(T entity);

    /**
     * Тоже что и {@link Dao#insert(T)}, но для нескольких записей
     * @param entities объекты записи
     * @return идентификаторы новых записей в базе.
     */
    Iterable<ID> inserts(Iterable<? extends T> entities);

    /**
     * Комбинация методов {@link Dao#insert(T)} и {@link Dao#update(T)}
     * @param entity объект записи для вставки или обновления
     * @return идентификатор новой/обновленной записи
     */
    ID upsert(T entity);
    /**
     * Тоже что и {@link Dao#upsert(T)}, но для нескольких записей
     * @param entities объекты записей
     * @return идентификаторы новых/обновленных записей в базе.
     */
    Iterable<ID> upserts(Iterable<? extends T> entities);

    /**
     * Удаляет запись из базы данных
     * @param key объект идентификатора записи
     * @return true - запись удалена. false - запись не удалена.
     */
    boolean delete(ID key);

    /**
     * Удаляет записи из базы
     * @param ids объекты идентификаторов записей
     * @return кол-во записей, которое было удалено.
     */
    int deletes(Iterable<? extends ID> ids);

    /**
     * Удаляет все записи из базы
     * @return кол-во записей, которое было удалено
     */
    int deletesAll();

    /**
     * Обновить запись в базе
     * @param entity объект записи
     * @return true - запись была обновлена, false - запись не найдена или не была обновлена по другим причинам
     */
    boolean update(T entity);

    /**
     * Тоже-самое что и {@link Dao#update(T)}, но для нескольких записей
     * @param entities объекты записей
     * @return кол-во записей, которое было обновлено.
     */
    int updates(Iterable<? extends T> entities);

    /**
     * Получить запись
     * @param id объект идентификатора записи
     * @return возвращает запись, или null, если записи с таким идентификатором не существует
     */
    @Nullable
    T get(ID id);

    /**
     * Реализация данного метода возвращает {@link Stream<T>},
     * который лениво получает данные из базы и не закрывает соединение до тех пор,
     * пока будет явно не вызван метод {@link Stream#close()}
     * или пока данные сами не закончат поступать.
     * @param ids объект содержащий идентификаторы.
     * @return стрим с сущностями в порядке получения идентификаторов из аргумента. Если сущность не найдена, она будет остутствовать в итераторе.
     */
    Stream<T> gets(Iterable<? extends ID> ids);

    /**
     * Возвращает идентификатор записи
     * @param entity запись
     * @return ее идентификатор
     */
    ID getId(T entity);

    /**
     * Реализация данного метода возвращает {@link Stream<T>},
     * который лениво получает данные из базы и не закрывает соединение до тех пор,
     * пока будет явно не вызван метод {@link Stream#close()}
     * или пока данные сами не закончат поступать
     *
     * @return {@link Stream<T>} с сущностями
     */
    Stream<T> getsAll();
}
