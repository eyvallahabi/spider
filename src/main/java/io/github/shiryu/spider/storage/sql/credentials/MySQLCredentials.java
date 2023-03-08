package io.github.shiryu.spider.storage.sql.credentials;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MySQLCredentials {

    private final String host;
    private final String username;
    private final String password;

    private final String database;

    private final int port;
}
