package com.Senai.Filmes.Repository;

import com.Senai.Filmes.Model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ISalaRepository extends JpaRepository<Sala, UUID> {
}
