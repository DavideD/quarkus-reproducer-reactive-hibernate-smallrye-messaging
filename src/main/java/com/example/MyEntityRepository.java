package com.example;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Marian at 31.01.2022
 */
@ApplicationScoped
public class MyEntityRepository implements PanacheRepository<MyEntity> {
}
