package ru.otus.mainPatternsHW.hw02;

import java.util.concurrent.BlockingQueue;

public class CommandExecutor implements CommandExecutable {
    private final Thread thread;
    private final BlockingQueue<Command> commandQueue;
    private boolean stop = false;

    private void threadExec() {
        synchronized (this) {
            notifyAll();
        }
        while (!stop) {
            try {
                commandQueue.take().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        synchronized (this) {
            notifyAll();
        }

    }

    public CommandExecutor(BlockingQueue<Command> commandQueue) {
        this.commandQueue = commandQueue;
        thread = new Thread(this::threadExec);
    }

    @Override
    public void start() {
        thread.start();
    }

    @Override
    public void softStop() {
        if (commandQueue.isEmpty())
            stop = true;
        else
            commandQueue.add(new StopSoftCommand(this));
    }

    @Override
    public void hardStop() {
        stop = true;
    }

    @Override
    public boolean isRunning() {
        Thread.State threadState = thread.getState();
        return threadState != Thread.State.NEW && threadState != Thread.State.TERMINATED;
    }
}
