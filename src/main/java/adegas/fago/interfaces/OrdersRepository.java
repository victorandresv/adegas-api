package adegas.fago.interfaces;

import adegas.fago.models.OrderCollection;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrdersRepository extends MongoRepository<OrderCollection, String> {
    @Query(value = "{'companyId': ?0, 'jailId': ?1}")
    @Aggregation(pipeline = {
            "{ '$match': { 'companyId' : ?0, 'jailId': ?1, 'createdAt': {'$gte': ?2, '$lte': ?3} } }",
            "{ '$sort' : { 'createdAt' : -1 } }"
    })
    List<OrderCollection> findByCompanyIdJailId(String cid, String jailId, long dtStart, long dtEnd);

    @Query(value = "{'_id': ?0}")
    OrderCollection findOneById(String id);

}
