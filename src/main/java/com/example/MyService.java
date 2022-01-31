package com.example;

import io.quarkus.hibernate.reactive.panache.Panache;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Marian at 31.01.2022
 */
@Slf4j
@ApplicationScoped
public class MyService {

    @Incoming(Event.FIRE_AND_FORGET)
    public void fireForget(Event event) {
        Panache.withTransaction(event::persist)
                .onFailure().invoke(throwable -> log.error("Failed to store event", throwable))
                .subscribe().with(panacheEntityBase -> {
                });
    }

    @Incoming("event-uni")
    public void onMessage(Event event) {
        Panache.withTransaction(event::persist)
                .onFailure().invoke(throwable -> log.error("Failed to store event", throwable))
                .subscribe().with(panacheEntityBase -> {
                });
    }

}
