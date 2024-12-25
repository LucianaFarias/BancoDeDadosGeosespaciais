package model;

import jakarta.persistence.*;

@Entity
@Table(name = "transferencia")
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "filial_origem")
    private Filial origem;

    @ManyToOne
    @JoinColumn(name = "filial_destino")
    private Filial destino;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private boolean concluida;

    @Column(nullable = false)
    private boolean cancelada;

    public Transferencia() {}

    public Transferencia(int id, Produto produto, Filial origem, Filial destino, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.origem = origem;
        this.destino = destino;
        this.quantidade = quantidade;
        this.concluida = false;
        this.cancelada = false;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Filial getOrigem() {
        return origem;
    }

    public void setOrigem(Filial origem) {
        this.origem = origem;
    }

    public Filial getDestino() {
        return destino;
    }

    public void setDestino(Filial destino) {
        this.destino = destino;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }
}