package com.example;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * Created by Marian at 31.01.2022
 */
@Slf4j
public class Reactive {

    //Mutiny won't execute without a subscriber
    public static Consumer<? super PanacheEntityBase> dummySubscriber() {
        return e -> {
        };
    }

}
