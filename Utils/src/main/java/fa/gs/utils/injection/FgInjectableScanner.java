/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.injection;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.Reflection;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.result.simple.Result;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class FgInjectableScanner {

    private final Injector injector;

    private FgInjectableScanner(Injector injector) {
        this.injector = injector;
    }

    public static FgInjectableScanner instance(Injector injector) {
        return new FgInjectableScanner(injector);
    }

    public void scanAndInject(Object instance) {
        Collection<Field> annotatedFields = collectInjectableFields(instance);
        injectFieldValues(instance, annotatedFields);
    }

    public Collection<Field> collectInjectableFields(Object instance) {
        Collection<Field> annotatedField = Lists.empty();

        if (instance != null) {
            Class<?> klass = instance.getClass();
            Collection<Field> fields = Reflection.getAllFields(klass);
            for (Field field : fields) {
                if (field.isAnnotationPresent(FgInjectable.class)) {
                    annotatedField.add(field);
                }
            }
        }

        return annotatedField;
    }

    public void injectFieldValues(Object instance, Collection<Field> injectableFields) {
        for (Field field : injectableFields) {
            Class<?> beanClass = field.getType();
            Object currentValue = Reflection.get(instance, field, beanClass);
            if (currentValue == null) {
                Result result = injector.bean(beanClass);
                if (result.isFailure()) {
                    Errors.dump(System.err, result);
                } else {
                    Object injectedValue = result.value();
                    Reflection.set(instance, field, injectedValue);
                }
            }
        }
    }

}
