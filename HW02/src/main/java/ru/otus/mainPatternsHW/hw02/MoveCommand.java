package ru.otus.mainPatternsHW.hw02;

public class MoveCommand implements Command {
    private final Movable movable;

    public MoveCommand(Movable movable) {
        this.movable = movable;
    }

    @Override
    public void execute() {
        movable.setPosition(Vector.sum(movable.getPosition(), movable.getVelocity()));
    }
}

