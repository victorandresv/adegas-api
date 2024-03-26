package adegas.fago.interfaces;

import adegas.fago.models.UserCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<UserCollection, String> {
    @Query(value = "{'companyId': ?0, 'phone': ?1}")
    UserCollection findByCompanyIdAndPhone(String companyId, String phone);

    @Query(value = "{'_id': ?0}")
    UserCollection findOneById(String oid);

    @Query(value = "{'companyId': ?0}")
    List<UserCollection> findByCompanyId(String cid);
}
