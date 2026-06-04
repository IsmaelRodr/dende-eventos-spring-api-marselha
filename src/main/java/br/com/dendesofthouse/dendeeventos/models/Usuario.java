package br.com.dendesofthouse.dendeeventos.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)

public class Usuario {

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

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Ingresso> ingressos = new ArrayList<>();

    // Construtor conveniente (sem id e sem ingressos)
    public Usuario(String nome, LocalDate dataNascimento, String sexo,
                   String email, String senha) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.email = email;
        this.senha = senha;
    }

    /**
     * Adiciona um ingresso ao usuário, garantindo a relação bidirecional.
     */
    public void adicionarIngresso(Ingresso ingresso) {
        if (ingresso == null) {
            throw new IllegalArgumentException("Ingresso não pode ser nulo");
        }
        if (!ingressos.contains(ingresso)) {
            ingressos.add(ingresso);
            ingresso.setUsuario(this);
        }
    }

    /**
     * Retorna uma lista imutável dos ingressos do usuário.
     */
    public List<Ingresso> getIngressos() {
        return List.copyOf(ingressos);
    }

    /**
     * Substitui a lista de ingressos, mantendo a relação bidirecional.
     */
    public void setIngressos(List<Ingresso> ingressos) {
        this.ingressos.clear();
        if (ingressos != null) {
            this.ingressos.addAll(ingressos);
            this.ingressos.forEach(i -> i.setUsuario(this));
        }
    }
}