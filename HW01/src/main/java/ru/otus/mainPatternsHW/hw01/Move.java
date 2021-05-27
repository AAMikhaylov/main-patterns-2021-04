package ru.otus.mainPatternsHW.hw01;

public class Move {
    private final Movable movable;

    public Move(Movable movable) {
        this.movable = movable;
    }

    public void execute() {
        movable.setPosition(Vector.sum(movable.getPosition(), movable.getVelocity()));
    }

}

