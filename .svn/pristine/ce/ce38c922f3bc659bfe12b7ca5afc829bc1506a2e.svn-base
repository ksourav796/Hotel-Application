package com.hyva.hotel.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Configurator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native",strategy ="native")
    private Long id;
    private String configuratorName;
    private String description;
    private String hoursBased;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfiguratorName() {
        return configuratorName;
    }

    public void setConfiguratorName(String configuratorName) {
        this.configuratorName = configuratorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHoursBased() {
        return hoursBased;
    }

    public void setHoursBased(String hoursBased) {
        this.hoursBased = hoursBased;
    }
}
