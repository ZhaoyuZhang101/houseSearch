package Methods;

public class House {
    String ID;
    String Type;
    String Price;
    Location location;
    Methods.Room Room;
    String promoted;

    House(String ID, String Type, String Price, Location location, Room Room) {
        this.ID = ID;
        this.Type = Type;
        this.Price = Price;
        this.location = location;
        this.Room = Room;
        this.promoted = "False";
    }

    House(String ID, String Type, String Price, Location location, Room Room, String promoted) {
        this.ID = ID;
        this.Type = Type;
        this.Price = Price;
        this.location = location;
        this.Room = Room;
        this.promoted = promoted;
    }

    public String getID() {
        return ID;
    }

    public String getType() {
        return Type;
    }

    public String getPrice() {
        return Price;
    }

    public Location getLocation() {
        return location;
    }

    public Methods.Room getRoom() {
        return Room;
    }

    public boolean isPromoted() {
        switch (promoted) {
            case "False":
                return false;
            case "True":
                return true;
        }
        return false;
    }

}
