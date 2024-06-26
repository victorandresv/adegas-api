package adegas.fago.interfaces;

import adegas.fago.models.OrdersLocationCollection;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrdersLocationRepository extends MongoRepository<OrdersLocationCollection, String> {
    @Aggregation(pipeline = {
            "{ '$match': { 'orderId' : ?0 } }",
            "{ '$sort' : { 'timeStamp' : 1 } }",
            "{ '$limit' : 5000 }"
    })
    List<OrdersLocationCollection> findLocationsByOrderId(String orderId);
}
