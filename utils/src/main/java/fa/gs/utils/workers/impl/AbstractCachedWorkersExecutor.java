/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.workers.impl;

import fa.gs.utils.misc.text.Strings;
import fa.gs.utils.workers.ApplicationWorker;
import fa.gs.utils.workers.ApplicationWorkerExecutor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class AbstractCachedWorkersExecutor extends ApplicationWorkerExecutor implements ThreadFactory {

    @Override
    protected ExecutorService createExecutorService() {
        return Executors.newCachedThreadPool(this);
    }

    @Override
    public Thread newThread(Runnable runnable) {
        if (runnable == null) {
            return null;
        }

        String threadName;
        if (runnable instanceof ApplicationWorker) {
            ApplicationWorker worker = (ApplicationWorker) runnable;
            threadName = worker.getWorkerName();
        } else {
            threadName = runnable.getClass().getName();
        }

        String workerName = Strings.format("worker@%x--%s", System.identityHashCode(runnable), threadName);
        return new Thread(runnable, workerName);
    }

    @Override
    protected boolean destroyExecutorService(ExecutorService executor) {
        try {
            executor.shutdown();
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    return false;
                }
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            return false;
        }
    }

}
