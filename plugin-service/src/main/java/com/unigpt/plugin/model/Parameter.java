package com.unigpt.plugin.model;

import com.unigpt.plugin.dto.ParameterDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "parameter")
public class Parameter {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 3000)
    private String description;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne()
    @JoinColumn(name = "tool_id", nullable = false)
    private Plugin plugin;

    public Parameter(ParameterDTO paramterDTO, Plugin plugin) {
        this.name = paramterDTO.getName();
        this.description = paramterDTO.getDescription();
        this.type = paramterDTO.getType();
        this.plugin = plugin;
    }

    public Parameter() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return Objects.equals(id, parameter.id); // Compare using a unique identifier like id
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
