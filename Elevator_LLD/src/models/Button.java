package models;

public abstract class Button {
    protected boolean isPressed;

    public Button() {
        this.isPressed = false;
    }

    public abstract void press();

    public boolean isPressed() {
        return isPressed;
    }

    public void reset() {
        this.isPressed = false;
    }
}
