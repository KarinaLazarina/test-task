package co.oril.testtask;

import co.oril.testtask.service.TimerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Timer;

@SpringBootApplication
public class TestTaskApplication {
    @Autowired
    TimerService timerService;

    public static void main(String[] args) {
        SpringApplication.run(TestTaskApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startTimer() {
        Timer timer = new Timer();
        timer.schedule(timerService, 0, 10000);
    }

}
