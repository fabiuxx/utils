package fa.gs.utils.collections;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.errors.Errors;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Fabio A. González Sosa on 20-nov-2018.
 */
public class Lists {

    /**
     * Retorna una lista vacia.
     *
     * @param <T> Parametro de tipo.
     * @return Lista vacia.
     */
    public static <T> List<T> empty() {
        return new LinkedList<>();
    }

    /**
     * Obtiene el primer elemento de una coleccion.
     *
     * @param <T> Parametro de tipo.
     * @param items Coleccion de objetos.
     * @return Primer objeto de la coleccion, si hubiere. Caso contrario
     * {@code null}.
     */
    public static <T> T first(Collection<T> items) {
        try {
            if (Assertions.isNullOrEmpty(items)) {
                return null;
            }

            return items.iterator().next();
        } catch (Throwable thr) {
            return null;
        }
    }

    /**
     * Encapsula una iteracion de objetos en una coleccion.
     *
     * @param <T> Parametro de tipo.
     * @param iterable Iteracion de objetos.
     * @return Coleccion.
     */
    public static <T> Collection<T> wrap(Iterable<T> iterable) {
        Collection<T> items = Lists.empty();
        if (iterable != null) {
            for (T item0 : iterable) {
                items.add(item0);
            }
        }
        return items;
    }

    /**
     * Encapsula un array de objetos en una coleccion.
     *
     * @param <T> Parametro de tipo.
     * @param array Array de objetos.
     * @return Coleccion.
     */
    public static <T> List<T> wrap(T[] array) {
        ArrayList<T> items = new ArrayList<>();
        if (!Assertions.isNullOrEmpty(array)) {
            for (T item0 : array) {
                items.add(item0);
            }
        }
        return items;
    }

    /**
     * Mapea cada objeto dentro de una coleccion a objetos de otro tipo.
     *
     * @param <TFrom> Parametro de tipo para objetos a convertir.
     * @param <TTo> Parametro de tipo para objetos convertidos.
     * @param objects Coleccion de objetos a convertir.
     * @param mapper Convertidor de objetos.
     * @return Coleccion de objetos convertidos.
     */
    public static <TFrom, TTo> Collection<TTo> map(Collection<TFrom> objects, Function<TFrom, TTo> mapper) {
        return objects.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static <T> void add(Collection<T> collection, T... elements) {
        add(collection, true, elements);
    }

    public static <T> void add(Collection<T> collection, boolean acceptsNull, T... elements) {
        if (Assertions.isNull(collection)) {
            return;
        }

        if (Assertions.isNullOrEmpty(elements)) {
            return;
        }

        for (T element : elements) {
            if (element != null) {
                collection.add(element);
            } else if (element == null && acceptsNull) {
                collection.add(element);
            } else {
                throw Errors.illegalArgument("No se permiten elementos nulos.");
            }
        }
    }

}
