package devAon.com.restaurant.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    List<Restaurant> findAll();

    //Optional null로 생기는 문제 해결해줌.
    Optional<Restaurant> findById(Long id);

    Restaurant save(Restaurant restaurant);
}
