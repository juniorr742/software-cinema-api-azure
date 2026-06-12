package com.Senai.Filmes.Service;

import com.Senai.Filmes.DTO.Request.SessaoRequest;
import com.Senai.Filmes.DTO.Response.AssentoResponse;
import com.Senai.Filmes.DTO.Response.FilmeResponse;
import com.Senai.Filmes.DTO.Response.SessaoResponse;
import com.Senai.Filmes.Model.Assento;
import com.Senai.Filmes.Model.Filme;
import com.Senai.Filmes.Model.Sala;
import com.Senai.Filmes.Model.Sessao;
import com.Senai.Filmes.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SessaoService {

    @Autowired
    IFilmeRepository filmeRepository;

    @Autowired
    ISalaRepository salaRepository;

    @Autowired
    ISessaoRepository sessaoRepository;

    @Autowired
    IAssentoRepository assentoRepository;

    @Autowired
    IReservaRepository reservaRepository;

    @Transactional
    public SessaoResponse criarSessao(SessaoRequest sessaoRequest){

        Filme filme = filmeRepository.findById(sessaoRequest.filmeId()).orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));
        Sala sala = salaRepository.findById(sessaoRequest.salaId()).orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        if (sessaoRequest.inicio().isAfter(sessaoRequest.fim())){
            throw new RuntimeException("O horário de inicio não pode ser depois do fim");
        }

        boolean conflito = sessaoRepository.existeConflitoDeSala(sessaoRequest.salaId(),
                sessaoRequest.inicio(), sessaoRequest.fim());

        if (conflito){
            throw new RuntimeException("Já existe uma sessão nesta sala para o horário selecionado");
        }

        Sessao sessao = new Sessao();
        sessao.setFilme(filme);
        sessao.setSala(sala);
        sessao.setInicio(sessaoRequest.inicio());
        sessao.setFim(sessaoRequest.fim());
        sessao.setPreco(sessaoRequest.preco());

        sessaoRepository.save(sessao);

        return toResponse(sessao);
    }

    public SessaoResponse buscarSessaoPorId(UUID id){
        Sessao sessao = sessaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));

        return toResponse(sessao);
    }

    public List<SessaoResponse> buscarSessaoPorData(LocalDateTime inicio, LocalDateTime fim){
        List<SessaoResponse> sessoes =  sessaoRepository.findByData(inicio, fim).stream()
                .map(this::toResponse).toList();

        return sessoes;
    }

    public List<AssentoResponse> obterMapaDeAssentos(UUID sessaoId){
        Sessao sessao = sessaoRepository.findById(sessaoId).orElseThrow(() -> new RuntimeException("Sessão não encontrada"));

        List<Assento> todosOsAssentos = assentoRepository.findBySalaId(sessao.getSala().getId());

        return todosOsAssentos.stream().map(assento -> {
            boolean ocupado = reservaRepository.isAssentoOcupado(sessaoId, assento.getId());
            return new AssentoResponse(assento.getId(),
                    assento.getFileira(),
                    assento.getNumero(),
                    !ocupado);
        }).toList();
    }

    public List<SessaoResponse> listarTodaSessoes(){
        return sessaoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public SessaoResponse atualizarSessao(UUID id, SessaoRequest request){
        Sessao sessao = sessaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));
        Filme filme = filmeRepository.findById(request.filmeId()).orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));
        Sala sala = salaRepository.findById(request.salaId()).orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        sessao.setFilme(filme);
        sessao.setSala(sala);
        sessao.setInicio(request.inicio());
        sessao.setFim(request.fim());
        sessao.setPreco(request.preco());

        sessaoRepository.save(sessao);

        return toResponse(sessao);
    }

    public boolean deletar(UUID id){
        boolean existe = sessaoRepository.existsById(id);
        if (existe){
            sessaoRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    private SessaoResponse toResponse(Sessao sessao){

        FilmeResponse filmeResponse = new FilmeResponse(sessao.getFilme().getId(),
                sessao.getFilme().getTitulo(),
                sessao.getFilme().getDescricao(),
                sessao.getFilme().getUrlPoster(),
                sessao.getFilme().getGenero(),
                sessao.getFilme().getDuracaoMinutos());


       return new SessaoResponse(sessao.getSala().getId(),
                filmeResponse,
                sessao.getInicio(),
                sessao.getFim(),
                sessao.getPreco());
    }
}
