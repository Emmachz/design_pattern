package fr.pantheonsorobnne.event_message;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@ApplicationScoped
public class EventMessageProcessor implements Processor {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void process(Exchange exchange) throws Exception {
        String message = exchange.getMessage().getBody(String.class);
        EventNewPrice eventNewPrice = OBJECT_MAPPER.readValue(message, EventNewPrice.class);
        System.out.print("Event received: " + eventNewPrice);
    }
}

