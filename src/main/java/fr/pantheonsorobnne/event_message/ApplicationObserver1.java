package fr.pantheonsorobnne.event_message;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ApplicationObserver1 extends RouteBuilder {

    @ConfigProperty(name = "quarkus.artemis.username")
    String username;
    @Inject
    EventMessageProcessor eventMessageProcessor;

    @Override
    public void configure() {
        from("sjms2:topic:M1.prices-"+username).log("${body}").process(eventMessageProcessor);
    }
}