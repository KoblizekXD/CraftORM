package lol.koblizek.craftorm.test;

import lol.koblizek.craftorm.CraftORM;
import lol.koblizek.craftorm.ResourceProvider;
import lol.koblizek.craftorm.beans.Inject;
import lol.koblizek.craftorm.util.properties.ValueOf;

public class TestMain {

    @Inject
    private static ResourceProvider resourceProvider;

    @ValueOf("database.url")
    private static String url;

    public static void main(String[] args) {
        CraftORM.start(TestMain.class);
        System.out.println(url);
    }
}
