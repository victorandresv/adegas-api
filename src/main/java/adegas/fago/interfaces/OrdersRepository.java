package adegas.fago.interfaces;

import adegas.fago.models.OrderCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrdersRepository extends MongoRepository<OrderCollection, String> {
    @Query(value = "{'companyId': ?0, 'jailId': ?1}")
    List<OrderCollection> findByCompanyIdJailId(String cid, String jailId);

}
