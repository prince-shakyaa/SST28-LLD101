package models;

public class InternalButton extends Button {
    private int floorNumber;

    public InternalButton(int floorNumber) {
        super();
        this.floorNumber = floorNumber;
    }

    @Override
    public void press() {
        this.isPressed = true;
        System.out.println("Internal button pressed for floor " + floorNumber);
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
