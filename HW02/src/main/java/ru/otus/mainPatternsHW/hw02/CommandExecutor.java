package ru.otus.mainPatternsHW.hw02;

import java.util.concurrent.BlockingQueue;

public class CommandExecutor implements CommandExecutable {
    private final IQueueThread processingThread;
    private final BlockingQueue<Command> commandQueue;


    public CommandExecutor(IQueueThread processingThread, BlockingQueue<Command> commandQueue) {
        this.processingThread = processingThread;
        this.commandQueue = commandQueue;
    }

    @Override
    public void start() {
        processingThread.start();
    }

    @Override
    public void softStop() {
        if (commandQueue.isEmpty()) {
            processingThread.stop();
        } else {
            if (commandQueue.stream().noneMatch(x -> x instanceof StopSoftCommand))
                commandQueue.add(new StopSoftCommand(this));
        }
    }

    @Override
    public void hardStop() {
        processingThread.stop();
    }

}
