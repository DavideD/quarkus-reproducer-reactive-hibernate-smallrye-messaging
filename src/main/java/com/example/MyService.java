package com.example;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Marian at 31.01.2022
 */
@Slf4j
@ApplicationScoped
public class MyService {


    @ReactiveTransactional
    @Incoming(Event.FIRE_AND_FORGET)
    public Uni<PanacheEntityBase> fireForget(Event event) {
        return event.persist().onItem().invoke(panacheEntityBase -> log.info("persist"));
    }

    @Incoming("event-uni")
    public void onMessage(Event event) {
        Panache.withTransaction(event::persist)
                .onFailure().invoke(throwable -> log.error("Failed to store event", throwable))
                .subscribe().with(panacheEntityBase -> {
                });
    }

}
