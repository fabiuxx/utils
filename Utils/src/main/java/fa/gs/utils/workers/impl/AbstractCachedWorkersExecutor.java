/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.workers.impl;

import fa.gs.utils.workers.ApplicationWorkerExecutor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class AbstractCachedWorkersExecutor extends ApplicationWorkerExecutor {

    @Override
    protected ExecutorService createExecutorService() {
        return Executors.newCachedThreadPool();
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
