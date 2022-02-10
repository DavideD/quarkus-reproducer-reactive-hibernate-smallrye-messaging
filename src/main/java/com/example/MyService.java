package com.example;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.hibernate.reactive.mutiny.Mutiny;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

/**
 * Created by Marian at 31.01.2022
 */
@Slf4j
@ApplicationScoped
public class MyService {

    @Inject
    Mutiny.SessionFactory factory;

    @Incoming(Event.FIRE_AND_FORGET)
    public Uni<?> fireForget(Event event) {
        return persist( event );
    }

    @Incoming("event-uni")
    public Uni<?> onMessage(Event event) {
        return persist( event );
    }

    private Uni<PanacheEntityBase> persist(Event event) {
        return Panache.withTransaction( () -> event
                .persist())
                .onItemOrFailure()
                .invoke( (r, e) -> log.info( "Persisted: " + event + ", Error: " + e ) );
    }
}
