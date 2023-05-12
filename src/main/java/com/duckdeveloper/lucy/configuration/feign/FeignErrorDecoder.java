package com.duckdeveloper.lucy.configuration.feign;

import com.duckdeveloper.lucy.exception.CryptoNotFoundException;
import com.duckdeveloper.lucy.exception.ExchangeNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        int responseStatus = response.status();
        if (responseStatus == 400) {
            return new CryptoNotFoundException();
        }
        if (responseStatus == 404) {
            return new ExchangeNotFoundException();
        }
        return new RuntimeException();
    }
}
