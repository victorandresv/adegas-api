package adegas.fago.interfaces;

import adegas.fago.models.OrdersLocationCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrdersLocationRepository extends MongoRepository<OrdersLocationCollection, String> {
    @Query(value = "{'orderId': ?0}")
    List<OrdersLocationCollection> findLocationsByOrderId(String orderId);
}
