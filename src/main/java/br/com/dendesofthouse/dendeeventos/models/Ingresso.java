package br.com.dendesofthouse.dendeeventos.models;

import br.com.dendesofthouse.dendeeventos.exceptions.ingresso.IngressoJaCanceladoException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ingresso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)

public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_ingresso_usuario"))
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "evento_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_ingresso_evento"))
    @JsonIgnore
    private Evento evento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private StatusIngresso status = StatusIngresso.ACEITO;

    @Column(name = "email", length = 255, nullable = false)
    @ToString.Include
    private String email;

    @Column(name = "valor_pago", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPago;

    @Column(name = "valor_estornado", nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal valorEstornado = BigDecimal.ZERO;


    @Column(name = "data_compra", nullable = false, updatable = false)
    @ToString.Include
    private LocalDateTime dataCompra;

    public enum StatusIngresso {
        ACEITO, CANCELADO
    }

    // Construtor sem id (recomendado para uso nos serviços)
    public Ingresso(Usuario usuario, Evento evento, BigDecimal valorPago, String email) {
        this.usuario = usuario;
        this.evento = evento;
        this.valorPago = valorPago;
        this.email = email;
        this.status = StatusIngresso.ACEITO;
        this.dataCompra = LocalDateTime.now();
        this.valorEstornado = BigDecimal.ZERO;
    }

    @PrePersist
    protected void onCreate() {
        if (dataCompra == null) dataCompra = LocalDateTime.now();
    }

    public boolean isCancelado() {
        return status == StatusIngresso.CANCELADO;
    }

    public void cancelar() {
        if (this.status == StatusIngresso.CANCELADO)
            throw new IngressoJaCanceladoException("Ingresso já cancelado.");
        this.status = StatusIngresso.CANCELADO;
        if (this.evento.isEventoEstorno()) {
            BigDecimal taxa = this.evento.getTaxaCancelamento();
            BigDecimal percentualRestante = BigDecimal.ONE.subtract(taxa.divide(new BigDecimal("100"),  4, RoundingMode.HALF_EVEN));
            this.valorEstornado = this.valorPago.multiply(percentualRestante);
        } else {
            this.valorEstornado = BigDecimal.ZERO;
        }
    }

    public boolean isAtivoParaListagem() {
        return !this.isCancelado()
                && this.evento.isEventoAtivo()
                && this.evento.getDataInicio().isAfter(LocalDateTime.now());
    }
}