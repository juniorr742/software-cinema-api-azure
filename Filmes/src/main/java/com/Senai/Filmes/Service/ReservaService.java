package com.Senai.Filmes.Service;

import com.Senai.Filmes.DTO.Request.ReservaRequest;
import com.Senai.Filmes.DTO.Response.AssentoResponse;
import com.Senai.Filmes.DTO.Response.FilmeResponse;
import com.Senai.Filmes.DTO.Response.ReservaResponse;
import com.Senai.Filmes.DTO.Response.SessaoResponse;
import com.Senai.Filmes.Model.*;
import com.Senai.Filmes.Model.Enums.StatusReserva;
import com.Senai.Filmes.Repository.IAssentoRepository;
import com.Senai.Filmes.Repository.IReservaRepository;
import com.Senai.Filmes.Repository.ISessaoRepository;
import com.Senai.Filmes.Repository.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReservaService {

    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    ISessaoRepository sessaoRepository;

    @Autowired
    IUsuarioRepository usuarioRepository;

    @Autowired
    IAssentoRepository assentoRepository;

    @Transactional
    public ReservaResponse criarReserva(ReservaRequest request, UUID usuarioId){
        Sessao sessao = sessaoRepository.findById(request.sessaoId()).orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setSessao(sessao);
        reserva.setStatus(StatusReserva.CONFIRMADA);

        for (UUID assentoId: request.assentoIds()){
            Assento assento = assentoRepository.findById(assentoId).orElseThrow(() -> new EntityNotFoundException("Assento Id " + assentoId + " não encontrado"));

            if (!assento.getSala().getId().equals(sessao.getSala().getId())){
                throw new RuntimeException("O assento " + assento.getNumero() + " não pertence a esta sala");
            }

            if (reservaRepository.isAssentoOcupado(sessao.getId(), assentoId)){
                throw new RuntimeException("O assento " + assento.getFileira() + assento.getNumero() + " já está ocupado");
            }

            ReservaAssento reservaAssento = new ReservaAssento();
            reservaAssento.setReserva(reserva);
            reservaAssento.setAssento(assento);

            reserva.getAssentos().add(reservaAssento);
        }

        Reserva reservaSalva = reservaRepository.save(reserva);
        return toResponse(reservaSalva);
    }

    @Transactional
    public void cancelarReserva(UUID id){
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        if (reserva.getSessao().getInicio().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Não é possível cancelar uma reserva de uma sessão que ja iniciou ou finalizou");
        }

        reserva.setStatus(StatusReserva.CANCELADA);
        reservaRepository.save(reserva);
    }

    public ReservaResponse buscarReservaPorId(UUID id){
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
        return toResponse(reserva);
    }

    public List<ReservaResponse> listarPorUsuario(UUID usuarioId){
        return reservaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponse).toList();
    }

    public List<ReservaResponse> listarTodas (){
        return reservaRepository.findAll().stream().map(this::toResponse).toList();
    }



    private ReservaResponse toResponse(Reserva reserva){
        FilmeResponse filmeResponse = new FilmeResponse(reserva.getSessao().getFilme().getId(),
                reserva.getSessao().getFilme().getTitulo(),
                reserva.getSessao().getFilme().getDescricao(),
                reserva.getSessao().getFilme().getUrlPoster(),
                reserva.getSessao().getFilme().getGenero(),
                reserva.getSessao().getFilme().getDuracaoMinutos());

        SessaoResponse sessaoResponse = new SessaoResponse(reserva.getSessao().getSala().getId(),
                filmeResponse,
                reserva.getSessao().getInicio(),
                reserva.getSessao().getFim(),
                reserva.getSessao().getPreco());

        List<AssentoResponse> assentoResponses = reserva.getAssentos().stream().
                map(ra -> new AssentoResponse(ra.getAssento().getId(),
                        ra.getAssento().getFileira(),
                        ra.getAssento().getNumero(),
                        ra.getAssento().isStatus())).toList();

        return new ReservaResponse(reserva.getId(),
                sessaoResponse,
                assentoResponses,
                reserva.getStatus(),
                reserva.getCriadoEm());
    }
}
