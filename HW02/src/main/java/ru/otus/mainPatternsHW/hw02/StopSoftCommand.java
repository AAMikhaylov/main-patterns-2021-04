package ru.otus.mainPatternsHW.hw02;

public class StopSoftCommand implements Command {
    private final CommandExecutable CommandExecutable;

    public StopSoftCommand(CommandExecutable CommandExecutable) {
        this.CommandExecutable = CommandExecutable;
    }

    @Override
    public void execute() {
        CommandExecutable.softStop();
    }
}
