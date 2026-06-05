package br.com.dendesofthouse.dendeeventos.repositories;

import br.com.dendesofthouse.dendeeventos.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query("SELECT e FROM Evento e WHERE e.eventoAtivo = true AND e.dataFim > CURRENT_TIMESTAMP AND e.capacidadeMaxima > (SELECT COUNT(i) FROM Ingresso i WHERE i.evento = e AND i.status = 'ACEITO') ORDER BY e.dataInicio, e.nome")
    List<Evento> findAllAtivosDisponiveis();

    List<Evento> findAllByOrganizadorId(Long organizadorId);

    @Query("SELECT DISTINCT e FROM Evento e LEFT JOIN FETCH e.ingressos WHERE e.id = :id")
    Optional<Evento> findByIdWithIngressos(@Param("id") Long id);

    boolean existsByOrganizadorIdAndEventoAtivoTrue(Long organizadorId);
}