package br.com.dendesofthouse.dendeeventos.models;

import br.com.dendesofthouse.dendeeventos.exceptions.DadosInvalidosException;
import br.com.dendesofthouse.dendeeventos.exceptions.evento.EventoJaAtivoException;
import br.com.dendesofthouse.dendeeventos.exceptions.evento.EventoJaInativoException;
import br.com.dendesofthouse.dendeeventos.exceptions.evento.EventoSemIngressosDisponiveisException;
import br.com.dendesofthouse.dendeeventos.exceptions.ingresso.CancelamentoNaoPermitidoException;
import br.com.dendesofthouse.dendeeventos.exceptions.ingresso.IngressoJaCanceladoException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Column(name = "tipo_evento", nullable = false, columnDefinition = "ENUM(...)")
    private TipoEvento tipoEvento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_principal_id",
            foreignKey = @ForeignKey(name = "fk_evento_principal"))
    private Evento eventoPrincipal;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidade", nullable = false)
    private Modalidade modalidade;

    @Column(name = "preco_unitario_ingresso", nullable = false, precision = 10, scale = 2)
    private double precoUnitarioIngresso;

    @Column(name = "taxa_cancelamento", nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private double taxaCancelamento = 0.0;

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
    @ToString.Exclude
    private List<Ingresso> ingressos = new ArrayList<>();

    // Enum público (compatível com banco via converter)
    public enum TipoEvento {
        SOCIAL("SOCIAL"),
        CORPORATIVO("CORPORATIVO"),
        ACADEMICO("ACADÊMICO"),
        CULTURAL("CULTURAL"),
        ENTRETENIMENTO("ENTRETENIMENTO"),
        RELIGIOSOS("RELIGIOSOS"),
        ESPORTIVOS("ESPORTIVOS"),
        FEIRA("FEIRA"),
        CONGRESSO("CONGRESSO"),
        OFICINA("OFICINA"),
        CURSO("CURSO"),
        TREINAMENTO("TREINAMENTO"),
        AULA("AULA"),
        SEMINARIO("SEMINÁRIO"),
        PALESTRA("PALESTRA"),
        SHOW("SHOW"),
        FESTIVAL("FESTIVAL"),
        EXPOSICAO("EXPOSIÇÃO"),
        RETIRO("RETIRO"),
        CULTO("CULTO"),
        CELEBRACAO("CELEBRAÇÃO"),
        CAMPEONATO("CAMPEONATO"),
        CORRIDA("CORRIDA");

        private final String dbValue;

        TipoEvento(String dbValue) {
            this.dbValue = dbValue;
        }

        public String getDbValue() {
            return dbValue;
        }

        public static TipoEvento fromDbValue(String dbValue) {
            for (TipoEvento te : values()) {
                if (te.dbValue.equals(dbValue)) {
                    return te;
                }
            }
            throw new IllegalArgumentException("Valor desconhecido: " + dbValue);
        }
    }

    public enum Modalidade {
        PRESENCIAL, REMOTO, HIBRIDO
    }

    // Converter para TipoEvento (lida com acentos)
    @Converter(autoApply = true)
    public static class TipoEventoConverter implements AttributeConverter<TipoEvento, String> {
        @Override
        public String convertToDatabaseColumn(TipoEvento attribute) {
            return attribute == null ? null : attribute.getDbValue();
        }

        @Override
        public TipoEvento convertToEntityAttribute(String dbData) {
            return dbData == null ? null : TipoEvento.fromDbValue(dbData);
        }
    }

    public int getIngressosDisponiveis() {
        long vendidosNaoCancelados = ingressos.stream()
                .filter(i -> !i.isCancelado())
                .count();
        return capacidadeMaxima - (int) vendidosNaoCancelados;
    }

    // Acesso imutável à lista
    public List<Ingresso> getIngressos() {
        return List.copyOf(ingressos);
    }

    public void setIngressos(List<Ingresso> ingressos) {
        this.ingressos.clear();
        if (ingressos != null) {
            this.ingressos.addAll(ingressos);
            // Garante o relacionamento bidirecional
            this.ingressos.forEach(i -> i.setEvento(this));
        }
    }

    // Regras de negócio
    public void ativar() {
        if (this.isEventoAtivo()) {
            throw new EventoJaAtivoException("Evento já está ativo.");
        }
        setEventoAtivo(true);
        // ingressosDisponiveis agora é calculado dinamicamente
    }

    public List<Ingresso> desativar() {
        if (!this.isEventoAtivo()) {
            throw new EventoJaInativoException("Evento já está inativo.");
        }
        List<Ingresso> cancelados = cancelarIngressos();
        setEventoAtivo(false);
        return cancelados;
    }

    private List<Ingresso> cancelarIngressos() {
        List<Ingresso> cancelados = new ArrayList<>();
        for (Ingresso ingresso : ingressos) {
            if (!ingresso.isCancelado()) {
                ingresso.cancelar();
                cancelados.add(ingresso);
            }
        }
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
        // Não é mais necessário incrementar contador manual
    }
}