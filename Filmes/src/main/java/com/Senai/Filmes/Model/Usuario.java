package com.Senai.Filmes.Model;

import com.Senai.Filmes.Model.Enums.Cargo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuarios")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Column(unique = true) // Porque não existe emails iguais
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    @NotNull(message = "O cargo é obrigatório")
    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    @CreationTimestamp //Salva tudo na hora atual, daquele momento
    private LocalDateTime criadoEm;
}
