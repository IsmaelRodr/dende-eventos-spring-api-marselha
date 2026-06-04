package br.com.dendesofthouse.dendeeventos.repositories;

import br.com.dendesofthouse.dendeeventos.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // FIM! Você acabou de herdar todos os métodos save(), findById(), findAll(), delete()...
}