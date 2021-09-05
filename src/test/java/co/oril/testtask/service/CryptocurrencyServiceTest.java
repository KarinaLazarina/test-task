package co.oril.testtask.service;

import co.oril.testtask.entity.Cryptocurrency;
import co.oril.testtask.repository.TimerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CryptocurrencyServiceTest {
    @InjectMocks
    private CryptocurrencyService cryptocurrencyService;
    @Mock
    private TimerRepository timerRepository;

    @Test
    void getMinPriceTest() {
        Cryptocurrency cryptocurrency1 = new Cryptocurrency("1", "BTC", "USD", 12999.9, new Date());
        Cryptocurrency cryptocurrency2 = new Cryptocurrency("2", "BTC", "USD", 12967.9, new Date());
        Cryptocurrency cryptocurrency3 = new Cryptocurrency("3", "BTC", "USD", 12990.9, new Date());

        when(timerRepository.findFirstByCurr1OrderByLprice("BTC")).thenReturn(cryptocurrency2);

        double minPrice = cryptocurrencyService.getMinPrice("BTC");
        assertThat(cryptocurrency2.getLprice(), equalTo(minPrice));
    }

    @Test
    void getMaxPriceTest() {
        Cryptocurrency cryptocurrency1 = new Cryptocurrency("1", "BTC", "USD", 12999.9, new Date());
        Cryptocurrency cryptocurrency2 = new Cryptocurrency("2", "BTC", "USD", 12967.9, new Date());
        Cryptocurrency cryptocurrency3 = new Cryptocurrency("3", "BTC", "USD", 12990.9, new Date());

        when(timerRepository.findFirstByCurr1OrderByLpriceDesc("BTC")).thenReturn(cryptocurrency1);

        double minPrice = cryptocurrencyService.getMaxPrice("BTC");
        assertThat(cryptocurrency1.getLprice(), equalTo(minPrice));
    }

    @Test
    void getCryptocurrencyTest() {
        Cryptocurrency cryptocurrency1 = new Cryptocurrency("1", "BTC", "USD", 12999.9, new Date());
        Cryptocurrency cryptocurrency2 = new Cryptocurrency("2", "BTC", "USD", 12967.9, new Date());
        Cryptocurrency cryptocurrency3 = new Cryptocurrency("3", "BTC", "USD", 12990.9, new Date());

        ArrayList<Cryptocurrency> arrayList = new ArrayList<>();
        arrayList.add(cryptocurrency2);
        arrayList.add(cryptocurrency3);

        Pageable pageRequest = PageRequest.of(0, 2, Sort.by("lprice"));

        when(timerRepository.findByCurr1("BTC", pageRequest))
                .thenReturn(new PageImpl<>(arrayList));

        Page<Cryptocurrency> cryptocurrencyPage = cryptocurrencyService.getCryptocurrency("BTC", pageRequest);

        assertThat(new PageImpl<>(arrayList), equalTo(cryptocurrencyPage));
    }

}
