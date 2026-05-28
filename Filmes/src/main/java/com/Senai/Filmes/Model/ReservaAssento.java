package com.Senai.Filmes.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reservas_assentos")
public class ReservaAssento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @JsonIgnore //Ele faz com que ao ser chamada, essa reservaassento, ele salve só o número de assento e ignora a reserva. Porque aí impede o loop. Isso tudo acontece porque uma tabela referencia a outra, isso SEMPRE para tabelas intermediárias. Pensando na REQUISIÇÃO.
    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reservas reserva;
}
