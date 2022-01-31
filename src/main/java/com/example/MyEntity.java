package com.example;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Marian at 31.01.2022
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "my_entity")
public class MyEntity extends PanacheEntity {

    @Column(name = "name")
    private String name;

}
