package adegas.fago.interfaces;

import adegas.fago.models.PriceCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PricesRepository extends MongoRepository<PriceCollection, String> {
    @Query(value = "{'companyId': ?0}")
    List<PriceCollection> findByCompanyId(String companyId);
}
