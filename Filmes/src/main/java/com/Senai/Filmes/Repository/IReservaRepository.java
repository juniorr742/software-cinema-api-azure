package com.Senai.Filmes.Repository;

import com.Senai.Filmes.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IReservaRepository extends JpaRepository<Reserva, UUID> {

    @Query("SELECT COUNT(r) > 0 FROM Reserva r JOIN r.assentos ra " +
           "WHERE r.sessao.id = :sessaoId AND ra.assento.id = :assentoId " +
           "AND r.status <> 'CANCELADA'")
    boolean isAssentoOcupado(@Param("sessaoId") UUID sessaoId, @Param("assentoId") UUID assentoId);

    List<Reserva> findByUsuarioId(UUID id);
}