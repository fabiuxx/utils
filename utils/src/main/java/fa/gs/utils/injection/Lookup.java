/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.injection;

import fa.gs.utils.misc.errors.Errors;
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
public class Lookup {

    /**
     * Realiza una búsqueda manual para la inyección de beans a través de JNDI.
     *
     * @param <T> Tipo esperado del bean a inyectar.
     * @param jndi Nombre JNDI del bean a inyectar.
     * @return Bean asociado al nombre dado. Caso contrario, {@code null}.
     */
    public static <T> Result<T> withJNDI(String jndi) {
        Result<T> result;

        try {
            Context context = new InitialContext();
            result = Lookup.withJNDI(context, jndi);
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .cause(Errors.error(thr, "Ocurrió un error inyectando objeto"))
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
    public static <T> Result<T> withJNDI(Context context, String jndi) {
        Result<T> result;

        if (context == null) {
            return Results.ko()
                    .cause(Errors.error("Contexto de inyección inválido"))
                    .build();
        }

        try {

            Object obj = context.lookup(jndi);
            T injected = (T) obj;
            result = Results.ok()
                    .value(injected)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .tag("jndi", jndi)
                    .tag("context", context.getClass().getCanonicalName())
                    .build();
        }

        return result;
    }

    /**
     * Realiza una busqueda para la inyeccion de beans CDI.
     *
     * @param <T> Tipo esperado del bean a inyectar.
     * @param klass Clase del bean a inyectar via CDI.
     * @return Instancia del CDI indicado.
     */
    public static <T> Result<T> withCDI(Class<T> klass) {
        Result<T> result;

        try {
            Object bean = CDI.current().select(klass).get();
            T instance = (bean != null) ? (T) bean : null;
            result = Results.ok()
                    .value(instance)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(Errors.error(thr, "Ocurrió un error resolviendo bean via cdi."))
                    .tag("bean.class", klass.getCanonicalName())
                    .build();
        }

        return result;
    }

    /**
     * Realiza una búsqueda manual para la inyección de managed beans.
     *
     * @param <T> Parametro de tipo.
     * @param klass Clase del bean a inyectar via BeanManager.
     * @return Manged Bean asociado a la clase indicada. Caso contrario,
     * {@code null}.
     */
    public static <T> Result<T> withBeanManager(Class<T> klass) {
        Result<T> result;

        try {
            Result<BeanManager> res = Lookup.withJNDI("java:comp/BeanManager");
            res.raise();
            BeanManager bm = res.value();
            Bean<T> mbean = (Bean<T>) bm.getBeans(klass).iterator().next();
            CreationalContext<T> ctx = bm.createCreationalContext(mbean);
            T instance = (T) (bm.getReference(mbean, klass, ctx));
            result = Results.ok()
                    .value(instance)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(Errors.error(thr, "Ocurrió un error resolviendo bean via BeanManager."))
                    .tag("bean.class", klass.getCanonicalName())
                    .build();
        }

        return result;
    }

}
