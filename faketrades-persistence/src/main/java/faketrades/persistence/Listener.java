package faketrades.persistence;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;


@Component
@ImportResource({ "classpath*:listen_context.xml" })
public class Listener {

	@Autowired
	private List<Listen> listeners;

	ExecutorService service;

	public void startListening() {
		service = Executors.newFixedThreadPool(listeners.size());
		listeners.forEach(l -> service.submit(l));
	}



}
