package ru.otus.mainPatternsHW.hw02;

public interface CommandExecutable {
    void start();

    void softStop();

    void hardStop();

    boolean isRunning();
}
