package Methods;

public class Room {
    int Bedroom;
    int Bathroom;
    int parkingspace;
    public Room(int Bedroom, int Bathroom, int parkingspace) {
        this.Bedroom = Bedroom;
        this.Bathroom = Bathroom;
        this.parkingspace = parkingspace;
    }

    public Room(int Bedroom, int Bathroom) {
        this.Bedroom = Bedroom;
        this.Bathroom = Bathroom;
    }

    public Room() {
        this.Bedroom = 1;
        this.Bathroom = 1;
        this.parkingspace = 1;
    }

    public int getBathroom() {
        return Bathroom;
    }

    public int getBedroom() {
        return Bedroom;
    }

    public int getparkingspace() {
        return parkingspace;
    }
}
