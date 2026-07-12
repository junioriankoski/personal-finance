package com.junior.personal_finance.transacao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.junior.personal_finance.categoria.Categoria;
import com.junior.personal_finance.enums.TipoTransacao;

import java.time.LocalDate;
import java.util.List;
import com.junior.personal_finance.auth.Usuario;


public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findAllByOrderByIdAsc();
    List<Transacao> findByTipo(TipoTransacao tipo);
    List<Transacao> findByCategoriaAndUsuario(Categoria categoria, Usuario usuario);
    List<Transacao> findByDataBetweenAndUsuario(LocalDate inicio, LocalDate fim, Usuario usuario);
    List<Transacao> findByUsuario(Usuario usuario);
    List<Transacao> findByTipoAndUsuario(TipoTransacao tipo, Usuario usuario);
}
