package com.Senai.Filmes.Controller;

import com.Senai.Filmes.DTO.Request.FilmeRequest;
import com.Senai.Filmes.DTO.Response.FilmeResponse;
import com.Senai.Filmes.Service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @GetMapping
    @Operation(summary = "Listar todos os filmes", description = "listar todos os flmes")
    public ResponseEntity<List<FilmeResponse>> listarTodos(){
        List<FilmeResponse> filmes = filmeService.listarTodos();
        if (filmes.isEmpty()){
            return new ResponseEntity<>(filmes, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(filmes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar filme por id", description = "Buscar por id")
    public ResponseEntity<FilmeResponse> buscarFilmePorId(@PathVariable UUID id){
        return new ResponseEntity<>(filmeService.buscarPorFilmeId(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Criar filme", description = "Criar filme")
    public ResponseEntity<FilmeResponse> criarFilme (@RequestBody FilmeRequest filmeRequest){
        return new ResponseEntity<>(filmeService.cadastrarFilme(filmeRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar filme", description = "Atualiza os dados de um filme")
    public ResponseEntity<FilmeResponse> atualizar(@PathVariable UUID id, @RequestBody FilmeRequest filmeRequest){
        return new ResponseEntity<>(filmeService.atualizarFilme(id, filmeRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Filme", description = "Remove um filme do sistema")
    public ResponseEntity<Void> deletar(@PathVariable UUID id){
        filmeService.deletarFilme(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
