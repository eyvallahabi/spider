package io.github.shiryu.spider.storage;

public interface StorageConnection {

    void connect();

    void close();
}
