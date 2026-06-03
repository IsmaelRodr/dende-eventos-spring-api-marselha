package br.com.dendesofthouse.dendeeventos.Models;

import br.com.dendesofthouse.dendeeventos.exceptions.ingresso.IngressoJaCanceladoException;
import jakarta.persistence.*;
import lombok.*;

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
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "evento_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_ingresso_evento"))
    private Evento evento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private StatusIngresso status = StatusIngresso.ACEITO;

    @Column(name = "email", length = 255, nullable = false)
    @ToString.Include
    private String email;

    @Column(name = "valor_pago", nullable = false, precision = 10, scale = 2)
    @ToString.Include
    private double valorPago;

    @Column(name = "valor_estornado", nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private double valorEstornado = 0.0;

    @Column(name = "data_compra", nullable = false, updatable = false)
    @ToString.Include
    private LocalDateTime dataCompra;

    public enum StatusIngresso {
        ACEITO, CANCELADO
    }

    // Construtor conveniente para criação de novos ingressos (sem builder)
    public Ingresso(Usuario usuario, Evento evento, double valorPago, String email) {
        this.usuario = Objects.requireNonNull(usuario);
        this.evento = Objects.requireNonNull(evento);
        this.valorPago = valorPago;
        this.email = email;
        this.status = StatusIngresso.ACEITO;
        this.dataCompra = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (dataCompra == null) {
            dataCompra = LocalDateTime.now();
        }
    }

    public boolean isCancelado() {
        return status == StatusIngresso.CANCELADO;
    }

    public void cancelar() {
        if (this.status == StatusIngresso.CANCELADO) {
            throw new IngressoJaCanceladoException("Ingresso já cancelado.");
        }
        this.status = StatusIngresso.CANCELADO;
        if (this.evento.isEventoEstorno()) {
            this.valorEstornado = this.valorPago * (1 - evento.getTaxaCancelamento() / 100.0);
        } else {
            this.valorEstornado = 0.0;
        }
    }

    public boolean isAtivoParaListagem() {
        return !this.isCancelado()
                && this.evento.isEventoAtivo()
                && this.evento.getDataInicio().isAfter(LocalDateTime.now());
    }
}