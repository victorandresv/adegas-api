package adegas.fago.interfaces;

import adegas.fago.models.StockCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface StockRepository extends MongoRepository<StockCollection, String> {
    @Query(value = "{'companyId': ?0}")
    List<StockCollection> findByCompanyId(String companyId);

    @Query(value = "{'companyId': ?0, 'jailId': ?1}")
    List<StockCollection> findByCompanyIdAndJailId(String companyId, String jailId);

    @Query(value = "{'companyId': ?0, 'jailId': ?1, 'state': ?2}")
    StockCollection findOneBy(String companyId, String jailId, String state);
}
