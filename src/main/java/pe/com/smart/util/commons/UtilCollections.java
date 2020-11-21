package pe.com.smart.util.commons;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UtilCollections {

    public static boolean isListEmpty(List<?> list) {
        return (list == null || list.isEmpty());
    }

}
