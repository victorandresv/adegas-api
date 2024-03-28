package adegas.fago.interfaces;

import adegas.fago.models.OrdersLocationCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersLocationRepository extends MongoRepository<OrdersLocationCollection, String> {
}
