package fr.pantheonsorobnne.message_router;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ApplicationObserver2 extends RouteBuilder {

    @ConfigProperty(name = "quarkus.artemis.username")
    String username;
    @Inject
    EventMessageProcessor eventMessageProcessor;

    @Override
    public void configure() throws Exception {
        from("direct:otherMessages" + username).log("${body}").process(eventMessageProcessor);
    }
}