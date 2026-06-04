package com.Senai.Filmes.Controller;


import com.Senai.Filmes.DTO.Request.SalaRequest;
import com.Senai.Filmes.DTO.Response.SalaResponse;
import com.Senai.Filmes.Model.Assento;
import com.Senai.Filmes.Model.Sala;
import com.Senai.Filmes.Service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Tag(name = "Salas", description = "Endpoint para gerenciamento de salas")
@RestController
@CrossOrigin("*")
@RequestMapping("/api/sala")
public class SalaController {

    @Autowired
    SalaService salaService;

    @GetMapping
    @Operation(summary = "Listar todas as salas", description = "Listar todas as salas")
    public ResponseEntity<List<SalaResponse>> listarTodasSalas(){
        List<SalaResponse> salas = salaService.listarTodasAsSalas();
        if (salas.isEmpty()){
            return new ResponseEntity<>(salas, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(salas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sala por id", description = "Buscar por id")
    public ResponseEntity<SalaResponse>buscarPorId(@PathVariable UUID id){
        return new ResponseEntity<>(salaService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    //@Pre
    @Operation(summary = "Cadastrar sala", description = "Cadastrar uma sala")
    public ResponseEntity<SalaResponse> criarSala(@RequestBody SalaRequest salaRequest){
        return new ResponseEntity<>(salaService.cadastrarSala(salaRequest), HttpStatus.CREATED);
    }

    @DeleteMapping
    @Operation(summary = "Deletar por id", description = "Rota para deletar por id")
    public ResponseEntity<Void> deletarPorId (@PathVariable UUID id){
        salaService.deletarPorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
