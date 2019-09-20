package py.com.generica.utils.collections.slices;

/**
 * Created by Fabio A. González Sosa on 20-nov-2018.
 */
public class AbstractSlice<T> implements Slice<T> {

    private T elements;
    private int subtotal;
    private int total;

    AbstractSlice(T elements, int subtotal, int total) {
        this.elements = elements;
        this.subtotal = subtotal;
        this.total = total;
    }

    @Override
    public T getElements() {
        return elements;
    }

    @Override
    public int getSubtotal() {
        return subtotal;
    }

    @Override
    public int getTotal() {
        return total;
    }

}
