package adegas.fago.interfaces;

import adegas.fago.models.UserLocationCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserLocationRepository extends MongoRepository<UserLocationCollection, String> {
    @Query(value = "{'userId': ?0, 'timestamp': ?1}")
    UserLocationCollection findOneByIdAndTs(String userId, long timestamp);
}
