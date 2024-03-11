package lol.koblizek.craftorm.test;

import lol.koblizek.craftorm.CraftORM;
import lol.koblizek.craftorm.ResourceProvider;
import lol.koblizek.craftorm.beans.Inject;

public class TestMain {

    @Inject
    private static ResourceProvider resourceProvider;

    public static void main(String[] args) {
        CraftORM.start(TestMain.class);
    }
}
