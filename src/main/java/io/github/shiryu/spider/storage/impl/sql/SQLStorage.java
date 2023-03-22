package io.github.shiryu.spider.storage.impl.sql;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.shiryu.spider.storage.Storage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class SQLStorage<I, T> implements Storage<I, T> {

    private final Cache<I, Optional<T>> cache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofMinutes(10))
            .build(this::load);

    private final SQLConnection connection;

    public void init(){
        //TODO
    }

    @Override
    public @NotNull Optional<T> load(@NotNull I id) {
        return Optional.empty();
    }
}
