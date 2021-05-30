package ru.otus.mainPatternsHW.hw02;

public class StopHardCommand implements Command {
    private final CommandExecutable CommandExecutable;

    public StopHardCommand(CommandExecutable CommandExecutable) {
        this.CommandExecutable = CommandExecutable;
    }

    @Override
    public void execute() {
        CommandExecutable.hardStop();
    }
}
