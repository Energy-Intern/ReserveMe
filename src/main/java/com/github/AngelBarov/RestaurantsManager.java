package com.github.AngelBarov;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import org.apache.commons.csv.*;

public class RestaurantsManager {
    private static final String COLUMN_SEPARATOR = ",";
    private static final String FILE_PATH = "listOfRestaurants.csv";

    public void update(HashSet restaurants){
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

    public HashSet<Restaurant> load(){
        HashSet<Restaurant> restaurants = new HashSet<Restaurant>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));){
            String line = br.readLine();
            line = br.readLine();

            while(line!=null){
                List<String> values = Arrays.asList(line.split(COLUMN_SEPARATOR));

                if(Boolean.parseBoolean(values.get(2))) {
                    restaurants.add(new Restaurant(
                            values.get(0),
                            values.get(1),
                            values.get(3),
                            Integer.parseInt(values.get(4)),
                            Boolean.parseBoolean(values.get(5)),
                            Boolean.parseBoolean(values.get(6)),
                            Double.parseDouble(values.get(7)),
                            Double.parseDouble(values.get(8)),
                            UUID.fromString(values.get(9))
                    ));
                }
                else {
                    restaurants.add(new Restaurant(
                            values.get(0),
                            values.get(1),
                            null,
                            Integer.parseInt(values.get(4)),
                            Boolean.parseBoolean(values.get(5)),
                            Boolean.parseBoolean(values.get(6)),
                            Double.parseDouble(values.get(7)),
                            Double.parseDouble(values.get(8)),
                            UUID.fromString(values.get(9))
                    ));
                }
                line = br.readLine();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return restaurants;
    }

    public void delete(String s) throws IndexOutOfBoundsException{
        HashSet<Restaurant> restaurants = load();

        restaurants.removeIf(i -> i.getUuid().toString().equals(s) || i.getName().equals(s));

        save(restaurants);
    }

    public void save(HashSet<Restaurant> restaurants){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH), "UTF-8"))){
            Stream<Restaurant> stream = restaurants.stream();

            bufferedWriter.write("Name, Address, Has Telephone Number?, Telephone Number, Places, Outside sitting, Lunch Menu, Longtitute, Latitude, UUId");
            bufferedWriter.newLine();

            stream.forEach(i->{
                    try{
                        bufferedWriter.write(RestaurantToString(i));
                        bufferedWriter.newLine();
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
