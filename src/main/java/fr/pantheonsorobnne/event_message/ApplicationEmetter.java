package fr.pantheonsorobnne.event_message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Message;
import jakarta.jms.Session;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ApplicationEmetter implements Runnable {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Inject
    ConnectionFactory connectionFactory;

    @ConfigProperty(name = "quarkus.artemis.username")
    String username;

    void onStart(@Observes StartupEvent ev) {
        run();
    }

    @Override
    public void run() {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            Message event = null;
            try {
                event = context.createTextMessage(toJson(new EventNewPrice(8)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            context.createProducer().send(context.createTopic("M1.prices-" + username), event);
        }
    }

    private static String toJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
