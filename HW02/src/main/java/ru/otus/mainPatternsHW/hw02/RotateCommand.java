package ru.otus.mainPatternsHW.hw02;

public class RotateCommand implements Command {
    private final Rotable rotable;

    public RotateCommand(Rotable rotable) {
        this.rotable = rotable;
    }

    @Override
    public void execute() {
        try {
            rotable.setDirection((rotable.getDirection() + rotable.getAngularVelocity()) % rotable.getMaxDirections());
        } catch (Exception E) {
            throw new RuntimeException("Can't set new direction:\n" + E.getMessage());
        }

    }


}
