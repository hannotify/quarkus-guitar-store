package com.github.hannotify;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Guitar extends PanacheEntity {
    public String make;
    public String model;
}
