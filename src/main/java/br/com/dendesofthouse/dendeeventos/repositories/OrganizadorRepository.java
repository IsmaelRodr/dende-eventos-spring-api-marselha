package br.com.dendesofthouse.dendeeventos.repositories;

import br.com.dendesofthouse.dendeeventos.models.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, Long> {

    Optional<Organizador> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT o FROM Organizador o LEFT JOIN FETCH o.eventos WHERE o.id = :id")
    Optional<Organizador> findByIdWithEventos(@Param("id") Long id);
}