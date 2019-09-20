/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.misc.errors;

import py.com.generica.utils.result.utils.Failure;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Errors {

    public static AppErrorException.Builder builder() {
        return new AppErrorExceptionBuilder();
    }

    public static AppErrorException failure(Failure failure) {
        return new AppErrorException(failure);
    }

    public static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("TODO");
    }

    public static void popStackTrace(Throwable throwable) {
        popStackTrace(throwable, 1);
    }

    public static void popStackTrace(Throwable throwable, int n) {
        StackTraceElement[] originalStackTrace = throwable.getStackTrace();
        StackTraceElement[] cleanedUpStackTrace = new StackTraceElement[originalStackTrace.length - n];
        System.arraycopy(originalStackTrace, n, cleanedUpStackTrace, 0, cleanedUpStackTrace.length);
        throwable.setStackTrace(cleanedUpStackTrace);
    }

}
