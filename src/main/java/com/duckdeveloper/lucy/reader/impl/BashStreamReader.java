package com.duckdeveloper.lucy.reader.impl;

import com.duckdeveloper.lucy.reader.StreamReader;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

@Component
public class BashStreamReader implements StreamReader<InputStream, String> {

    @Override
    @SneakyThrows
    public String read(InputStream inputStream) {
        if (inputStream == null)
            return "";

        var bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        var joiner = new StringJoiner("\n");

        bufferedReader.lines()
                .forEach(joiner::add);

        bufferedReader.close();
        inputStream.close();
        return joiner.toString();
    }
}