package tr.com.aselsankadir.casestudy.infrastructure.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import tr.com.aselsankadir.casestudy.domain.common.DomainEvent;
import tr.com.aselsankadir.casestudy.domain.common.IDomainEventPublisher;

@Component
public class AselDomainEventPublisher implements IDomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public AselDomainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(DomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
