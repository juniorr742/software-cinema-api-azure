package com.Senai.Filmes.Service;

import com.Senai.Filmes.DTO.Request.CadastroRequest;
import com.Senai.Filmes.DTO.Response.AuthReponse;
import com.Senai.Filmes.Repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutheticationService {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passworEncoder;

    @Autowired
    private AuthReponse cadastrarUsuario(CadastroRequest cadastroRequest)


}
