package lol.koblizek.craftorm;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lol.koblizek.craftorm.beans.BeanLoader;
import lol.koblizek.craftorm.beans.Inject;
import lol.koblizek.craftorm.beans.InjectionEncounterHandler;
import lol.koblizek.craftorm.util.ClassScanner;
import lol.koblizek.craftorm.util.properties.ValueOf;

public final class CraftORM {

    private static CraftORM craftORM;

    private final HikariConfig config;
    private final HikariDataSource dataSource;
    private final BeanLoader beanLoader;

    private CraftORM(Class<?> executionType, String url) {
        ClassScanner scanner  = new ClassScanner();

        beanLoader = new BeanLoader(true);
        this.config = new HikariConfig();
        beanLoader.loadVirtual(() -> this);
        beanLoader.loadPackage(executionType.getPackageName());
        if (url == null) {
            config.setJdbcUrl(beanLoader.getBean(ResourceProvider.class)
                    .getPropertyFile("database.properties")
                    .getProperty("database.url"));
        } else config.setJdbcUrl(url);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        this.dataSource = new HikariDataSource(config);
        scanner.scanPackage(executionType.getClassLoader(),
                executionType.getPackageName(),
                new InjectionEncounterHandler(beanLoader, ValueOf.class, Inject.class));
    }

    public static void start(Class<?> main, String dataSourceUrl) {
        if (craftORM == null)
            craftORM = new CraftORM(main, dataSourceUrl);
    }

    public static void start(Class<?> main) {
        start(main, null);
    }
}
