package ru.otus.mainPatternsHW.hw02;

public class MoveCommand implements Command {
    private final Movable movable;

    public MoveCommand(Movable movable) {
        this.movable = movable;
    }

    @Override
    public void execute() {
        try {
            movable.setPosition(Vector.sum(movable.getPosition(), movable.getVelocity()));
        } catch (Exception E) {
            throw new RuntimeException("Can't set new position:\n" + E.getMessage());
        }


    }
}

