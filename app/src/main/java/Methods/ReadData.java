package Methods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ReadData {

    public static HashMap<String, House> loadFromXMLFile(InputStream inputStream) {

        HashMap<String, House> houseList = new HashMap<>();
        DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = dFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element root = document.getDocumentElement();
            NodeList houses = root.getElementsByTagName("id");
            android.util.Log.e("", houses.toString());
            for (int i = 0; i < houses.getLength(); i++) {
                Element houseInfo = (Element) houses.item(i);
                NodeList locationElemS = houseInfo.getElementsByTagName("location");
                String ID = houseInfo.getAttribute("ID");
                Element locationElem = (Element) locationElemS.item(0);
                String city = locationElem.getElementsByTagName("city").item(0).getTextContent();
                String street = locationElem.getElementsByTagName("street").item(0).getTextContent();
                Location location = new Location(city, street);
                String price = houseInfo.getElementsByTagName("price").item(0).getTextContent();
                String type = houseInfo.getElementsByTagName("type").item(0).getTextContent();
                Node roomElem = houseInfo.getElementsByTagName("rooms").item(0);
                NodeList roomsElem = roomElem.getChildNodes();
                int bedroom = 0;
                int bathroom = 0;
                int garage = 0;
                for (int j = 0; j < roomsElem.getLength(); j++) {
                    switch (roomsElem.item(j).getNodeName()) {
                        case "bedroom":
                            bedroom = Integer.parseInt(roomsElem.item(j).getTextContent());
                            break;
                        case "bathroom":
                            bathroom = Integer.parseInt(roomsElem.item(j).getTextContent());
                            break;
                        case "parkingspace":
                            garage = Integer.parseInt(roomsElem.item(j).getTextContent());
                            break;
                    }
                }
                Room room = new Room(bedroom, bathroom, garage);
                House house;
                if (houseInfo.hasAttribute("promoted")) {
                    String promoted = houseInfo.getElementsByTagName("promoted").item(0).getTextContent();
                    house = new House(ID, type, price, location, room, promoted);
                }else {
                    house = new House(ID, type, price, location, room);
                }
                houseList.put(house.ID, house);
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return houseList;
    }

    public static HashMap<String, House> loadFromBespokeFile(InputStream inputStream) throws IOException {

        HashMap<String, House> HouseMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line;
        Room room = null;
        while ((line = reader.readLine()) != null) {
            String tokens[] = line.split(";");
            String Street = tokens[1];
            String City = tokens[2];
            String promoted = null;
            Location location = new Location(City, Street);
            if (tokens[8] != null) {
                room = new Room(Integer.valueOf(tokens[5]), Integer.valueOf(tokens[6]), Integer.valueOf(tokens[7]));
                promoted = tokens[8];
            } else {
                room = new Room(Integer.valueOf(tokens[5]), Integer.valueOf(tokens[6]), 0);
                promoted = tokens[7];
            }
            House house = new House(tokens[0], tokens[4], tokens[3], location, room, promoted);
            HouseMap.put(tokens[0], house);

        }
        return HouseMap;
    }

    public static HashMap<String, House> loadFromJsonFile(InputStream inputStream) throws IOException {

        HashMap<String, House> HouseMap = new HashMap<>();

        ArrayList<String> Result = new ArrayList<>();


        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bf.readLine()) != null) {
                Result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray json = new JSONArray(Result);
            for (int i = 0; i < json.length(); i++) {
                JSONObject jb = json.getJSONObject(i);
                String ID = jb.getString("ID");
                String type = jb.getString("type");
                String price = jb.getString("price");
                String city = jb.getString("city");
                String street = jb.getString("street");
                int bedroom = jb.getInt("bedroom");
                int bathroom = jb.getInt("bathroom");
                int parkingspace = 0;
                String promoted = jb.getString("promoted");
                if (jb.has("parkingspace")) {
                    parkingspace = jb.getInt("parkingspace");
                }
                Location location = new Location(city, street);
                Room room = new Room(bedroom, bathroom, parkingspace);
                House house = new House(ID, type, price, location, room, promoted);
                HouseMap.put(ID, house);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return HouseMap;
    }
}
