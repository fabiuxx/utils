/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.injection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Fabio A. González Sosa
 */
/**
 * Clase que permite crear un proxy que permita interceptar algunos metodos a
 * manera de realizar otras acciones de control. Especificamente, este proxy
 * permite liberar los recursos tanto del
 * {@link javax.persistence.EntityManager EntityManager} como del
 * {@link javax.persistence.EntityManagerFactory EntityManagerFactory} que lo
 * creo.
 */
class EntityManagerProxy implements InvocationHandler {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Coleccion de clases que son interceptadas por este proxy.
     */
    private static final Class[] classes;
    /**
     * Coleccion de metodos a interceptar. Los mismos deben corresponder a los
     * definidos en la coleccion de clases interceptadas.
     */
    private static final Map<String, Method> methods;
    /**
     * Factory para administradores de entidades.
     */
    private final EntityManagerFactory factory;
    /**
     * Instancia creada mediante el factory de administradores de entidades.
     */
    private final EntityManager instance;
    //</editor-fold>

    static {
        // Inicializar clases para este proxy.
        classes = new Class[]{EntityManager.class};
        // Inicializar metodos que pueden ser interceptados.
        methods = new TreeMap<>();
        for (Class<?> klass : classes) {
            for (Method method : klass.getDeclaredMethods()) {
                methods.put(getKey(method), method);
            }
        }
    }

    /**
     * Constructor.
     *
     * @param factory Factory de administradores de entidades.
     */
    private EntityManagerProxy(EntityManagerFactory factory) {
        this.factory = factory;
        this.instance = factory.createEntityManager();
    }

    /**
     * Obtiene una nueva instancia de este proxy.
     *
     * @param loader Cargador de clases.
     * @param factory Factory de administradores de entidades.
     * @return Nuevo proxy.
     */
    public static Object newProxyInstance(ClassLoader loader, EntityManagerFactory factory) {
        Object proxy = java.lang.reflect.Proxy.newProxyInstance(loader, EntityManagerProxy.classes, new EntityManagerProxy(factory));
        return proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        Method declared = methods.get(getKey(method));
        if (declared != null) {
            result = declared.invoke(instance, args);
            if (method.getName().equalsIgnoreCase("close")) {
                if (factory.isOpen()) {
                    factory.close();
                }
            }
        }
        return result;
    }

    /**
     * Retorna una cadena que representa de manera unica a un metodo.
     *
     * @param method Metodo.
     * @return Cadena de identificacion.
     */
    private static String getKey(Method method) {
        return method.toString();
    }
}
