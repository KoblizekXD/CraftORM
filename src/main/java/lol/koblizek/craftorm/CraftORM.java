package lol.koblizek.craftorm;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lol.koblizek.craftorm.beans.Bean;
import lol.koblizek.craftorm.beans.BeanLoader;
import lol.koblizek.craftorm.beans.Inject;
import lol.koblizek.craftorm.beans.InjectionScanner;
import lol.koblizek.craftorm.test.TestMain;
import lol.koblizek.craftorm.util.properties.ValueOf;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public final class CraftORM {

    private static CraftORM craftORM;

    @Bean
    static CraftORM getInstance() {
        return craftORM;
    }

    private final HikariConfig config;
    private final HikariDataSource dataSource;
    private final BeanLoader beanLoader;

    private CraftORM(Class<?> executionType, String url) {
        beanLoader = new BeanLoader(true);
        this.config = new HikariConfig();
        beanLoader.loadVirtual(() -> this);
        if (url == null) {
            config.setJdbcUrl(beanLoader.getBean(ResourceProvider.class)
                    .getPropertyFile("database.properties")
                    .getProperty("database.url"));
        } else config.setJdbcUrl(url);
        config.addDataSourceProperty("cachePrepStmts", "true" );
        config.addDataSourceProperty("prepStmtCacheSize", "250" );
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048" );
        this.dataSource = new HikariDataSource(config);
        // only if above true
        // beanLoader.performFieldInjection(executionType.getPackageName());
        InjectionScanner scanner = new InjectionScanner(beanLoader, ValueOf.class, Inject.class);
        scanner.injectFields(executionType.getPackageName());
    }

    public static void start(Class<?> main, String dataSourceUrl) {
        if (craftORM == null)
            craftORM = new CraftORM(main, dataSourceUrl);
    }

    public static void start(Class<?> main) {
        start(main, null);
    }
}
