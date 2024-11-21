package com.fran.FRAN.controller;

import com.fran.FRAN.dto.response.RelatorioResponseDTO;
import com.fran.FRAN.model.dao.EstagioRepository;
import com.fran.FRAN.model.dao.RelatorioRepository;
import com.fran.FRAN.model.entity.Estagio;
import com.fran.FRAN.model.entity.Mes;
import com.fran.FRAN.model.entity.Relatorio;
import com.fran.FRAN.model.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioRepository relatorioRepository;
    @Autowired
    private EstagioRepository estagioRepository;

    /**
     * Atualiza as horas totais de um relatório.
     */
    @PatchMapping("/{id}/horas-totais")
    public ResponseEntity<Relatorio> atualizarHorasTotais(@PathVariable Long id, @RequestBody LocalTime novasHorasTotais) {
        Relatorio relatorio = buscarRelatorioPorId(id);
        relatorio.setHoras_totais(novasHorasTotais);
        return ResponseEntity.ok(relatorioRepository.save(relatorio));
    }

    /**
     * Atualiza as horas válidas de um relatório.
     */
    @PatchMapping("/{id}/horas-validas")
    public ResponseEntity<Relatorio> atualizarHorasValidas(@PathVariable Long id, @RequestBody LocalTime novasHorasValidas) {
        Relatorio relatorio = buscarRelatorioPorId(id);
        relatorio.setHoras_validas(novasHorasValidas);
        return ResponseEntity.ok(relatorioRepository.save(relatorio));
    }

    /**
     * Atualiza a data de entrega de um relatório.
     */
    @PatchMapping("/{id}/data-entregue")
    public ResponseEntity<Relatorio> atualizarDataEntregue(@PathVariable Long id, @RequestBody LocalDateTime novaDataEntregue) {
        Relatorio relatorio = buscarRelatorioPorId(id);
        relatorio.setData_entregue(novaDataEntregue);
        return ResponseEntity.ok(relatorioRepository.save(relatorio));
    }

    /**
     * Atualiza o mês de referência de um relatório.
     */
    @PatchMapping("/{id}/mes-referencia")
    public ResponseEntity<Relatorio> atualizarMesReferencia(@PathVariable Long id, @RequestBody Mes novoMesReferencia) {
        Relatorio relatorio = buscarRelatorioPorId(id);
        relatorio.setMes_referencia(novoMesReferencia);
        return ResponseEntity.ok(relatorioRepository.save(relatorio));
    }

    /**
     * Atualiza o status de um relatório.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Relatorio> atualizarStatus(@PathVariable Long id, @RequestBody Status novoStatus) {
        Relatorio relatorio = buscarRelatorioPorId(id);
        relatorio.setStatus(novoStatus);
        return ResponseEntity.ok(relatorioRepository.save(relatorio));
    }

    /**
     * Método utilitário para buscar um relatório pelo ID.
     */
    private Relatorio buscarRelatorioPorId(Long id) {
        return relatorioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relatório não encontrado"));
    }

    @GetMapping("/{estagioId}/relatorios")
    public ResponseEntity<List<RelatorioResponseDTO>> listarRelatoriosPorEstagio(@PathVariable Long estagioId) {
        // Buscar o estágio no banco de dados
        Estagio estagio = estagioRepository.findById(estagioId)
                .orElseThrow(() -> new RuntimeException("Estágio não encontrado"));

        // Obter os relatórios associados ao estágio
        List<Relatorio> relatorios = estagio.getRelatorios();

        // Converter a lista de Relatório para uma lista de RelatorioResponseDTO
        List<RelatorioResponseDTO> relatorioResponseDTOs = relatorios.stream()
                .map(RelatorioResponseDTO::new)
                .toList();

        // Retornar os relatórios
        return ResponseEntity.ok(relatorioResponseDTOs);
    }

}

