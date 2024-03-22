package adegas.fago.interfaces;

import adegas.fago.models.PriceCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PricesRepository extends MongoRepository<PriceCollection, String> {

}
