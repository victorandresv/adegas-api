package adegas.fago.interfaces;

import adegas.fago.models.LocationCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<LocationCollection, String> {
}
