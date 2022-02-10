package com.example;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.hibernate.reactive.mutiny.Mutiny;

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
    @ReactiveTransactional
    public Uni<Event> fireForget(Event event) {
        return event.persist();
    }

    @Incoming("event-uni")
    @ReactiveTransactional
    public Uni<Event> onMessage(Event event) {
        return event.persist();
    }
}
