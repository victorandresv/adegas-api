package adegas.fago.interfaces;

import adegas.fago.models.KeysCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface KeysRepository extends MongoRepository<KeysCollection, String> {
    @Query(value = "{'userId': ?0}")
    KeysCollection findOneByUserId(String oid);
}
