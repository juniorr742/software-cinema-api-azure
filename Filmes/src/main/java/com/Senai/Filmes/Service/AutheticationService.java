package com.Senai.Filmes.Service;

import com.Senai.Filmes.DTO.Request.CadastroRequest;
import com.Senai.Filmes.DTO.Request.LoginRequest;
import com.Senai.Filmes.DTO.Response.AuthResponse;
import com.Senai.Filmes.Model.Enums.Cargo;
import com.Senai.Filmes.Model.Usuario;
import com.Senai.Filmes.Repository.IUsuarioRepository;
import com.Senai.Filmes.security.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutheticationService {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passworEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse cadastrarUsuario(CadastroRequest cadastroRequest){
       if (usuarioRepository.existsByEmail(cadastroRequest.email())){
           throw new RuntimeException("E-mail já cadastrado");
       }
       
       Usuario novoUsuario = new Usuario();
       novoUsuario.setNome(cadastroRequest.nome());
       novoUsuario.setEmail(cadastroRequest.email());
       novoUsuario.setSenha(passworEncoder.encode(cadastroRequest.senha()));
       novoUsuario.setCargo(Cargo.ADMIN);// vou deixar o cadastro pra nascer como admin temporariamente, assim consigo testar os endpoints

       usuarioRepository.save(novoUsuario);

       String token = jwtUtil.gerarToken(novoUsuario);
       return new AuthResponse(token, novoUsuario.getNome(), novoUsuario.getCargo().name());
    }

    public AuthResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.senha())
        );

        var usuario = usuarioRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        String token = jwtUtil.gerarToken(usuario);
        return new AuthResponse(token, usuario.getNome(), usuario.getCargo().name());
    }


}
