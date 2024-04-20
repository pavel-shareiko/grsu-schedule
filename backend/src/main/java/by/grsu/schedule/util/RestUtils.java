package by.grsu.schedule.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.net.URI;
import java.util.Map;

@UtilityClass
public class RestUtils {
    @SneakyThrows
    public static URI buildUri(String path) {
        return new URI(path);
    }

    @SneakyThrows
    public static URI buildUri(String path, Map<String, String> params) {
        if (params.isEmpty()) {
            return buildUri(path);
        }

        StringBuilder sb = new StringBuilder(path);
        sb.append('?');
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey())
                    .append('=')
                    .append(entry.getValue())
                    .append('&');
        }
        sb.deleteCharAt(sb.length() - 1);

        return buildUri(sb.toString());
    }
}
