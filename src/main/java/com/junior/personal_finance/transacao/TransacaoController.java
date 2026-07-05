package com.junior.personal_finance.transacao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransacaoController {
    private TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @GetMapping("/transacoes")
    public List<TransacaoResponse> getTransacoes() {
        return service.listarTodas();
    }

    @GetMapping("/transacoes/{id}")
    public ResponseEntity<TransacaoResponse> getTransacao(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping("/transacoes")
    public ResponseEntity<TransacaoResponse> adicionarTransacao(@RequestBody TransacaoRequest transacao){
        return ResponseEntity.ok(service.salvar(transacao));
    }

    @PutMapping("/transacoes/{id}")
    public ResponseEntity<TransacaoResponse> atualizarTransacao(@RequestBody TransacaoRequest transacaoAtualizada, @PathVariable Long id) {
        return ResponseEntity.ok(service.atualizarTransacao(transacaoAtualizada, id));
    }

    @DeleteMapping("/transacoes/{id}")
    public ResponseEntity<Void> deletarTransacao(@PathVariable Long id) {
        if (service.deletarPorId(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
