package model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.Normalizer;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.LocalizacaoDTO;
import mapper.MapperLocalizacao;

public class BuscaCoordenadas {
	// Cria a localização da cidade informada
    public LocalizacaoDTO buscaLocalizacaoViaAPI(LocalizacaoDTO localizacaoCidade) throws Exception {

      String cidade = localizacaoCidade.getCidade();
      // Remove acentos, converte para minúsculo e troca espaços por hífens
      String cidadeFormatada = removerAcentos(cidade).toLowerCase().replaceAll(" ", "-");

      String urlParaChamada =  "https://servicodados.ibge.gov.br/api/v1/localidades/municipios/"+cidadeFormatada;

      String dados = buscarDadosViaAPI(urlParaChamada);
      
      //Transforma a string em um Json
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode rootNode = objectMapper.readTree(dados);
      
      //Procura atributo correspondente no Json
      String id = rootNode.findValue("id").asText();
      String nomeDaCidade = rootNode.findValue("nome").asText();
      String estado = rootNode.findValue("UF").findValue("nome").asText();

      urlParaChamada = "https://servicodados.ibge.gov.br/api/v3/malhas/municipios/"+id+"/metadados";
      dados = buscarDadosViaAPI(urlParaChamada);

      // Atualiza o Json com os dados da nova chamada
      rootNode = objectMapper.readTree(dados);

      double longitude = rootNode.get(0).findValue("centroide").findValue("longitude").asDouble();
      double latitude = rootNode.get(0).findValue("centroide").findValue("latitude").asDouble();
      
      Localizacao localizacao = new Localizacao(id, estado, nomeDaCidade, latitude, longitude);
      MapperLocalizacao mapper = new MapperLocalizacao();
      LocalizacaoDTO dto = mapper.toDTO(localizacao);
      
      return dto;
    }
    
    public String buscarDadosViaAPI(String url) throws IOException, InterruptedException{

      HttpRequest request = HttpRequest
      .newBuilder(URI.create(url))
      .build();
      HttpClient client = HttpClient.newHttpClient();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
  
      return response.body();
      
    }

    public String removerAcentos(String texto){
      String normalizer = Normalizer.normalize(texto, Normalizer.Form.NFD);
      Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
      return pattern.matcher(normalizer).replaceAll("");
    }
    
    public static void main(String[] args) {
    	BuscaCoordenadas c = new BuscaCoordenadas();
    	LocalizacaoDTO l = new LocalizacaoDTO();
    	l.setCidade("Camalaú");
    	try {
			l = c.buscaLocalizacaoViaAPI(l);
			System.out.print(l.getCidade()+" "+l.getEstado()+" "+l.getLatitude()+" "+l.getLongitude());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
