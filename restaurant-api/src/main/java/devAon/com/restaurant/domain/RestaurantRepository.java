package devAon.com.restaurant.domain;

import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> findAll();

    Restaurant finById(Long id);

    List<MenuItem> findAllByRetaurantId(Long id);
}
