package by.grsu.schedule.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.net.URI;

@UtilityClass
public class RestUtils {
    @SneakyThrows
    public static URI buildUri(String path) {
        return new URI(path);
    }
}
