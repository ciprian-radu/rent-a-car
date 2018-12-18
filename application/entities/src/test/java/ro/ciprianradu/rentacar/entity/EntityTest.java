package ro.ciprianradu.rentacar.entity;

import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Entity}
 */
class EntityTest {

    @Test
    void test_Entity_nullEntity_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Entity((Entity) null));
    }

    @Test
    void test_Entity_nullID_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Entity((String) null));
    }

    @Test
    void test_Entity_ID_instantiates() {
        Assertions.assertNotNull(new Entity(UUID.randomUUID().toString()));
    }

    @Test
    void test_equals_null_returnsFalse() {
        Entity entity = new Entity();
        Assertions.assertFalse(entity.equals(null));
    }

    @Test
    void test_equals_otherClass_returnsFalse() {
        Entity entity = new Entity();
        Assertions.assertFalse(entity.equals(Integer.valueOf(0)));
    }

    @Test
    void test_equals_isReflexive() {
        Entity entity = new Entity();
        Assertions.assertTrue(entity.equals(entity));
    }

    @Test
    void test_equals_isSymmetric() {
        Entity entity1 = new Entity();
        Entity entity2 = new Entity(entity1);
        Assertions.assertTrue(entity1.equals(entity2));
        Assertions.assertTrue(entity2.equals(entity1));
    }

    @Test
    void test_equals_isTransitive() {
        Entity entity1 = new Entity();
        Entity entity2 = new Entity(entity1);
        Entity entity3 = new Entity(entity2);
        Assertions.assertTrue(entity1.equals(entity2));
        Assertions.assertTrue(entity2.equals(entity3));
        Assertions.assertTrue(entity1.equals(entity3));
    }

    @Test
    void test_hashCode_returnsIdHash() {
        Entity entity = new Entity();
        Assertions.assertEquals(Objects.hash(entity.getId()), entity.hashCode());
    }

    @Test
    void test_toString_returnsId() {
        Entity entity = new Entity();
        Assertions.assertEquals("Entity{id=" + entity.getId().toString() + "}", entity.toString());
    }

}