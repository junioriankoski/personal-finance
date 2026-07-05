package com.junior.personal_finance.categoria;

import java.util.List;

import org.springframework.stereotype.Service;

import com.junior.personal_finance.exceptions.RecursosNaoEncontradosException;

@Service
public class CategoriaService {
    private CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponse> listarTodos() {
        return categoriaRepository.findAllByOrderByIdAsc()
        .stream()
        .map(categoria -> new CategoriaResponse(
            categoria.getId(),
            categoria.getNome(),
            categoria.getTipo()
        ))
        .toList();
    }

    public CategoriaResponse buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RecursosNaoEncontradosException("Categoria não encontrada"));

        return new CategoriaResponse(
            categoria.getId(),
            categoria.getNome(),
            categoria.getTipo()
        );
    }

    public CategoriaResponse salvar(CategoriaRequest categoria) {
        Categoria novaCategoria = new Categoria(categoria.getNome(), categoria.getTipo());
        Categoria categoriaSalva = categoriaRepository.save(novaCategoria);

        return new CategoriaResponse(
            categoriaSalva.getId(),
            categoriaSalva.getNome(),
            categoriaSalva.getTipo()
        );
    }

    public CategoriaResponse atualizarCategoria(CategoriaRequest categoriaAtualizada, Long id) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RecursosNaoEncontradosException("Categoria não encontrada"));

        categoria.setNome(categoriaAtualizada.getNome());
        categoria.setTipo(categoriaAtualizada.getTipo());
        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return new CategoriaResponse(
         categoriaSalva.getId(),
         categoriaSalva.getNome(),
         categoriaSalva.getTipo()
        );
    }

    public boolean deletarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            return false;
        }
        categoriaRepository.deleteById(id);
        return true;
    }
}
