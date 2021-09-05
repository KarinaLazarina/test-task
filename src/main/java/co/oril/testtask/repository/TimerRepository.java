package co.oril.testtask.repository;

import co.oril.testtask.entity.Cryptocurrency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TimerRepository extends MongoRepository<Cryptocurrency, String> {
    Page<Cryptocurrency> findByCurr1(String curr1, Pageable pageable);

    Cryptocurrency findFirstByCurr1OrderByLprice(String name);
    Cryptocurrency findFirstByCurr1OrderByLpriceDesc(String name);
}
