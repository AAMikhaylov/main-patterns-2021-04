package ru.otus.mainPatternsHW.hw02;

public class StartCommand implements Command {
    private final CommandExecutable CommandExecutable;

    public StartCommand(CommandExecutable CommandExecutable) {
        this.CommandExecutable = CommandExecutable;
    }

    @Override
    public void execute() {
        CommandExecutable.start();
    }
}
