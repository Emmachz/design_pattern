package fr.pantheonsorobnne.message_router;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ApplicationRouter extends RouteBuilder {

    @ConfigProperty(name = "quarkus.artemis.username")
    String username;

    @Inject
    EventMessageProcessor eventMessageProcessor;



    @Override
    public void configure() {
        from("sjms2:queue:M1.prices-" + username)
                .choice()
                    .when(body().contains("important"))
                        .to("direct:importantMessages" + username)
                    .when(body().contains("urgent"))
                        .to("direct:urgentMessages" + username)
                    .otherwise()
                        .to("direct:otherMessages" + username);
    }
}
