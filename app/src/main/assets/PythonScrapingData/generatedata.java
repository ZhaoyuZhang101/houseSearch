import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class generatedata {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        HashMap<String, House> Houses = new HashMap<>();
        Random random = new Random();

        for (int i = 0; i < 1000; i++){
            int cityNo = random.nextInt(3);
            String type = null;
            String price = String.valueOf((random.nextInt(9)+1)*100000 + (random.nextInt(9)+1)*10000+ (random.nextInt(9)+1)*1000);
            String area = String.valueOf((random.nextInt(350) + 50));
            String city = null;
            String district = null;
            String street = null;
            String No = String.valueOf(i+1);
            Location location;
            int districtNo = 0;
            Room room;

            switch (cityNo){
                case 0:
                    city = "Canberra";
                    districtNo = random.nextInt(3);
                    switch (districtNo){
                        case 0:
                            district = "Central";
                            break;
                        case 1:
                            district = "Belconnen";
                            break;
                        case 2:
                            district = "Gungahlin";
                            break;
                    }
                    break;
                case 1:
                    city = "Sydney";
                    districtNo = random.nextInt(5);
                    switch (districtNo){
                        case 0:
                            district = "Chatswood";
                            break;
                        case 1:
                            district = "Redfern";
                            break;
                        case 2:
                            district = "Burwood";
                            break;
                        case 3:
                            district = "Pyrmont";
                            break;
                        case 4:
                            district = "Hurstville";
                            break;
                    }
                    break;
                case 2:
                    city = "Melbourne";
                    districtNo = random.nextInt(5);
                    switch (districtNo){
                        case 0:
                            district = "Box Hill";
                            break;
                        case 1:
                            district = "Rowville";
                            break;
                        case 2:
                            district = "Doncaster";
                            break;
                        case 3:
                            district = "Vermont";
                            break;
                        case 4:
                            district = "Mitcham";
                            break;
                    }
                    break;
            }
            int streetNo = random.nextInt(5);
            switch (streetNo){
                case 0:
                    street = "StreetOne";
                    break;
                case 1:
                    street = "StreetTwo";
                    break;
                case 2:
                    street = "StreetThree";
                    break;
                case 3:
                    street = "StreetFour";
                    break;
                case 4:
                    street = "StreetFive";
                    break;
            }
            switch (random.nextInt(3)){
                case 0:
                    type = "flat";
                    break;
                case 1:
                    type = "house";
                    break;
                case 2:
                    type = "apartment";
            }

            location = new Location(city, district);
            room = new Room((random.nextInt(3) + 1), (random.nextInt(2) + 1), (random.nextInt(2)));

            House house = new House(No, type, price, location, room, "True");
            Houses.put(No, house);
        }

        HouseData HousesData = new HouseData();
        File file = new File("HousesData.xml");
        HousesData.Store(file);
//        Houses = HousesData.loadFromXMLFile(file);
//        System.out.println(Houses.get("1").getLocation().getCity());

    }
}
