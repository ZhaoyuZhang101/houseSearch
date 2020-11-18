package Methods;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import Tokenizer.Token;

import static Tokenizer.Parsing.getTokens;

public class search {
    public static String getCommon(String wordsInput, String sentenceInput) {
        String result = "";
        int begin = 0;
        for (int i=0;i<wordsInput.length();i++) {
            for (int j=i+1;j<wordsInput.length();j++) {
                String sub = wordsInput.substring(i,j);
                int length = sub.length();
                if (sentenceInput.contains(sub)&&length>begin) {
                    begin = length;
                    result = sub;
                }
            }
        }
        return result;
    }

    public static int toPriceInt(String input) {
        StringBuilder i = new StringBuilder();
        int a = input.indexOf("$");
        if (a != -1) {
            input = input.substring(a);
        }
        input = input.replace("$","");
        input = input.replace(",","");
        input = input.trim();
        for (Character j : input.toCharArray()) {
            if (Character.isDigit(j)) {
                i.append(j);
            } else {
                break;
            }
        }
        if(i.length()==0) {
            return 0;
        } else {
            return Integer.parseInt(i.toString());
        }
    }

    public static ArrayList<House> addToView(String string, HashMap<String, House> housesInfo, InputStreamReader reader) {
        string = string.replace("$","~");
        string = string.replace("than ","");
        string = string.replace("in ","");
        string = string.replace("a ","");
        string = string.replace(",","");
        ArrayList<House> allHouse = new ArrayList<>();
        ArrayList<ArrayList<Token>> tokens = getTokens(string,reader);
        for (int l = 1; l <= housesInfo.size(); l++) {
            allHouse.add(housesInfo.get(String.valueOf(l)));
        }

        for (int i = 0; i < 4; i++) {
            if (tokens.get(i).size() != 0) {
                switch (i) {
                    case 0:
                        for (Token token : tokens.get(i)) {
                            String[] words = token.token().split("\\|");
                            android.util.Log.e("words", Arrays.toString(words));
                            if (words[0].equals("greater") | words[0].equals(">")) {
                                for (int j = 1; j <= housesInfo.size(); j++) {
                                    if (toPriceInt(Objects.requireNonNull(housesInfo.get(String.valueOf(j))).getPrice()) < Integer.parseInt(words[1])) {
                                        allHouse.remove(housesInfo.get(String.valueOf(j)));
                                    }
                                }
                            } else if (words[0].equals("less") | words[0].equals("<")) {
                                for (int j1 = 1; j1 <= housesInfo.size(); j1++) {
                                    android.util.Log.e("less",String.valueOf(toPriceInt(Objects.requireNonNull(housesInfo.get(String.valueOf(j1))).getPrice())));
                                    if (toPriceInt(Objects.requireNonNull(housesInfo.get(String.valueOf(j1))).getPrice()) > Integer.parseInt(words[1])) {
                                        allHouse.remove(housesInfo.get(String.valueOf(j1)));
                                    }
                                }
                            } else if (words[0].equals("=")) {
                                for (int j2 = 1; j2 <= housesInfo.size(); j2++) {
                                    if (toPriceInt(Objects.requireNonNull(housesInfo.get(String.valueOf(j2))).getPrice()) != Integer.parseInt(words[1])) {
                                        allHouse.remove(housesInfo.get(String.valueOf(j2)));
                                    }
                                }
                            }
                        }
                        break;
                    case 1:
                        for (Token token : tokens.get(i)) {
                            for (int j = 1; j <= housesInfo.size(); j++) {
                                if (!Objects.requireNonNull(housesInfo.get(String.valueOf(j))).getType().toLowerCase().equals(token.token())) {
                                    allHouse.remove(housesInfo.get(String.valueOf(j)));
                                }
                            }
                        }
                        break;
                    case 2:
                        for (Token token : tokens.get(i)) {
                            String[] words = token.token().split("\\|");
                            android.util.Log.e("room", Arrays.toString(words));
                            if (words[0].equals("greater") | words[0].equals(">")) {
                                for (int j = 1; j <= housesInfo.size(); j++) {
                                    switch (words[2]) {
                                        case "bedroom":
                                            if (Objects.requireNonNull(housesInfo.get(String.valueOf(j))).getRoom().getBedroom() < Integer.parseInt(words[1])) {
                                                allHouse.remove(housesInfo.get(String.valueOf(j)));
                                            }
                                            break;
                                        case "bathroom":
                                            if (Objects.requireNonNull(housesInfo.get(String.valueOf(j))).getRoom().getBathroom() < Integer.parseInt(words[1])) {
                                                allHouse.remove(housesInfo.get(String.valueOf(j)));
                                            }
                                            break;
                                        case "garage":
                                            if (Objects.requireNonNull(housesInfo.get(String.valueOf(j))).getRoom().getparkingspace() < Integer.parseInt(words[1])) {
                                                allHouse.remove((housesInfo.get(String.valueOf(j))));
                                            }
                                            break;
                                    }
                                }
                            }
                            if (words[0].equals("less") | words[0].equals("<")) {
                                for (int j = 1; j <= housesInfo.size(); j++) {
                                    switch (words[2]) {
                                        case "bedroom":
                                            if (Objects.requireNonNull(housesInfo.get(String.valueOf(j))).getRoom().getBedroom() > Integer.parseInt(words[1])) {
                                                allHouse.remove(housesInfo.get(String.valueOf(j)));
                                            }
                                            break;
                                        case "bathroom":
                                            if (Objects.requireNonNull(housesInfo.get(String.valueOf(j))).getRoom().getBathroom() > Integer.parseInt(words[1])) {
                                                allHouse.remove(housesInfo.get(String.valueOf(j)));
                                            }
                                            break;
                                        case "garage":
                                            if (Objects.requireNonNull(housesInfo.get(String.valueOf(j))).getRoom().getparkingspace() > Integer.parseInt(words[2])) {
                                                allHouse.remove(housesInfo.get(String.valueOf(j)));
                                            }
                                            break;
                                    }
                                }
                            }
                            if (words[0].equals("=")) {
                                for (int j = 1; j <= housesInfo.size(); j++) {
                                    switch (words[2]) {
                                        case "bedroom":
                                            if (Objects.requireNonNull(housesInfo.get(String.valueOf(j))).getRoom().getBedroom() != Integer.parseInt(words[1])) {
                                                allHouse.remove(housesInfo.get(String.valueOf(j)));
                                            }
                                            break;
                                        case "bathroom":
                                            if (Objects.requireNonNull(housesInfo.get(String.valueOf(j))).getRoom().getBathroom() != Integer.parseInt(words[1])) {
                                                allHouse.remove(housesInfo.get(String.valueOf(j)));
                                            }
                                            break;
                                        case "garage":
                                            if (Objects.requireNonNull(housesInfo.get(String.valueOf(j))).getRoom().getparkingspace() != Integer.parseInt(words[1])) {
                                                allHouse.remove(housesInfo.get(String.valueOf(j)));
                                            }
                                            break;
                                    }
                                }
                            }
                        }
                        break;
                    case 3:
                        for (Token token : tokens.get(i)) {
                            for (int j = 1; j <= housesInfo.size(); j++) {
                                String location = (Objects.requireNonNull(housesInfo.get(String.valueOf(j))).getLocation().getCity().toLowerCase() + Objects.requireNonNull(housesInfo.get(String.valueOf(j))).getLocation().getStreet().toLowerCase());
                                String common = getCommon(token.token(),location);
                                if (token.token().length()!=0) {
                                    if (common.length() < 2) {
                                        allHouse.remove(housesInfo.get(String.valueOf(j)));
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        }
        return allHouse;
    }
}
