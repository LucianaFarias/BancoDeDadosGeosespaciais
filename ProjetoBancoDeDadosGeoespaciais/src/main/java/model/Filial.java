package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dto.EstoqueDTO;
import dto.FilialDTO;
import dto.TransferenciaDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import mapper.MapperEstoque;

@Entity
@Table(name = "filial")
public class Filial implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@Column
    private String nome;

	@Embedded
    private Localizacao endereco;

    public Filial(int id, String nome, Localizacao endereco) {
        this.id = id;
        this.nome = nome;
        this.setEndereco(endereco);
    }

    public Filial() {

    }
    
    public List<TransferenciaDTO> gerarTranferencias(List<EstoqueDTO> estoquesTransferidos, FilialDTO destino){
		List<TransferenciaDTO> transferencias = new ArrayList<>();
		MapperEstoque mapperEstoque = new MapperEstoque();
		
		for(EstoqueDTO estoque: estoquesTransferidos) {
			Estoque entidadeEstoque = mapperEstoque.toEntity(estoque);
			TransferenciaDTO transferencia = entidadeEstoque.criarTransferencia(destino, estoque.getQuantidade());
			transferencias.add(transferencia);
		}
		return transferencias;
	}

	public int getId() {
    	return id; 
    }
    public String getNome() { 
    	return nome; 
    	}
    public void setNome(String nome) { 
    	this.nome = nome; 
    	}

	public Localizacao getEndereco() {
		return endereco;
	}

	public void setEndereco(Localizacao endereco) {
		this.endereco = endereco;
	}
	@Override
    public String toString() {
        return "Filial{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", endereço='" + endereco + '\'' +
                '}';
    }
}