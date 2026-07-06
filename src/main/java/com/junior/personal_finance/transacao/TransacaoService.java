package com.junior.personal_finance.transacao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.junior.personal_finance.categoria.Categoria;
import com.junior.personal_finance.categoria.CategoriaRepository;
import com.junior.personal_finance.enums.TipoTransacao;
import com.junior.personal_finance.exceptions.RecursosNaoEncontradosException;

@Service
public class TransacaoService {
    private TransacaoRepository transacaoRepository;
    private CategoriaRepository categoriaRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, CategoriaRepository categoriaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<TransacaoResponse> listarTodas() {
        return transacaoRepository.findAllByOrderByIdAsc()
        .stream()
        .map(transacao -> new TransacaoResponse(
            transacao.getId(),
            transacao.getDescricao(),
            transacao.getValor(),
            transacao.getData(),
            transacao.getTipo(),
            transacao.getCategoria() != null ? transacao.getCategoria().getNome() : null
        ))
        .toList();
    }

    public TransacaoResponse buscarPorId(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
            .orElseThrow(() -> new RecursosNaoEncontradosException("Transação não encontrada."));
        
        return new TransacaoResponse(
            transacao.getId(),
            transacao.getDescricao(),
            transacao.getValor(),
            transacao.getData(),
            transacao.getTipo(),
            transacao.getCategoria() != null ? transacao.getCategoria().getNome() : null
        );
    }

    public TransacaoResponse atualizarTransacao(TransacaoRequest transacaoAtualziada, Long id) {
        Transacao transacao = transacaoRepository.findById(id)
            .orElseThrow(() -> new RecursosNaoEncontradosException("Transação não encontrada."));
        Categoria categoria = categoriaRepository.findById(transacaoAtualziada.getCategoriaId())
            .orElseThrow(() -> new RecursosNaoEncontradosException("Categoria não encontrada."));
            
            transacao.setDescricao(transacaoAtualziada.getDescricao());
            transacao.setValor(transacaoAtualziada.getValor());
            transacao.setData(transacaoAtualziada.getData());
            transacao.setTipo(transacaoAtualziada.getTipo());
            transacao.setCategoria(categoria);
            Transacao transacaoSalva = transacaoRepository.save(transacao);

            return new TransacaoResponse(
                transacaoSalva.getId(),
                transacaoSalva.getDescricao(),
                transacaoSalva.getValor(),
                transacaoSalva.getData(),
                transacaoSalva.getTipo(),
                transacaoSalva.getCategoria() != null ? transacaoSalva.getCategoria().getNome() : null
            );
    }
    public double calcularSaldo() {
        List<Transacao> listaReceitas = transacaoRepository.findByTipo(TipoTransacao.RECEITA);
        List<Transacao> listaDespesas = transacaoRepository.findByTipo(TipoTransacao.DESPESA);
        double totalReceitas = listaReceitas.stream()
        .mapToDouble(t -> t.getValor())
        .sum();
        double totalDespesas = listaDespesas.stream()
        .mapToDouble(d -> d.getValor())
        .sum();

        double saldo = totalReceitas - totalDespesas;

        return saldo;
    }

    public List<ResumoCategoria> resumoPorCategoria() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
        .map(categoria -> {
           double total = transacaoRepository.findByCategoria(categoria)
            .stream()
            .mapToDouble(t -> t.getValor())
            .sum();
        return new ResumoCategoria(
            categoria.getNome(),
            total
            );
        })
        .toList();
    }

    public List<TransacaoResponse> filtroPorPeriodo(LocalDate inicio, LocalDate fim) {
        return transacaoRepository.findByDataBetween(inicio, fim)
        .stream()
        .map(transacao -> new TransacaoResponse(
            transacao.getId(),
            transacao.getDescricao(),
            transacao.getValor(),
            transacao.getData(),
            transacao.getTipo(),
            transacao.getCategoria() != null ? transacao.getCategoria().getNome() : null
        ))
        .toList();
    }

    public TransacaoResponse salvar(TransacaoRequest transacao) {
        Categoria categoria = categoriaRepository.findById(transacao.getCategoriaId())
            .orElseThrow(() -> new RecursosNaoEncontradosException("Categoria não encontrada."));
        Transacao novaTransacao = new Transacao(
            transacao.getDescricao(),
            transacao.getValor(),
            transacao.getData(),
            transacao.getTipo()
        );
        novaTransacao.setCategoria(categoria);
        Transacao transacaoSalva = transacaoRepository.save(novaTransacao);
        return new TransacaoResponse(
            transacaoSalva.getId(),
            transacaoSalva.getDescricao(),
            transacaoSalva.getValor(),
            transacaoSalva.getData(),
            transacaoSalva.getTipo(),
            transacaoSalva.getCategoria() != null ? transacaoSalva.getCategoria().getNome() : null
        );
    }

    public Boolean deletarPorId(Long id) {
        if (!transacaoRepository.existsById(id)){
            return false;
        }
        transacaoRepository.deleteById(id);
        return true;
    }
}
