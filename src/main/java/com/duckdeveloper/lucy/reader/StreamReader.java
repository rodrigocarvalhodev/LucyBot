package com.duckdeveloper.lucy.reader;

public interface StreamReader<K, V> {

    V read(K k);

}
