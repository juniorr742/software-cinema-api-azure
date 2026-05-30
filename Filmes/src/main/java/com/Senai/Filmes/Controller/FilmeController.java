package com.Senai.Filmes.Controller;

import com.Senai.Filmes.DTO.Response.FilmeResponse;
import com.Senai.Filmes.Model.Filme;
import com.Senai.Filmes.Service.FilmeService;
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
    public ResponseEntity<List<FilmeResponse>> listarTodos(){
        List<FilmeResponse> filmes = filmeService.listarTodos();
        if (filmes.isEmpty()){
            return new ResponseEntity<>(filmes, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(filmes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeResponse> buscarFilmePorId(@PathVariable UUID id){
        FilmeResponse filme = filmeService.buscarPorFilmeId(id);
        return
    }
}
