package io.github.shiryu.spider.storage.sql;

import io.github.shiryu.spider.storage.Storage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class SQLStorage implements Storage<SQLExecutor> {

    private final SQLConnection connection;

    @NotNull
    public SQLExecutor getExecutor(){
        return new SQLExecutor(
                this.connection
        );
    }
}
