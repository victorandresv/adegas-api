package adegas.fago.interfaces;

import adegas.fago.models.OrdersLocationCollection;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrdersLocationRepository extends MongoRepository<OrdersLocationCollection, String> {
    @Aggregation(pipeline = {
            "{ '$match': { 'orderId' : ?0 } }",
            "{ '$sort' : { 'timeStamp' : 1 } }",
            "{ '$limit' : 20 }"
    })
    List<OrdersLocationCollection> findLocationsByOrderId(String orderId);
}
