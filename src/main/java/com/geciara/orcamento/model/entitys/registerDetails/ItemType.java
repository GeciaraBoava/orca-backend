package com.geciara.orcamento.model.entitys.registerDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items_types")
public class ItemType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_type_seq")
    @SequenceGenerator(name = "item_type_seq", sequenceName = "item_type_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    private String name;

    @Column(unique = true, nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean active;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        registeredAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public ItemType(String descricao) {
        this.description = descricao;
    }
}
