package adegas.fago.interfaces;

import adegas.fago.models.PriceCollection;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PricesRepository extends MongoRepository<PriceCollection, String> {
    @Query(value = "{'companyId': ?0}", sort = "{'timestamp': -1}}")
    List<PriceCollection> findByCompanyId(String companyId);

    @Aggregation(pipeline = {
            "{ '$match': { 'companyId' : ?0 } }",
            "{ '$sort' : { 'timestamp' : -1 } }",
            "{ '$limit' : 1 }"
    })
    PriceCollection findCurrentByCompanyId(String companyId);
}
