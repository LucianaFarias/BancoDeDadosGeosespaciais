package dto;

public class TransferenciaDTO {
    private int id;
    private FilialDTO origem;
    private FilialDTO destino;
    private ProdutoDTO produto;
    private int quantidade;

    public TransferenciaDTO() {}

    public TransferenciaDTO(int id, FilialDTO origem, FilialDTO destino, ProdutoDTO produto, int quantidade) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public FilialDTO getOrigem() { return origem; }
    public void setOrigem(FilialDTO origem) { this.origem = origem; }
    public FilialDTO getDestino() { return destino; }
    public void setDestino(FilialDTO destino) { this.destino = destino; }
    public ProdutoDTO getProduto() { return produto; }
    public void setProduto(ProdutoDTO produto) { this.produto = produto; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}