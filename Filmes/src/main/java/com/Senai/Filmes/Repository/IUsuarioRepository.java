package com.Senai.Filmes.Repository;

import com.Senai.Filmes.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmai(String email);
    boolean existByEmail(String email);
}
