package lol.koblizek.craftorm.test;

import lol.koblizek.craftorm.CraftORM;
import lol.koblizek.craftorm.ResourceProvider;
import lol.koblizek.craftorm.beans.Bean;
import lol.koblizek.craftorm.beans.Inject;
import lol.koblizek.craftorm.util.properties.ValueOf;

import java.util.Scanner;

@Bean
public class TestMain {

    @Bean
    public Scanner getScanner() {
        return new Scanner(System.in);
    }

    @Inject
    private static ResourceProvider resourceProvider;

    @ValueOf("database.url")
    private static String url;

    @Inject
    private static Scanner scanner;

    public static void main(String[] args) {
        CraftORM.start(TestMain.class);
    }
}
