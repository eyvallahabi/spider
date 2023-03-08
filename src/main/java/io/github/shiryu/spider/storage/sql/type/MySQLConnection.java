package io.github.shiryu.spider.storage.sql.type;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.shiryu.spider.storage.sql.SQLConnection;
import io.github.shiryu.spider.storage.sql.credentials.MySQLCredentials;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;

@Getter
@RequiredArgsConstructor
public class MySQLConnection implements SQLConnection {

    private final MySQLCredentials credentials;

    private DataSource source;

    private DataSource create(@NotNull final String driverClassPath, @NotNull final String host, final int port,
                              @NotNull final String database, @NotNull final String username,
                              @NotNull final String password){
        final HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://" + host + ':' + port + '/' + database);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassPath);

        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useServerPrepStmts", true);
        config.addDataSourceProperty("useLocalSessionState", true);
        config.addDataSourceProperty("rewriteBatchedStatements", true);
        config.addDataSourceProperty("cacheResultSetMetadata", true);
        config.addDataSourceProperty("cacheServerConfiguration", true);
        config.addDataSourceProperty("elideSetAutoCommits", true);
        config.addDataSourceProperty("maintainTimeStats", false);

        return new HikariDataSource(config);
    }

    private DataSource create(@NotNull final String host, final int port,
                              @NotNull final String database, @NotNull final String username,
                              @NotNull final String password, boolean legacy){
        if (legacy){
            return this.create("com.mysql.cj.jdbc.Driver", host, port, database, username, password);
        }else{
            return this.create("com.mysql.jdbc.Driver", host, port, database, username, password);
        }
    }

    private DataSource create(final boolean legacy){
        return this.create(
                this.credentials.getHost(),
                this.credentials.getPort(),
                this.credentials.getDatabase(),
                this.credentials.getUsername(),
                this.credentials.getPassword(),
                legacy
        );
    }

    @Override
    public void connect() {
        //TODO ADD LEGACY

        this.create(false);
    }
}
