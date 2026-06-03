package com.Senai.Filmes.Controller;

import com.Senai.Filmes.DTO.Request.FilmeRequest;
import com.Senai.Filmes.DTO.Request.SalaRequest;
import com.Senai.Filmes.DTO.Response.SalaResponse;
import com.Senai.Filmes.Service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/sala")
public class SalaController {

    @Autowired
    SalaService salaService;

    @GetMapping
    @Operation(summary = "Listar todas as salas", description = "Listar todas as salas")
    public ResponseEntity<List<SalaResponse>> cadastrarSala(SalaRequest salaRequest){
        List<SalaResponse> salas = salaService.listarTodasAsSalas();
        if (salas.isEmpty()){
            return new ResponseEntity<>(salas, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(salas, HttpStatus.OK);
    }


}
