package dto;

public class FilialDTO {
	private int id;
	private String nome;
    private LocalizaçaoDTO endereco;	    

public FilialDTO(int id, String nome, String cidade, LocalizaçaoDTO endereco) {
	        this.id = id;
	        this.nome = nome;
	        this.endereco=endereco;
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

		public LocalizaçaoDTO getEndereco() {
			return endereco;
		}

		public void setEndereco(LocalizaçaoDTO endereco) {
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
