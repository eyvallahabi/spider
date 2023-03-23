package io.github.shiryu.spider.storage.impl.sql;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.shiryu.spider.storage.Storage;
import io.github.shiryu.spider.storage.entity.StorageEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class SQLStorage<I, T> implements Storage<I, T> {

    private final Cache<I, Optional<T>> cache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofMinutes(10))
            .build(this::load);

    private final T template;
    private final String table;

    private final SQLConnection connection;

    public SQLStorage(@NotNull final T template, @NotNull final String table, @NotNull final SQLConnection connection){
        this.template = template;
        this.table = table;

        this.connection = connection;

        this.init();
    }

    public void init(){
        final SQLExecutor executor = SQLExecutor.create(this.connection);

        executor.createTable(
                this.table,
                this.tableMap()
        );
    }

    private Map<String, Object> tableMap(){
        final StorageEntity entity = new StorageEntity(this.template);

        final Map<String, Object> table = new HashMap<>();

        entity.getParameters()
                .forEach(parameter -> table.put(parameter.getId(), "TEXT"));

        return table;
    }

    @Override
    public @NotNull Optional<T> load(@NotNull I id) {
        return Optional.empty();
    }
}
