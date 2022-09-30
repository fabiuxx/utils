package fa.gs.utils.collections;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.text.Strings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java8.util.stream.Collectors;
import java8.util.stream.Stream;
import java8.util.stream.StreamSupport;

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
     * Obtiene la cantidad de elementos en una coleccion.
     *
     * @param <T> Parametro de tipo.
     * @param items Coleccion de objetos.
     * @return Cantidad de elementos en la coleccion.
     */
    public static <T> int size(Collection<T> items) {
        if (Assertions.isNullOrEmpty(items)) {
            return 0;
        } else {
            return items.size();
        }
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
     * Obtiene el ultimo elemento de una coleccion.
     *
     * @param <T> Parametro de tipo.
     * @param items Coleccion de objetos.
     * @return Ultimo objeto de la coleccion, si hubiere. Caso contrario
     * {@code null}.
     */
    public static <T> T last(Collection<T> items) {
        try {
            if (Assertions.isNullOrEmpty(items)) {
                return null;
            }

            return at(items, items.size() - 1);
        } catch (Throwable thr) {
            return null;
        }
    }

    public static <T> T at(Collection<T> items, int pos) {
        if (Assertions.isNullOrEmpty(items)) {
            throw new IndexOutOfBoundsException(Strings.format("%s >= (empty)", pos));
        }

        if (items instanceof List) {
            List<T> list = (List<T>) items;
            return list.get(pos);
        } else {
            int i = 0;
            Iterator<T> it = items.iterator();
            while (it.hasNext()) {
                T item = it.next();
                if (i == pos) {
                    return item;
                }
                i++;
            }
            throw new IndexOutOfBoundsException(Strings.format("%s >= %s", pos, Lists.size(items)));
        }
    }

    /**
     * Encapsula una iteracion de objetos en una coleccion.
     *
     * @param <T> Parametro de tipo.
     * @param iterable Iteracion de objetos.
     * @return Coleccion.
     */
    public static <T> List<T> wrap(Iterable<T> iterable) {
        List<T> items = Lists.empty();
        if (iterable != null) {
            for (T item0 : iterable) {
                items.add(item0);
            }
        }
        return items;
    }

    /**
     * Encapsula un objeto en una coleccion.
     *
     * @param <T> Parametro de tipo.
     * @param element Objecto.
     * @return Coleccion.
     */
    public static <T> List<T> wrap(T element) {
        ArrayList<T> items = new ArrayList<>();
        if (element != null) {
            items.add(element);
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
            items.addAll(Arrays.asList(array));
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
        Stream<TFrom> stream = StreamSupport.stream(objects);
        return stream
                .map(mapper::apply)
                .collect(Collectors.toList());
    }

    /**
     * Agrega elementos a una coleccion del mismo tipo.
     *
     * @param <T> Parametro de tipo.
     * @param collection Coleccion de elementos.
     * @param elements Elementos a agregar.
     */
    public static <T> void add(Collection<T> collection, T... elements) {
        add(collection, true, elements);
    }

    /**
     * Agrega elementos a una coleccion del mismo tipo.
     *
     * @param <T> Parametro de tipo.
     * @param collection Coleccion de elementos.
     * @param acceptsNull Bandera que indica si se aceptan valores nulos.
     * @param elements Elementos a agregar.
     * @throws IllegalArgumentException Si {@code acceptsNull == true} y existe
     * algun elemento nulo por agregar.
     */
    public static <T> void add(Collection<T> collection, boolean acceptsNull, T... elements) {
        add(collection, acceptsNull, true, elements);
    }

    /**
     * Agrega elementos a una coleccion del mismo tipo.
     *
     * @param <T> Parametro de tipo.
     * @param collection Coleccion de elementos.
     * @param acceptsNull Bandera que indica si se aceptan valores nulos.
     * @param exceptionOnNull Bandera que indica si se genera una excepcion al
     * encontrar valores nulos.
     * @param elements Elementos a agregar.
     * @throws IllegalArgumentException Si {@code acceptsNull == true} y existe
     * algun elemento nulo por agregar.
     */
    public static <T> void add(Collection<T> collection, boolean acceptsNull, boolean exceptionOnNull, T... elements) {
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
            } else if (element == null && !acceptsNull && exceptionOnNull) {
                throw Errors.illegalArgument("No se permiten elementos nulos.");
            }
        }
    }

    /**
     * Agrega elementos a una coleccion del mismo tipo.
     *
     * @param <T> Parametro de tipo.
     * @param collection Coleccion a la cual se agregan elementos.
     * @param others Elementos a agregar.
     */
    public static <T> void add(Collection<T> collection, Collection<T> others) {
        add(collection, others, false);
    }

    /**
     * Agrega elementos a una coleccion del mismo tipo.
     *
     * @param <T> Parametro de tipo.
     * @param collection Coleccion a la cual se agregan elementos.
     * @param others Elementos a agregar.
     * @param clearBefore Si la coleccion inicial debe ser limpiada previamente.
     */
    public static <T> void add(Collection<T> collection, Collection<T> others, boolean clearBefore) {
        if (collection == null || others == null) {
            return;
        }

        if (clearBefore) {
            collection.clear();
        }
        collection.addAll(others);
    }

    /**
     * Obtiene un stream de procesamiento para colecciones.
     *
     * @param <T> Parametro de tipo.
     * @param collection Coleccion inicial.
     * @return Stream de datos.
     */
    public static <T> Stream<T> stream(Collection<T> collection) {
        if (collection == null) {
            collection = Lists.empty();
        }
        return StreamSupport.stream(collection);
    }

    /**
     * Obtiene un stream de procesamiento para un array de elementos.
     *
     * @param <T> Parametro de tipo.
     * @param array Array de elementos.
     * @return Stream de datos.
     */
    public static <T> Stream<T> stream(T[] array) {
        return stream(wrap(array));
    }

    /**
     * Convierte una coleccion de colecciones en una sola.
     *
     * @param <T> Parametro de tipo.
     * @param collections Coleccion de colecciones.
     * @return Coleccion que contiene a todos los elementos individuales de las
     * colecciones.
     */
    public static <T> Collection<T> flatten(Iterable<Collection<T>> collections) {
        Collection<T> collection = Lists.empty();
        for (Collection<T> collection0 : collections) {
            if (!Assertions.isNullOrEmpty(collection0)) {
                collection.addAll(collection0);
            }
        }
        return collection;
    }

}
