package main.Resources.Utils;

import java.util.function.BiConsumer;
import java.util.function.Function;

public record Column<T>(
        String label,
        Function<T,?> getter,
        BiConsumer<T, Object> setter
) {
}
