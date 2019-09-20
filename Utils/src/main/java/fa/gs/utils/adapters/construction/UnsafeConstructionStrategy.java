/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.adapters.construction;

import fa.gs.utils.adapters.Adapter;
import sun.reflect.ReflectionFactory;

/**
 *
 * @author Fabio A. González Sosa
 */
public class UnsafeConstructionStrategy implements Constructor {

    @Override
    public Adapter instantiate(Class<? extends Adapter> klass) throws Throwable {
        return createUnsafe(klass, Object.class);
    }

    /**
     * Fuente:
     * <a href="https://www.javaspecialists.eu/archive/Issue175.html">javaspecialists</a>
     */
    @SuppressWarnings("all")
    private <T> T createUnsafe(Class<T> klass, Class<? super T> parent) throws Exception {
        ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
        java.lang.reflect.Constructor ctor = parent.getDeclaredConstructor();
        java.lang.reflect.Constructor intConstr = rf.newConstructorForSerialization(klass, ctor);
        return klass.cast(intConstr.newInstance());
    }

}
