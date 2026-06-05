package br.com.dendesofthouse.dendeeventos.models;

import br.com.dendesofthouse.dendeeventos.exceptions.evento.*;
import br.com.dendesofthouse.dendeeventos.exceptions.ingresso.CancelamentoNaoPermitidoException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "evento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizador_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_evento_organizador"))
    @JsonIgnore
    private Organizador organizador;

    @Column(name = "nome", length = 255, nullable = false)
    @ToString.Include
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "pagina_web", length = 500)
    private String paginaWeb;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDateTime dataFim;

    @Convert(converter = TipoEventoConverter.class)
    @Column(name = "tipo_evento", nullable = false)
    private TipoEvento tipoEvento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_principal_id", foreignKey = @ForeignKey(name = "fk_evento_principal"))
    @JsonIgnore
    private Evento eventoPrincipal;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidade", nullable = false)
    private Modalidade modalidade;

    @Column(name = "preco_unitario_ingresso", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitarioIngresso;

    @Column(name = "taxa_cancelamento", nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal taxaCancelamento = BigDecimal.ZERO;


    @Column(name = "evento_estorno", nullable = false)
    @Builder.Default
    private boolean eventoEstorno = false;

    @Column(name = "capacidade_maxima", nullable = false)
    private int capacidadeMaxima;

    @Column(name = "local_evento", length = 255, nullable = false)
    private String localEvento;

    @Column(name = "evento_ativo", nullable = false)
    @Builder.Default
    private boolean eventoAtivo = false;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    @ToString.Exclude
    private List<Ingresso> ingressos = new ArrayList<>();

    // Campo calculado (não persistido)
    public int getIngressosDisponiveis() {
        long vendidosNaoCancelados = ingressos.stream()
                .filter(i -> i.getStatus() == Ingresso.StatusIngresso.ACEITO)
                .count();
        return capacidadeMaxima - (int) vendidosNaoCancelados;
    }

    public List<Ingresso> getIngressos() {
        return Collections.unmodifiableList(ingressos);
    }

    public void setIngressos(List<Ingresso> ingressos) {
        this.ingressos.clear();
        if (ingressos != null) {
            this.ingressos.addAll(ingressos);
            this.ingressos.forEach(i -> i.setEvento(this));
        }
    }

    // Regras de negócio
    public void ativar() {
        if (this.isEventoAtivo()) throw new EventoJaAtivoException("Evento já está ativo.");
        setEventoAtivo(true);
    }

    public List<Ingresso> desativar() {
        if (!this.isEventoAtivo()) throw new EventoJaInativoException("Evento já está inativo.");
        List<Ingresso> cancelados = new ArrayList<>();
        for (Ingresso ingresso : ingressos) {
            if (!ingresso.isCancelado()) {
                ingresso.cancelar();
                cancelados.add(ingresso);
            }
        }
        setEventoAtivo(false);
        return cancelados;
    }

    public void adicionarIngresso(Ingresso ingresso) {
        if (getIngressosDisponiveis() <= 0) {
            throw new EventoSemIngressosDisponiveisException("Ingressos esgotados.");
        }
        if (!ingressos.contains(ingresso)) {
            ingressos.add(ingresso);
            ingresso.setEvento(this);
        }
    }

    public void cancelarIngressoIndividual(Ingresso ingresso) {
        if (!this.eventoEstorno) {
            throw new CancelamentoNaoPermitidoException("Evento não permite estorno.");
        }
        ingresso.cancelar();
    }

    // Enums e converter
    public enum TipoEvento {
        SOCIAL, CORPORATIVO, ACADEMICO, CULTURAL, ENTRETENIMENTO, RELIGIOSOS,
        ESPORTIVOS, FEIRA, CONGRESSO, OFICINA, CURSO, TREINAMENTO, AULA, SEMINARIO,
        PALESTRA, SHOW, FESTIVAL, EXPOSICAO, RETIRO, CULTO, CELEBRACAO, CAMPEONATO, CORRIDA
    }

    public enum Modalidade {
        PRESENCIAL, REMOTO, HIBRIDO
    }

    @Converter(autoApply = true)
    public static class TipoEventoConverter implements AttributeConverter<TipoEvento, String> {
        @Override
        public String convertToDatabaseColumn(TipoEvento attribute) {
            return attribute == null ? null : attribute.name();
        }

        @Override
        public TipoEvento convertToEntityAttribute(String dbData) {
            return dbData == null ? null : TipoEvento.valueOf(dbData);
        }
    }
}