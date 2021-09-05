package co.oril.testtask.service;

import co.oril.testtask.repository.TimerRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVService {
    @Autowired
    public CryptocurrencyService cryptocurrencyService;

    public ByteArrayInputStream load(){
        List<String> cryptocurrencyNamesList = new ArrayList<>();
        cryptocurrencyNamesList.add("BTC");
        cryptocurrencyNamesList.add("XRP");
        cryptocurrencyNamesList.add("ETH");

        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (String name: cryptocurrencyNamesList) {
                String data = name +
                        " minPrice:" + cryptocurrencyService.getMinPrice(name) +
                        " maxPrice:" + cryptocurrencyService.getMaxPrice(name);

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
