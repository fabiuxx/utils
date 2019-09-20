/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.misc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Fabio A. González Sosa
 */
public final class ParameterReader {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Nombre del parametro.
     */
    private final String clave;

    /**
     * Valor actual leido.
     */
    private volatile String valor;

    /**
     * Tiempo de ultima actualizacion del valor leido.
     */
    private volatile long lastUpdate;

    /**
     * Tiempo minimo entre actualizaciones del valor leido.
     */
    private long updateDeltaMillis;

    /**
     * Lock de control de concurrencia para operaciones.
     */
    private final ReadWriteLock lock;
    //</editor-fold>

    /**
     * Constructor.
     *
     * @param clave Nombre del parametro.
     */
    public ParameterReader(String clave) {
        this(clave, TimeUnit.SECONDS.toMillis(5));
    }

    /**
     * Constructor.
     *
     * @param clave NOmbre del parametro.
     * @param updateDeltaMillis Tiempo minimo entre actualizaciones del valor
     * leido.
     */
    public ParameterReader(String clave, long updateDeltaMillis) {
        this.updateDeltaMillis = updateDeltaMillis;
        this.lastUpdate = 0;
        this.clave = clave;
        this.valor = null;
        this.lock = new ReentrantReadWriteLock(true);
    }

    /**
     * Indica si el valor del parametro puede ser leido desde la base de datos.
     * Esto es, es posible llamar a
     * {@link ParameterReader#read(java.sql.Connection) red} para intentar
     * actualizar el valor del parametro.
     *
     * @return {@code true} si es posible actualizar el valor del parametro,
     * caso contrario {@code false}.
     */
    public boolean canRead() {
        lock.readLock().lock();
        try {
            long currentMillis = System.currentTimeMillis();
            long diffMillis = Math.abs(currentMillis - lastUpdate);
            return diffMillis > updateDeltaMillis;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Lee directamente el valor del parametro desde BD y actualiza la copia en
     * memoria para su uso directo.
     *
     * @param connection Conexion a la base de datos.
     * @return Valor del parametro, que puede ser el valor actualizaco o bien el
     * mismo valor anterior si no se cumple el tiempo minimo de actualizacion.
     */
    public String read(Connection connection) {
        lock.writeLock().lock();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement("select valor from trans.parametro where clave = ?");
            ps.setString(1, clave);
            rs = ps.executeQuery();
            if (rs.next()) {
                valor = rs.getString(1);
            }
        } catch (Throwable thr) {
            thr.printStackTrace(System.err);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Throwable thr) {
                ;
            }
            lastUpdate = System.currentTimeMillis();
            lock.writeLock().unlock();
        }
        return valor;
    }

    /**
     * Retorna el valor en memoria obtenido desde base de datos, si hubiere.
     *
     * @return Valor del parametro.
     */
    public String valor() {
        lock.readLock().lock();
        try {
            return valor;
        } finally {
            lock.readLock().unlock();
        }
    }
}
