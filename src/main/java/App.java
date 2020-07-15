import java.util.HashSet;

public class App {
    public static void main (String... args) {
        HashSet<Restaurant> restaurants = new HashSet<Restaurant>();


        restaurants.add(new Restaurant("r1", "a1", "0885674563", 13, true, true, 23.239, 45.2837,  null));
        restaurants.add(new Restaurant("r2", "a2", null, 13, true, true, 23.239, 45.2837,  null));

        new RestaurantsManager().save(restaurants);

    }
}
