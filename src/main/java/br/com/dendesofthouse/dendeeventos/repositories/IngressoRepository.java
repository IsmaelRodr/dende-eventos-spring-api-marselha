package br.com.dendesofthouse.dendeeventos.repositories;

import br.com.dendesofthouse.dendeeventos.models.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {

    List<Ingresso> findAllByUsuarioId(Long usuarioId);

    List<Ingresso> findAllByEventoId(Long eventoId);

    @Query("SELECT i FROM Ingresso i JOIN FETCH i.evento WHERE i.usuario.id = :usuarioId")
    List<Ingresso> findAllByUsuarioIdWithEvento(@Param("usuarioId") Long usuarioId);

    @Query("SELECT COUNT(i) FROM Ingresso i WHERE i.evento.id = :eventoId AND i.status = 'ACEITO'")
    long countIngressosVendidosAtivosByEventoId(@Param("eventoId") Long eventoId);
}