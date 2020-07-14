

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class ListManager {
    private static final String COLUMN_SEPARATOR = ",";
    private static final String FILE_PATH = "~/listOfRestaurants.csv";

    public void update(HashSet restaurants){
        try(FileWriter fw = new FileWriter(FILE_PATH, false);){
            save(restaurants);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public Restaurant findById(UUID uuid){
        Set<Restaurant> restaurants = load();

        for(Restaurant restaurant:restaurants){
            if(restaurant.getUuid().equals(uuid)){
                return restaurant;
            }
        }

        return null;
    }

    public Restaurant findByName(String name){
        Set<Restaurant> restaurants = load();

        for(Restaurant restaurant:restaurants){
            if(restaurant.getName().equals(name))return restaurant;
        }

        return null;
    }

    public HashSet<Restaurant> load(){
        HashSet<Restaurant> restaurants = new HashSet<Restaurant>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));){
            String line = br.readLine();

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
                            Integer.parseInt(values.get(3)),
                            Boolean.parseBoolean(values.get(4)),
                            Boolean.parseBoolean(values.get(5)),
                            Double.parseDouble(values.get(6)),
                            Double.parseDouble(values.get(7)),
                            UUID.fromString(values.get(8))));
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

    public void deleteId(UUID uuid) throws IndexOutOfBoundsException{
        HashSet<Restaurant> restaurants = load();

        restaurants.removeIf(i->i.getUuid().equals(uuid));

        save(restaurants);
    }

    public void save(HashSet<Restaurant> restaurants){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH), "UTF-8"));){
            Stream<Restaurant> stream = restaurants.stream();
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
            sb.append(restaurant.getTelNumber().trim());
            sb.append(COLUMN_SEPARATOR);
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
