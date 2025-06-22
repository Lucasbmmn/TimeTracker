package com.lucasbmmn.timetracker.data.dao;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Generic Data Access Object (DAO) interface defining standard CRUD operations.
 *
 * @param <T> the type of the entity this DAO manages
 */
public interface Dao<T> {

    /**
     * Retrieves all entities from the data source.
     *
     * @return a list of all entities; never {@code null}, may be empty
     */
    @NotNull
    List<T> getAll();

    /**
     * Retrieves an entity by its unique identifier represented as a String.
     *
     * @param uuid the unique identifier of the entity; must not be {@code null}
     * @return the entity matching the given UUID, or {@code null} if none found
     * @throws NullPointerException if {@code uuid} is {@code null}
     */
    T getById(@NotNull String uuid);

    /**
     * Retrieves an entity by its unique identifier represented as a {@link UUID}.
     *
     * @param uuid the unique identifier of the entity; must not be {@code null}
     * @return the entity matching the given UUID, or {@code null} if none found
     * @throws NullPointerException if {@code uuid} is {@code null}
     */
    T getById(@NotNull UUID uuid);

    /**
     * Inserts a new entity into the data source.
     *
     * @param entity the entity to insert; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    void insert(@NotNull T entity);

    /**
     * Deletes an existing entity from the data source.
     *
     * @param entity the entity to delete; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    void delete(@NotNull T entity);


    /**
     * Updates an existing entity in the data source.
     *
     * @param entity the entity to update; must not be {@code null}
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    void update(@NotNull T entity);
}
