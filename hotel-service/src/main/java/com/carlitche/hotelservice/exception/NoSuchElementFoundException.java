package com.carlitche.hotelservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchElementFoundException extends RuntimeException{

  public NoSuchElementFoundException(Class<? extends Object> clazz, String... searchParamsMap) {
    super(NoSuchElementFoundException.generateMessage(clazz.getSimpleName(),
                                                   toMap(String.class, String.class, searchParamsMap)));
  }

  private static String generateMessage(String entity, Map<String, String> searchParams) {
    return StringUtils.capitalize(entity) +
            " was not found for parameters " +
            searchParams;
  }

  private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
    // entries: key - value pair logic
    if (entries.length % 2 == 1)
      throw new IllegalArgumentException("Invalid entries");

    return IntStream.range(0, entries.length / 2).map(i -> i * 2)
            .collect(HashMap::new,
                    (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                    Map::putAll);
  }

}
