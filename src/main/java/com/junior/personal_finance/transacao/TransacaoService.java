package com.junior.personal_finance.transacao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.junior.personal_finance.auth.Usuario;
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
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
        return transacaoRepository.findByUsuario(usuarioLogado)
        .stream()
        .map(transacao -> new TransacaoResponse(
            transacao.getId(),
            transacao.getDescricao(),
            transacao.getValor(),
            transacao.getData(),
            transacao.getTipo(),
            transacao.getCategoria() != null ? transacao.getCategoria().getNome() : null,
            transacao.getCategoria() != null ? transacao.getCategoria().getId() : null
        ))
        .toList();
    }

    public TransacaoResponse buscarPorId(Long id) {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder .getContext()
            .getAuthentication()
            .getPrincipal();
        Transacao transacao = transacaoRepository.findByIdAndUsuario(id, usuarioLogado)
            .orElseThrow(() -> new RecursosNaoEncontradosException("Transação não encontrada."));
        
        return new TransacaoResponse(
            transacao.getId(),
            transacao.getDescricao(),
            transacao.getValor(),
            transacao.getData(),
            transacao.getTipo(),
            transacao.getCategoria() != null ? transacao.getCategoria().getNome() : null,
            transacao.getCategoria() != null ? transacao.getCategoria().getId() : null
        );
    }

    public TransacaoResponse atualizarTransacao(TransacaoRequest transacaoAtualziada, Long id) {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder .getContext()
            .getAuthentication()
            .getPrincipal();
        Transacao transacao = transacaoRepository.findByIdAndUsuario(id, usuarioLogado)
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
                transacaoSalva.getCategoria() != null ? transacaoSalva.getCategoria().getNome() : null,
                transacaoSalva.getCategoria() != null ? transacaoSalva.getCategoria().getId() : null
            );
    }
    public double calcularSaldo() {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder .getContext()
            .getAuthentication()
            .getPrincipal();
        List<Transacao> listaReceitas = transacaoRepository.findByTipoAndUsuario(TipoTransacao.RECEITA, usuarioLogado);
        List<Transacao> listaDespesas = transacaoRepository.findByTipoAndUsuario(TipoTransacao.DESPESA, usuarioLogado);
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
        Usuario usuarioLogado = (Usuario) SecurityContextHolder .getContext()
            .getAuthentication()
            .getPrincipal();
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
        .map(categoria -> {
           double total = transacaoRepository.findByCategoriaAndUsuario(categoria, usuarioLogado)
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
        Usuario usuarioLogado = (Usuario) SecurityContextHolder .getContext()
            .getAuthentication()
            .getPrincipal();
        return transacaoRepository.findByDataBetweenAndUsuario(inicio, fim, usuarioLogado)
        .stream()
        .map(transacao -> new TransacaoResponse(
            transacao.getId(),
            transacao.getDescricao(),
            transacao.getValor(),
            transacao.getData(),
            transacao.getTipo(),
            transacao.getCategoria() != null ? transacao.getCategoria().getNome() : null,
            transacao.getCategoria() != null ? transacao.getCategoria().getId() : null
        ))
        .toList();
    }

    public TransacaoResponse salvar(TransacaoRequest transacao) {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
        Categoria categoria = categoriaRepository.findById(transacao.getCategoriaId())
            .orElseThrow(() -> new RecursosNaoEncontradosException("Categoria não encontrada."));
        Transacao novaTransacao = new Transacao(
            transacao.getDescricao(),
            transacao.getValor(),
            transacao.getData(),
            transacao.getTipo()
        );
        novaTransacao.setCategoria(categoria);
        novaTransacao.setUsuario(usuarioLogado);
        Transacao transacaoSalva = transacaoRepository.save(novaTransacao);
        return new TransacaoResponse(
            transacaoSalva.getId(),
            transacaoSalva.getDescricao(),
            transacaoSalva.getValor(),
            transacaoSalva.getData(),
            transacaoSalva.getTipo(),
            transacaoSalva.getCategoria() != null ? transacaoSalva.getCategoria().getNome() : null,
            transacaoSalva.getCategoria() != null ? transacaoSalva.getCategoria().getId() : null
        );
    }

    public Boolean deletarPorId(Long id) {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder .getContext()
            .getAuthentication()
            .getPrincipal();
        
        Transacao transacao = transacaoRepository.findByIdAndUsuario(id, usuarioLogado)
            .orElse(null);
            
        if (transacao == null){
            return false;
        }
        transacaoRepository.delete(transacao);
        return true;
    }
}
