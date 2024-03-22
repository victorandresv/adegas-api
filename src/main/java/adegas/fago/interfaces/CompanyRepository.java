package adegas.fago.interfaces;

import adegas.fago.models.CompanyCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<CompanyCollection, String> {
}
