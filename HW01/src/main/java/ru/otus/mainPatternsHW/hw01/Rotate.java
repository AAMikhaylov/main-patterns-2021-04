package ru.otus.mainPatternsHW.hw01;

public class Rotate {
    private final Rotable rotable;

    public Rotate(Rotable rotable) {
        this.rotable = rotable;
    }

    public void execute() {
        rotable.setDirection((rotable.getDirection() + rotable.getAngularVelocity()) % rotable.getMaxDirections());
    }


}
