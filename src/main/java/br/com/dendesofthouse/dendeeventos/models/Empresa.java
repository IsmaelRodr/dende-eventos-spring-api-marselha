package br.com.dendesofthouse.dendeeventos.models;

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
    @NotBlank(message = "CNPJ é obrigatório")
    @Column(name = "cnpj", length = 18, nullable = false, unique = true)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String cnpj;


    @NotBlank(message = "Razão social é obrigatória")
    @Column(name = "razao_social", length = 255, nullable = false)
    @ToString.Include
    private String razaoSocial;

    @NotBlank(message = "Nome Fantasia é obrigatório")
    @Column(name = "nome_fantasia", length = 255, nullable = false)
    @ToString.Include
    private String nomeFantasia;

    // Relacionamento ManyToOne com Organizador (assumindo que a entidade Organizador existe)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "organizador_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_empresa_organizador")
    )
    @ToString.Exclude // evita loop no toString
    private Organizador organizador;
}