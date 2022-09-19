/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import fa.gs.utils.injection.FgInjectable;
import fa.gs.utils.injection.FgInjectableScanner;
import fa.gs.utils.injection.Injector;
import fa.gs.utils.misc.Reflection;
import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;
import fa.gs.utils.tests.database.PersonaEmail;
import javax.naming.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_Injection {

    @FgInjectable
    private PersonaEmail object;

    @Test
    public void test0() throws Throwable {
        Injector injector = new Injector0();
        FgInjectableScanner scanner = FgInjectableScanner.instance(injector);
        scanner.scanAndInject(this);

        Assertions.assertNotNull(object);
    }

    private static class Injector0 extends Injector {

        @Override
        public <T> Result<T> bean(Class<T> beanClass) {
            Result<T> result;
            try {
                Object injection = Reflection.createInstance(beanClass);
                result = Results.ok()
                        .value(injection)
                        .build();
            } catch (Throwable thr) {
                result = Results.ko()
                        .cause(thr)
                        .build();
            }
            return result;
        }

        @Override
        public <T> Result<T> bean(Context context, Class<T> beanClass) {
            return bean(beanClass);
        }

    }

}
