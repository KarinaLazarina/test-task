package co.oril.testtask;

import co.oril.testtask.service.TimerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;

import java.util.Timer;

@SpringBootTest
class TestTaskApplicationTests {

	@Test
	void contextLoads() {
	}

}
