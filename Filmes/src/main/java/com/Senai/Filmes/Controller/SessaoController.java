package com.Senai.Filmes.Controller;

import com.Senai.Filmes.DTO.Request.SessaoRequest;
import com.Senai.Filmes.DTO.Response.AssentoResponse;
import com.Senai.Filmes.DTO.Response.SessaoResponse;
import com.Senai.Filmes.Service.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Sessões", description = "Endpoint para gerenciamento de sessões de filmes")
@RestController
@CrossOrigin("*")
@RequestMapping("/api/sessao")
public class SessaoController {

    @Autowired
    private SessaoService service;

    @PostMapping
    @Operation(summary = "Criar uma sessão", description = "Rota para criar uma nova sessão de filme em uma sala")
    public ResponseEntity<SessaoResponse> criarSessao(@RequestBody SessaoRequest sessaoRequest){
        return new ResponseEntity<>(service.criarSessao(sessaoRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sessão por id", description = "Rota para buscar os detalhes de uma sessão específica")
    public ResponseEntity<SessaoResponse> buscarSessaoPorId(@PathVariable UUID id){
        return new ResponseEntity<>(service.buscarSessaoPorId(id), HttpStatus.OK);
    }

    @GetMapping("/data")
    @Operation(summary = "Buscar sessões por intervalo de data", description = "Lista sessões que iniciam entre o período informado")
    public ResponseEntity<List<SessaoResponse>> buscarPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim){
        List<SessaoResponse> sessoes = service.buscarSessaoPorData(inicio, fim);
        if (sessoes.isEmpty()){
           return new ResponseEntity<>(sessoes, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sessoes, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar todas as sessões", description = "Rota para listar todas as sessões cadastradas")
    public ResponseEntity<List<SessaoResponse>> listarTodasSessoes(){
        List<SessaoResponse> sessoes = service.listarTodaSessoes();
        if (sessoes.isEmpty()){
            return new ResponseEntity<>(sessoes, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sessoes, HttpStatus.OK);
    }

    @GetMapping("/{id}/mapa-assentos")
    @Operation(summary = "Obter mapa de assentos", description = "Retorna a lista de assentos e sua disponibilidade para uma sessão específica")
    public ResponseEntity<List<AssentoResponse>> obterMapaDeAssentos(@PathVariable UUID id) {
        return new ResponseEntity<>(service.obterMapaDeAssentos(id), HttpStatus.OK);
    }
}
