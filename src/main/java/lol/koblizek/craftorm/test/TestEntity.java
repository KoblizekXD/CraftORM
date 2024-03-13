package lol.koblizek.craftorm.test;

import lol.koblizek.craftorm.persistence.Entity;
import lol.koblizek.craftorm.persistence.Id;

import java.util.UUID;

@Entity(table = @Entity.Table(name = "test_entity"))
public class TestEntity {

    @Id
    private UUID id;
}
