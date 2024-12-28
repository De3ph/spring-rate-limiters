package com.example.springratelimiters.service.intf;

public interface CacheService<K, V> {
    String ping();

    void putToCache(K key, V value);

    String getFromCache(String key);
}
