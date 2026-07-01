package com.junior.personal_finance.categoria;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriaController {
    private CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping("/categorias")
    public List<CategoriaResponse> getCategorias() {
        return service.listarTodos();
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponse> getCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping("/categorias")
    public CategoriaResponse criarCategoria(@RequestBody CategoriaRequest categoria) {
        return service.salvar(categoria);
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponse> atualizarCategoria(@RequestBody CategoriaRequest categoria, @PathVariable Long id) {
        return ResponseEntity.ok(service.atualizarCategoria(categoria, id));
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        if(service.deletarCategoria(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
