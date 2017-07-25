package org.zh.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA 2017.1.2. <br/>
 * User: ZhaoHang  <br/>
 * Date: 2017/5/24  <br/>
 * Time: 14:02  <br/>
 *
 * @Description: 用一句话描述
 */
public class ThreadPoolExecutorFactory {

    private static final int CORE_POOL_SIZE = 20;

    /**
     * 线程池对象
     */
    private static ExecutorService executorPool = null;

    private ThreadPoolExecutorFactory() {

    }

    public synchronized static ExecutorService getThreadPoolExecutor() {
        if (null == executorPool) {
            executorPool = Executors.newFixedThreadPool(CORE_POOL_SIZE);
        }
        return executorPool;
    }


}
