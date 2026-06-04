package br.com.dendesofthouse.dendeeventos.models;

import br.com.dendesofthouse.dendeeventos.exceptions.DadosInvalidosException;
import br.com.dendesofthouse.dendeeventos.exceptions.organizador.OrganizadorComEventosAtivosException;
import br.com.dendesofthouse.dendeeventos.exceptions.organizador.OrganizadorJaInativoException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Organizador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(name = "nome", length = 255, nullable = false)
    @ToString.Include
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "sexo", length = 50, nullable = false)
    private String sexo;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    @ToString.Include
    private String email;

    @Column(name = "senha", length = 255, nullable = false)
    private String senha;

    @Column(name = "ativo", nullable = false)
    @Builder.Default
    private boolean ativo = true;

    // Relacionamento com Empresa (mapeado pelo lado de Empresa, que possui organizador_id)
    @OneToOne(mappedBy = "organizador", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Empresa empresa;

    // Relacionamento com Evento (um organizador pode ter vários eventos)
    @OneToMany(mappedBy = "organizador", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Evento> eventos = new ArrayList<>();

    // Construtor conveniente (sem builder)
    public Organizador(String nome, LocalDate dataNascimento, String sexo,
                       String email, String senha, Empresa empresa) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.email = email;
        this.senha = senha;
        this.empresa = empresa;
    }

    /**
     * Desativa o organizador, desde que não possua eventos ativos.
     */
    public void desativar() {
        if (!this.isAtivo()) {
            throw new OrganizadorJaInativoException("Organizador já está inativo.");
        }
        boolean temEventosAtivos = eventos.stream().anyMatch(Evento::isEventoAtivo);
        if (temEventosAtivos) {
            throw new OrganizadorComEventosAtivosException("Organizador possui eventos ativos.");
        }
        this.ativo = false;
    }

    /**
     * Adiciona um evento ao organizador, estabelecendo a relação bidirecional.
     */
    public void adicionarEvento(Evento evento) {
        if (evento == null) {
            throw new DadosInvalidosException("O evento não pode ser nulo.");
        }
        if (!eventos.contains(evento)) {
            eventos.add(evento);
            evento.setOrganizador(this);
        }
    }

    /**
     * Retorna uma lista imutável dos eventos.
     */
    public List<Evento> getEventos() {
        return List.copyOf(eventos);
    }

    /**
     * Substitui a lista de eventos, mantendo a relação bidirecional.
     */
    public void setEventos(List<Evento> eventos) {
        this.eventos.clear();
        if (eventos != null) {
            this.eventos.addAll(eventos);
            this.eventos.forEach(e -> e.setOrganizador(this));
        }
    }
}