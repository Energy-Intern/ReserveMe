import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class
RestaurantTest {

    @Test
    @Order(1)
    public void TestSaving() {

        HashSet<Restaurant> restaurants = new HashSet<Restaurant>();

        restaurants.add(new Restaurant("r1", "a1", "0885674563", 13, true, true, 23.239, 45.2837,  null));
        restaurants.add(new Restaurant("r2", "a2", null, 13, true, true, 23.239, 45.2837,  null));

        RestaurantsManager restaurantsManager = new RestaurantsManager();
        restaurantsManager.save(restaurants);

        try{
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream("listOfRestaurants.csv"), "UTF-8"));

            int savedR = 0;

            while(bf.readLine().toString()!=null){
                savedR++;
            }

            assertEquals(restaurants.size(), savedR);
        }catch (Exception e){

        }
    }

    @Test
    @Order(2)
    public void TestLoading() {

        int howMuch=0;
        HashSet<Restaurant> restaurants = new HashSet<Restaurant>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("listOfRestaurants.csv"));
            String line = br.readLine();

            while(line!=null) {

                howMuch++;

                List<String> values = Arrays.asList(line.split(","));

                if (Boolean.parseBoolean(values.get(2))){
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
                } else {
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

        assertEquals(howMuch, 2);
    }

    @Test
    @Order(3)
    public void TestFind(){
        RestaurantsManager restaurantsManager = new RestaurantsManager();

        HashSet<Restaurant> restaurants = restaurantsManager.load();

        Restaurant restaurant = restaurantsManager.find(restaurants, "r1");

        assertEquals(restaurant.getName(), "r1");
    }

    @Test
    @Order(4)
    public void TestDelete () {

        RestaurantsManager restaurantsManager = new RestaurantsManager();

        HashSet<Restaurant> restaurants = restaurantsManager.load();

        int size = restaurants.size();

        restaurants.forEach(i -> {
            if(i.getName().equals("r1")){
                restaurantsManager.deleteId(i.getUuid());
            }
        });

        HashSet<Restaurant> restaurants1 = restaurantsManager.load();

        assertEquals(size-1, restaurants1.size());

    }


    /*@Test
    @Order(4)
    public void TestFindById(){
        Set<Restaurant> restaurants = new ListManager().load();

        for(Restaurant restaurant:restaurants){
            Restaurant restaurant1 = new ListManager().findById(restaurant.getUuid());

            assertEquals(restaurant.getName(), restaurant1.getName());
        }
    }

    @Test
    @Order(4)
    public void TestFindByName(){
        HashSet<Restaurant> restaurants = new ListManager().load();

        Restaurant restaurant = new ListManager().findByName("r1");

        for(Restaurant restaurant1 : restaurants){
            assertEquals(restaurant.getTelNumber(), restaurant1.getTelNumber());
        }
    }*/

    /*@Test
    @Order(5)
    public void TestUpdate(){
        HashSet<Restaurant> restaurants = new HashSet<Restaurant>();

        restaurants.add(new Restaurant("n1", "b1", null, 13, true, true, 23.239, 45.2837, null));
        restaurants.add(new Restaurant("djfndj", "ndjfn", null, 35, true, true, 3.239, 45.2837, null));

        try(FileWriter fw = new FileWriter("listOfRestaurants.csv", false);){
            new RestaurantsManager().save(restaurants);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }*/

}