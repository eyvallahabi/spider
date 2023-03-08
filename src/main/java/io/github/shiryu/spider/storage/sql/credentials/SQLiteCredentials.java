package io.github.shiryu.spider.storage.sql.credentials;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SQLiteCredentials {

    private final String pool;
    private final String path;
}
