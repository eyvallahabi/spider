package io.github.shiryu.spider.storage.sql.type;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.shiryu.spider.storage.sql.SQLConnection;
import io.github.shiryu.spider.storage.sql.credentials.SQLiteCredentials;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;

@Getter
@RequiredArgsConstructor
public class SQLiteConnection implements SQLConnection{

    private final SQLiteCredentials credentials;

    private DataSource source;

    private DataSource create(@NotNull final String driverClassPath, @NotNull final String poolName,
                              @NotNull final String filePath){
        final HikariConfig config = new HikariConfig();
        config.setPoolName(poolName);
        config.setDriverClassName(driverClassPath);
        config.setJdbcUrl("jdbc:sqlite:" + filePath);
        config.setMaxLifetime(60000L);
        config.setIdleTimeout(45000L);
        config.setMaximumPoolSize(50);

        return new HikariDataSource(config);
    }

    private DataSource create(@NotNull final String poolName, @NotNull final String filePath){
        return this.create("org.sqlite.JDBC", poolName, filePath);
    }

    @Override
    public void connect() {
        this.source = this.create(
                this.credentials.getPool(),
                this.credentials.getPath()
        );
    }
}
