package co.oril.testtask.service;

import co.oril.testtask.entity.Cryptocurrency;
import co.oril.testtask.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CryptocurrencyService {
    @Autowired
    private TimerRepository timerRepository;

    public double getMinPrice(String name){
        Cryptocurrency cryptocurrency = timerRepository.findFirstByCurr1OrderByLprice(name);
        return cryptocurrency.getLprice();
    }

    public double getMaxPrice(String name) {
        Cryptocurrency cryptocurrency = timerRepository.findFirstByCurr1OrderByLpriceDesc(name);
        return cryptocurrency.getLprice();
    }

    public Page<Cryptocurrency> getCryptocurrency(String name, Pageable pageable){
        return timerRepository.findByCurr1(name, pageable);
    }

}
