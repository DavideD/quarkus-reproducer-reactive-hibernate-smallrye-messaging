package com.example;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by Marian at 31.01.2022
 */

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event extends PanacheEntity {

    public static final String FIRE_AND_FORGET = "fire-and-forget";

    @Column
    private String name;

    @ManyToOne
    private MyEntity myEntity;
}
