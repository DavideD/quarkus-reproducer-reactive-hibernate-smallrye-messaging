package com.example;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.reactive.mutiny.Mutiny;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;

@Slf4j
@Path("/hello")
public class ExampleResource {

    @Inject
    @Channel(Event.FIRE_AND_FORGET)
    MutinyEmitter<Event> eventEmitter;

    @Inject
    Mutiny.SessionFactory factory;

    @GET
    @Path("invoke")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> invokeFetchEntityAndGreetAndLog(@QueryParam("id") Long id) {
        return findItemById(id)
                .map(this::event)
                .call(eventEmitter::send)
                .map(Response::accepted)
                .map(Response.ResponseBuilder::build);
    }

    @GET
    @Path("call")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> callFetchEntityAndGreetAndLog(@QueryParam("id") Long id) {
        return findItemById(id)
                .map(this::event)
                .call(eventEmitter::send)
                .map(Response::accepted)
                .map(Response.ResponseBuilder::build);
    }

    private Uni<MyEntity> findItemById(Long id) {
        //We don't do a real lookup by id here for the demo
        return factory.withSession( session -> session.createQuery( "from MyEntity", MyEntity.class ).getSingleResult() )
                .onItem().ifNotNull().invoke(myEntity -> log.info("entity found " + myEntity.id))
                .onItem().ifNull().failWith(new Exception("No item found for id " + id));
    }

    private Event event(MyEntity entity) {
        return Event.builder().name("an event").myEntity(entity).build();
    }
}