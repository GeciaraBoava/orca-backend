package com.geciara.orcamento.model.entitys.registerDetails;

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

    @Column(unique = true, nullable = false, length = 50)
    private String description;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;
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
