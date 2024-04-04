package adegas.fago.interfaces;

import adegas.fago.models.UserLocationCollection;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserLocationRepository extends MongoRepository<UserLocationCollection, String> {
    @Query(value = "{'userId': ?0, 'timestamp': ?1}")
    UserLocationCollection findOneByIdAndTs(String userId, long timestamp);

    @Aggregation(pipeline = {
            "{ '$match': { 'userId' : ?0 } }",
            "{ '$sort' : { 'timestamp' : 1 } }",
            "{ '$limit' : ?1 }"
    })
    List<UserLocationCollection> findUserLocations(String userId, int limit);
}
