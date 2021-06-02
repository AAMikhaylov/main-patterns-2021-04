package ru.otus.mainPatternsHW.hw02;

public interface IQueueThread {
    void start();

    void stop();

    void join(long ms) throws InterruptedException;

    Thread.State getState();

}
