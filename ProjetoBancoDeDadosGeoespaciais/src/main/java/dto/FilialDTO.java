package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilialDTO {
	private int id;
	private String nome;
	private LocalizacaoDTO endereco;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalizacaoDTO getEndereco() {
		return endereco;
	}
	public void setEndereco(LocalizacaoDTO endereco) {
		this.endereco = endereco;
	}

}
