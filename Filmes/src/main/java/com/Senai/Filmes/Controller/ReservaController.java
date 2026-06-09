package com.Senai.Filmes.Controller;

import com.Senai.Filmes.DTO.Request.ReservaRequest;
import com.Senai.Filmes.DTO.Response.ReservaResponse;
import com.Senai.Filmes.Model.Usuario;
import com.Senai.Filmes.Service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Reservas", description = "Endpoint para gerenciamento de reservas de ingressos")
@RestController
@CrossOrigin("*")
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    @Operation(summary = "Criar reserva", description = "Cria uma reserva para um usuário específico")
    public ResponseEntity<ReservaResponse> criarReserva(@RequestBody ReservaRequest request) {
        Usuario logado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(reservaService.criarReserva(request, logado.getId()), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todas as reservas", description = "Retorna uma lista de todas as reservas cadastradas")
    public ResponseEntity<List<ReservaResponse>> listarTodas() {
        List<ReservaResponse> reservas = reservaService.listarTodas();
        if (reservas.isEmpty()) {
            return new ResponseEntity<>(reservas, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar reserva", description = "Cancela uma reserva existente e libera os assentos")
    public ResponseEntity<Void> cancelarReserva(@PathVariable UUID id) {
        reservaService.cancelarReserva(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reserva por id", description = "Busca detalhes de uma reserva específica")
    public ResponseEntity<ReservaResponse> buscarPorId(@PathVariable UUID id) {
        return new ResponseEntity<>(reservaService.buscarReservaPorId(id), HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar reservas do usuário", description = "Lista todas as reservas realizadas por um usuário específico")
    public ResponseEntity<List<ReservaResponse>> listarPorUsuario(@PathVariable UUID usuarioId) {
        List<ReservaResponse> reservas = reservaService.listarPorUsuario(usuarioId);
        if (reservas.isEmpty()) {
            return new ResponseEntity<>(reservas, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }
}
