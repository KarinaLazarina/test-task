package co.oril.testtask.service;

import co.oril.testtask.entity.Cryptocurrency;
import co.oril.testtask.repository.TimerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.TimerTask;

@Service
@RequiredArgsConstructor
public class TimerService extends TimerTask {
    private static final String uri = "https://cex.io/api/last_price/";

    @Autowired
    private TimerRepository timerRepository;

    @SneakyThrows
    @Override
    public void run() {
        updateData("BTC", "USD");
        updateData("ETH", "USD");
        updateData("XRP", "USD");
    }

    @Transactional
    public Cryptocurrency updateData(String curr1,
                                     String curr2) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri + curr1 + "/" + curr2, String.class);

        Cryptocurrency value = new ObjectMapper().readValue(result, Cryptocurrency.class);
        value.setCreatedAt(new Date());
//        if (timerRepository.existsByCurr1(curr1)) {
//            value.setId(timerRepository.findByCurr1(curr1).getId());
//        }

        System.out.println("timer");
        return timerRepository.save(value);
    }
}
