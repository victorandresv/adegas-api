package adegas.fago.interfaces;

import adegas.fago.models.UserCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserCollection, String> {

}
