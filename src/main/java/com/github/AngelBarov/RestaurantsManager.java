package com.github.AngelBarov;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import org.apache.commons.csv.*;

public class RestaurantsManager {
    private static final String COLUMN_SEPARATOR = ",";
    private static final String FILE_PATH = "listOfRestaurants.csv";

    public void update(Collection<Restaurant> restaurants){
        try(FileWriter fw = new FileWriter(FILE_PATH, false);){
            save(restaurants);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Restaurant find(Collection<Restaurant> restaurants, String s){
        restaurants.removeIf(i -> !i.getUuid().toString().equals(s) && !i.getName().equals(s));

        try{
            return (Restaurant) restaurants.toArray()[0];
        }catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public Collection<Restaurant> load() {
        Collection<Restaurant> restaurants = new HashSet<Restaurant>() ;
        try {
            File file = new File(FILE_PATH);
            InputStreamReader input = new InputStreamReader(new FileInputStream(file));
            CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(input);
            csvParser.forEach(i->
                restaurants.add(new Restaurant(
                        i.get("Name"),
                        i.get("Address"),
                        i.get("Telephone Number"),
                        Integer.parseInt(i.get("Places")),
                        Boolean.parseBoolean(i.get("Outside sitting")),
                        Boolean.parseBoolean(i.get("Lunch Menu")),
                        Double.parseDouble(i.get("Longtitude")),
                        Double.parseDouble(i.get("Latitude")),
                        UUID.fromString(i.get("UUId"))
                ))
            );
        }catch (IOException e){
            e.printStackTrace();
        }

        return restaurants;
    }

    public void delete(String s) throws IndexOutOfBoundsException{
        Collection<Restaurant> restaurants = load();

        restaurants.removeIf(i -> i.getUuid().toString().equals(s) || i.getName().equals(s));

        save(restaurants);
    }

    public void save(Collection<Restaurant> restaurants){
        try(CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(FILE_PATH), CSVFormat.DEFAULT.withHeader("Name", "Address", "Has Telephone Number?", "Telephone Number", "Places", "Outside sitting", "Lunch Menu", "Longtitude", "Latitude", "UUId"))){

            restaurants.forEach(i->{
                    try{
                        csvPrinter.printRecord(RestaurantToString(i).split(COLUMN_SEPARATOR));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String RestaurantToString(Restaurant restaurant){
        StringBuffer sb = new StringBuffer();

        sb.append(restaurant.getName().trim());
        sb.append(COLUMN_SEPARATOR);

        sb.append(restaurant.getAddress().trim());
        sb.append(COLUMN_SEPARATOR);

        sb.append(restaurant.isNumber());
        sb.append(COLUMN_SEPARATOR);

        if(restaurant.isNumber()){
            sb.append(restaurant.getTelephoneNumber().trim());
            sb.append(COLUMN_SEPARATOR);
        }else{
            sb.append(" ,");
        }

        sb.append(restaurant.getPlaces());
        sb.append(COLUMN_SEPARATOR);

        sb.append(restaurant.isOutside());
        sb.append(COLUMN_SEPARATOR);

        sb.append(restaurant.isLunchMenu());
        sb.append(COLUMN_SEPARATOR);

        sb.append(restaurant.getLongtitude());
        sb.append(COLUMN_SEPARATOR);

        sb.append(restaurant.getLatitude());
        sb.append(COLUMN_SEPARATOR);

        sb.append(restaurant.getUuid().toString());

        return sb.toString();
    }

}
