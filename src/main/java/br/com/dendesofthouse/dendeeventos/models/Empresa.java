package br.com.dendesofthouse.dendeeventos.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)

public class Empresa {

    @Id
    @Column(name = "cnpj", length = 18, nullable = false, unique = true)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String cnpj;

    @Column(name = "razao_social", length = 255, nullable = false)
    @ToString.Include
    private String razaoSocial;

    @Column(name = "nome_fantasia", length = 255, nullable = false)
    @ToString.Include
    private String nomeFantasia;

    // Relacionamento ManyToOne com Organizador (assumindo que a entidade Organizador existe)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "organizador_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_empresa_organizador")
    )
    @ToString.Exclude // evita loop no toString
    @JsonIgnore
    private Organizador organizador;

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
        if (organizador != null && organizador.getEmpresa() != this) {
            organizador.setEmpresa(this);
        }
    }
}