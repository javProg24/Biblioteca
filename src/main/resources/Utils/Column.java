package main.resources.Utils;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Representa una columna genérica que contiene un rótulo, un getter y un setter.
 *
 * @param <T> El tipo de la entidad asociada a la columna.
 * @param label El rótulo o etiqueta de la columna.
 * @param getter Una función que obtiene el valor de la columna a partir de una entidad.
 * @param setter Un consumidor que establece el valor de la columna en una entidad.
 */
public record Column<T>(
        String label,
        Function<T,?> getter,
        BiConsumer<T, Object> setter
) {
}
