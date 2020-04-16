package devAon.com.restaurant.domain;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(Long id){
        super("could not find restaurant " + id);
    }
}
