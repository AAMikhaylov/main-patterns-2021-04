package ru.otus.mainPatternsHW.hw02;

import java.util.concurrent.BlockingQueue;

public class QueueProcessingThread implements IQueueThread {
    private final Thread thread;
    private final BlockingQueue<Command> queue;
    private boolean stop;

    public QueueProcessingThread(BlockingQueue<Command> queue) {
        this.queue = queue;
        this.thread = new Thread(() -> {
            while (!stop) {
                try {
                    this.queue.take().execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void start() {
        stop = false;
        thread.start();
    }

    @Override
    public void stop() {
        stop = true;
    }

    @Override
    public void join(long ms) throws InterruptedException {
        thread.join(ms);
    }

    @Override
    public Thread.State getState() {
        return thread.getState();
    }
}
