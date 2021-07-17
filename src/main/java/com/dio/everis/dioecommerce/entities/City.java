package com.dio.everis.dioecommerce.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CITIES")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 3, unique = true)
    private String initials;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
}
