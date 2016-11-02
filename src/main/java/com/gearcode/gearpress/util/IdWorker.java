package com.gearcode.gearpress.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdWorker {
    private static Logger logger = LoggerFactory.getLogger(IdWorker.class);


    private volatile static IdWorker instance = null;

    /**
     * @param workerId 不能超过1023, 不能小于0
     * @return
     */
    public static IdWorker getInstance(long workerId) {
        if (instance == null) {
            synchronized (IdWorker.class) {

                if (instance == null) {
                    // 优先使用-D参数中的app.id
                    String appId = System.getProperty("app.id");
                    if(appId != null) {
                        instance = new IdWorker(Integer.parseInt(appId));
                        logger.info("Create IdWorker instance success, app.id: " + appId);
                    } else {
                        instance = new IdWorker(workerId);
                        logger.info("Create IdWorker instance success, workerId: " + workerId);
                    }
                }

            }
        }
        return instance;
    }

    private IdWorker(long workerId) {
        if(workerId < 0 || workerId > maxWorkerId) {
            throw new RuntimeException("workerId must greater than 0 and less than " + maxWorkerId);
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1 & sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            vibrance = ~vibrance & 1;
            this.sequence = vibrance;
        }
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", (this.lastTimestamp - timestamp)));
        }

        this.lastTimestamp = timestamp;
        return timestamp - twepoch << timestampLeftShift
                | workerId << workerIdShift | this.sequence;
    }

    // 抖动值, 每一个新的毫秒抖动值变换一次(0,1), 如果不抖动则会导致每毫秒新生成的ID默认的sequence都为0, 那么每个ID都是偶数
    private static int vibrance = 0;

    private final long workerId;
    static final long twepoch = 1288834974657L;
    private long sequence = 0L;
    static final long workerIdBits = 10L;
    static final long maxWorkerId = -1L ^ -1L << workerIdBits;
    static final long sequenceBits = 12L;

    static final long workerIdShift = sequenceBits;
    static final long timestampLeftShift = sequenceBits + workerIdBits;
    static final long sequenceMask = -1L ^ -1L << sequenceBits;

    private long lastTimestamp = -1L;

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {

        for (int j = 0; j < 20; j++) {
            long id = IdWorker.getInstance(1).nextId();
            System.out.println(j + ", " + id);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


//        final Vector<Long> set = new Vector<Long>();
//
//        final CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("======================================");
//                TreeSet<Long> longs = new TreeSet<Long>(set);
//                for (Long l : longs) {
//                    System.out.println(l);
//                }
//            }
//        });
//
//        for (int i = 0; i < 20; i++) {
//            final int n = i;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for (int j = 0; j < 20; j++) {
//                        long id = IdWorker.getInstance(n).nextId();
//                        System.out.println(n + ", " + id);
//
//                        if(set.contains(id)) {
//                            throw new RuntimeException("duplicate id");
//                        }
//                        set.add(id);
//                    }
//
//                    try {
//                        barrier.await();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (BrokenBarrierException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
    }
}