package ro.ciprianradu.rentacar.usecases.gateways;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ro.ciprianradu.rentacar.entity.Entity;


/**
 *
 */
class EntityGateway<T extends Entity> {

    private Map<String, T> entities;

    public EntityGateway() {
        entities = new HashMap<>();
    }

    public long count() {
        return entities.size();
    }

    /**
     * Saves a given entity.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }

        entities.put(entity.getId(), entity);

        return entity;
    }

    /**
     * Saves all given entities.
     *
     * @param entities must not be {@literal null}.
     * @return the saved entities will never be {@literal null}.
     * @throws IllegalArgumentException in case the given entities is {@literal null}.
     */
    public Iterable<T> saveAll(Iterable<T> entities) {
        if (entities == null) {
            throw new IllegalArgumentException();
        }

        Collection<T> collection = new ArrayList<>();
        entities.forEach(e -> {
            collection.add(e);
        });

        return collection;
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    public Optional<T> findById(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        T entity = entities.get(id);

        return Optional.ofNullable(entity);
    }

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    public boolean existsById(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        return entities.containsKey(id);
    }

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    public Iterable<T> findAll() {
        Collection<T> collection = new ArrayList<>();
        entities.forEach((k, v) -> {
            collection.add(v);
        });

        return collection;
    }

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @throws IllegalArgumentException if {@code ids} is {@literal null}.
     */
    public Iterable<T> findAllById(Iterable<String> ids) {
        if (ids == null) {
            throw new IllegalArgumentException();
        }

        Collection<T> collection = new ArrayList<>();
        ids.forEach(i -> {
            T e = entities.get(i);
            if (e != null) {
                collection.add(e);
            }
        });

        return collection;
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    public void deleteById(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        entities.remove(id);
    }

    /**
     * Deletes a given entity.
     *
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    public void delete(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }

        entities.remove(entity.getId());
    }

    /**
     * Deletes the given entities.
     *
     * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
     */
    public void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(e -> {
            this.entities.remove(e.getId());
        });
    }

    /**
     * Deletes all entities managed by the repository.
     */
    public void deleteAll() {
        entities.clear();
    }

}
