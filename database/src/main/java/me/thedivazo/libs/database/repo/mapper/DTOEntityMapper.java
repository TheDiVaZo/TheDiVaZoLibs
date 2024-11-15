package me.thedivazo.libs.database.repo.mapper;

/**
 * @author TheDiVaZo
 * created on 15.11.2024
 */
public interface DTOEntityMapper<T, E> {
    T toDTO(E entity);
    E toEntity(T dto);
}
