package com.Senai.Filmes.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sala")
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome da sala é obrigatório")
    private String nome;

    @Min(value = 1L, message = "A sala deve ter pelo menos 1 assento") // É o padrão de números de assentos, o mínimo, no caso.
    private Integer totalAssentos;

    
}
