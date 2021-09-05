package co.oril.testtask.controller;

import co.oril.testtask.entity.Cryptocurrency;
import co.oril.testtask.service.CSVService;
import co.oril.testtask.service.CryptocurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cryptocurrencies")
public class CryptocurrencyController {
    private static List<String> valueList = Arrays.asList("BTC", "XRP", "ETH");

    @Autowired
    private CryptocurrencyService cryptocurrencyService;
    @Autowired
    private CSVService fileService;

    @SneakyThrows
    @GetMapping(value = "/minprice")
    public double getMinPrice(@RequestParam String name) {
        if (valueList.contains(name)) {
            throw new RuntimeException("Wrong request params");
        }
        double minPrice = cryptocurrencyService.getMinPrice(name);

        log.info("Find minPrice of {} cryptocurrency: {}", name, minPrice);
        return minPrice;
    }

    @SneakyThrows
    @GetMapping(value = "/maxprice")
    public double getMaxPrice(@RequestParam String name) {
        if (valueList.contains(name)) {
            throw new RuntimeException("Wrong request params");
        }
        double maxPrice = cryptocurrencyService.getMaxPrice(name);

        log.info("Find maxPrice of {} cryptocurrency: {}", name, maxPrice);
        return maxPrice;
    }

    @GetMapping
    public Page<Cryptocurrency> getListOfCryptocurrency(@RequestParam String name,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        if (page < 0 || size <= 0 || valueList.contains(name)) {
            throw new RuntimeException("Wrong request params");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("lprice"));

        log.info("Find list of {} cryptocurrency records", name);
        return cryptocurrencyService.getCryptocurrency(name, pageable);
    }


    @GetMapping(value = "/csv")
    public ResponseEntity<Resource> getFile() {
        String filename = "cryptocurrency.csv";
        InputStreamResource file = new InputStreamResource(fileService.load());

        log.info("Load csv report");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}

