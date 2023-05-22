package fa.gs.utils.misc.io;

import fa.gs.utils.misc.NO;
import java.io.IOException;
import java.io.InputStream;

/**
 * Permite encapsular un stream de datos de entrada y deshabilita el cierre del
 * mismo, a manera que pueda ser cerrado de forma explícita por otros
 * componentes.
 */
public class UnclosableInputStream extends InputStream {

    /**
     * Stream de datos encapsulado.
     */
    private final InputStream wrapped;

    /**
     * Constructor.
     *
     * @param wrapped Stream principal encapsulado.
     */
    public UnclosableInputStream(InputStream wrapped) {
        this.wrapped = wrapped;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int read() throws IOException {
        return wrapped.read();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void close() throws IOException {
        NO.OP();
    }

}
