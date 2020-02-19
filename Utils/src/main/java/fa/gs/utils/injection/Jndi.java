/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.injection;

import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Jndi {

    //<editor-fold defaultstate="collapsed" desc="JNDI">
    /**
     * Realiza una búsqueda manual para la inyección de beans a través de JNDI.
     *
     * @param <T> Tipo esperado del bean a inyectar.
     * @param jndi Nombre JNDI del bean a inyectar.
     * @return Bean asociado al nombre dado. Caso contrario, {@code null}.
     */
    public static <T> Result<T> lookup(String jndi) {
        Result<T> result;

        try {
            Context context = new InitialContext();
            result = lookup(context, jndi);
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .message("Ocurrió un error inyectando objeto")
                    .tag("jndi", jndi)
                    .build();
        }

        return result;
    }

    /**
     * Realiza una búsqueda manual para la inyección de beans a través de JNDI.
     *
     * @param <T> Tipo esperado del bean a inyectar.
     * @param context Contexto de busqueda.
     * @param jndi Nombre JNDI del bean a inyectar.
     * @return Bean asociado al nombre dado. Caso contrario, {@code null}.
     */
    public static <T> Result<T> lookup(Context context, String jndi) {
        Result<T> result;

        try {
            if (context == null) {
                return Results.ko()
                        .message("Contexto de inyección inválido")
                        .build();
            }

            Object obj = context.lookup(jndi);
            T injected = (T) obj;
            result = Results.ok()
                    .value(injected)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .message("Ocurrió un error inyectando objeto")
                    .tag("jndi", jndi)
                    .tag("context", context.getClass().getCanonicalName())
                    .build();
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="CDI">
    /**
     * Realiza una busqueda para la inyeccion de beans CDI.
     *
     * @param <T> Tipo esperado del bean a inyectar.
     * @param cdiBeanClass Clase del bean CDI a inyectar.
     * @return Instancia del CDI indicado.
     */
    public static <T> T lookupCdiBean(Class<T> cdiBeanClass) {
        try {
            Object bean = CDI.current().select(cdiBeanClass).get();
            return (bean != null) ? (T) bean : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Realiza una búsqueda manual para la inyección de managed beans.
     *
     * @param <T> Parametro de tipo.
     * @param managedBeanClass Clase del bean a inyectar.
     * @return Manged Bean asociado a la clase indicada. Caso contrario,
     * {@code null}.
     */
    public static <T> T lookupManagedBean(Class<T> managedBeanClass) {
        try {
            Result<BeanManager> res = Jndi.lookup("java:comp/BeanManager");
            res.raise();
            BeanManager bm = res.value();
            Bean<T> mbean = (Bean<T>) bm.getBeans(managedBeanClass).iterator().next();
            CreationalContext<T> ctx = bm.createCreationalContext(mbean);
            return (T) (bm.getReference(mbean, managedBeanClass, ctx));
        } catch (Throwable thr) {
            return null;
        }
    }
    //</editor-fold>

}
