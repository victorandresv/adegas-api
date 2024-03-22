package adegas.fago.interfaces;

import adegas.fago.models.JailCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface JailRepository extends MongoRepository<JailCollection, String> {
    @Query(value = "{'companyId': ?0}", sort = "{'name': 1}}")
    List<JailCollection> findByCompanyId(String companyId);
}
