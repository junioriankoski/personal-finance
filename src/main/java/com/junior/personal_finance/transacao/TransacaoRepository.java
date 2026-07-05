package com.junior.personal_finance.transacao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.junior.personal_finance.categoria.Categoria;
import com.junior.personal_finance.enums.TipoTransacao;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findAllByOrderByIdAsc();
    List<Transacao> findByTipo(TipoTransacao tipo);
    List<Transacao> findByCategoria(Categoria categoria);
}
