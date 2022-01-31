package com.example;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.function.Consumer;

@Slf4j
@Path("/hello")
public class ExampleResource {

    @Inject
    @Channel(Event.FIRE_AND_FORGET)
    Emitter<Event> eventEmitter;

    @Inject
    MyEntityRepository repository;

    @GET
    @Path("invoke")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> invokeFetchEntityAndGreetAndLog(@QueryParam("id") Long id) {
        return findItemById(id)
                .onItem().invoke(entity -> eventEmitter.send(event(entity)))
                .onItem().transform(entity -> Response.accepted(entity).build());
    }

    @GET
    @Path("call")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> callFetchEntityAndGreetAndLog(@QueryParam("id") Long id) {
        return findItemById(id)
                .onItem().call(entity -> Uni.createFrom().completionStage(eventEmitter.send(event(entity))))
                .onItem().transform(entity -> Response.accepted(entity).build());
    }

    private Uni<MyEntity> findItemById(Long id) {
        //We don't do a real lookup by id here for the demo
        return repository.findAll().firstResult()
                .onItem().ifNotNull().invoke(myEntity -> log.info("entity found " + myEntity.id))
                .onItem().ifNull().failWith(new Exception("No item found for id " + id));
    }

    private Event event(MyEntity entity) {
        return Event.builder().name("an event").myEntity(entity).build();
    }
}