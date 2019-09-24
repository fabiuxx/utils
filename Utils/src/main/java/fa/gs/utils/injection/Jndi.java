/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.injection;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Jndi {

    //<editor-fold defaultstate="collapsed" desc="EJB">
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
            Context c = new InitialContext();
            Object obj = c.lookup(jndi);
            Assertions.raiseIfNull(obj);

            T injected = (T) obj;
            result = Results.ok()
                    .value(injected)
                    .build();
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
     * Realiza una búsqueda manual para la inyeccion de beans a través de JNDI
     * utilizando clases dentro de este mismo módulo.
     *
     * @param <T> Tipo esperado del bean a inyectar.
     * @param namespace Espacio de nombres para la busqueda.
     * @param module Nombre del modulo en el cual se realiza la busqueda del
     * bean.
     * @param beanClass Clase del bean a inyectar.
     * @return Instancia del bean indicado. Caso contrario, {@code null}.
     */
    public static <T> Result<T> lookup(String namespace, String module, Class<T> beanClass) {
        String jndi = "java:global";
        if (!Assertions.stringNullOrEmpty(namespace)) {
            jndi = jndi + "/" + namespace;
        }
        if (!Assertions.stringNullOrEmpty(module)) {
            jndi = jndi + "/" + module;
        }
        jndi = jndi + "/" + beanClass.getSimpleName();
        return lookup(jndi);
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

    //<editor-fold defaultstate="collapsed" desc="JPA">
    /**
     * Realiza una busqueda manual para la inyeccion de un administrador de
     * entidades.
     *
     * @param persistenceUnitName Nombre de la unidad de persistencia.
     * @return Administrador de entidadres. Caso contrario, {@code null}.
     */
    public static EntityManager lookupEntityManager(String persistenceUnitName) {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
            Object instance = EntityManagerProxy.newProxyInstance(Jndi.class.getClassLoader(), factory);
            return (EntityManager) instance;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }
    //</editor-fold>

}
