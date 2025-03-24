package tr.com.aselsankadir.casestudy.domain.common;

import java.util.Collection;

public interface IDomainEventPublisher {
    void publish(DomainEvent event);

    default void publishAll(Collection<? extends DomainEvent> events) {
        events.forEach(this::publish);
    }
}
