package ro.ciprianradu.rentacar.entity;

import java.util.Objects;
import java.util.UUID;

/**
 * Entities encapsulate Enterprise wide business rules. An entity can be an object with methods, or it can be a set of data structures and functions. It does
 * not matter so long as the entities could be used by many different applications in the enterprise.
 */
public class Entity {

    /** This is the ID of this entity. May not be unique since entities can be copied. */
    private String id;

    /**
     * Default constructor
     */
    public Entity() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Constructor - allows you to create an {@link Entity} with a specific ID
     *
     * @param id the ID of the entity (cannot be <code>null</code>)
     */
    public Entity(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null!");
        }
        this.id = id;
    }

    /**
     * Copy constructor
     *
     * @param entity the entity to copy
     */
    public Entity(Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        this.id = entity.id;
    }

    /**
     * @return the ID of this entity
     */
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Entity entity = (Entity) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Entity{" +
            "id=" + id +
            '}';
    }

}
