package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

import dto.LocalizacaoDTO;
import main.JsonNode;
import main.ObjectMapper;

public class BuscaCoordenadas {
	// Cria a localização da cidade informada
    public LocalizacaoDTO buscaLocalizacaoViaAPI(LocalizacaoDTO localizacaoCidade) throws Exception {

      String cidade = localizacaoCidade.getCidade();
      // Remove acentos, converte para minúsculo e troca espaços por hífens
      String cidadeFormatada = removerAcentos(cidade).toLowerCase().replaceAll(" ", "-");

      String urlParaChamada =  "https://servicodados.ibge.gov.br/api/v1/localidades/municipios/"+cidadeFormatada;

      String dados = buscarDadosViaAPI(urlParaChamada);
      
      String id = extrairId(dados);
      String nomeDaCidade = extrairCidade(dados);
      String estado = extrairEstado(dados);

      urlParaChamada = "https://servicodados.ibge.gov.br/api/v3/malhas/municipios/"+id+"/metadados";
      dados = buscarDadosViaAPI(urlParaChamada);

      // Procura a parte do texto que começa com a palavra longitude e termina com uma vírgula
      double longitude = extrairCoordenada("longitude.*?,", dados);

      // Procura a parte do texto que começa com a palavra latitude e termina com uma vírgula
      double latitude = extrairCoordenada("latitude.*?,", dados);
      
      Localizacao localizacao = new Localizacao(id, estado, nomeDaCidade, latitude, longitude);
      LocalizacaoDTO dto = new LocalizacaoDTO(
    		  localizacao.getId(),
				localizacao.getEstado(), 
				localizacao.getCidade(), 
				localizacao.getLatitude(), 
				localizacao.getLongitude(),
				localizacao.getPonto());
      
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

    // Procurar somente o nome da cidade no texto
    public String extrairCidade(String dados){
      
      String cidadeEncontrada = ""; // Retorna um texto vazio se a cidade não for encontrada

      // Cria um padrão para procurar a parte do texto que começa com "nome" e termina com uma vírgula
      String regex = "nome.*?,";
      cidadeEncontrada = extrairTexto(regex, dados);

      // Remove a palavra nome e alguns caracteres especiais
      cidadeEncontrada = cidadeEncontrada.replaceAll("nome|[\":,]", "");
      
      return cidadeEncontrada; 
    }

    // Procurar somente o nome do estado no texto
    public String extrairEstado(String dados){

      String estado = ""; // Retorna um texto vazio se o estado não for encontrada

      // Cria um padrão para procurar a parte do texto que começa com UF e termina com regiao
      String regex = "UF.*?regiao";
      estado = extrairTexto(regex, dados);
      
      // Extrai do texto a parte que começa com nome e termina com vígula
      regex = "nome.*?,";

      // Remove a palavra nome e alguns caracteres especiais
      estado = extrairTexto(regex, estado).replaceAll("nome|[\":,]", "");
       
      return estado; 
      
    }

    public double extrairCoordenada(String regexCoordenada, String dados){
      // Encontra a parte do texto que corresponder ao padrão do regex e remove tudo que não for número ou sinal de menos
      String coordenada = extrairTexto(regexCoordenada, dados).replaceAll("[^0-9-]", "");
      // Retorna a coordenada encontrada convertida para double
      return Double.parseDouble(coordenada);
    }

    public String extrairId(String dados){
      String regexId = "id.*?,";
      return extrairTexto(regexId, dados).replaceAll("[^0-9]", "");
    }

    public String removerAcentos(String texto){
      String normalizer = Normalizer.normalize(texto, Normalizer.Form.NFD);
      Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
      return pattern.matcher(normalizer).replaceAll("");
    }

    //Procura um padrão de texto específico (regex) para extrair somente a parte pedida
    public String extrairTexto(String regex, String dados){
      Pattern pattern = Pattern.compile(regex); // Cria um padrão de texto que será buscado 
      Matcher matcher = pattern.matcher(dados); // Procura as partes do texto que batem com o padrão 

      String textoEncontrado = ""; // Se o texto não for encontrado será retornado ""

      //Se alguma ocorrência do padrão for encontrada no texto
      if (matcher.find()){
        textoEncontrado = matcher.group(); //Primeira ocorrência encontrada
      }
      return textoEncontrado;
    }
    
    public void consumoAPIemJson() {
    	try {
            // Caminho para o arquivo GeoJSON
            File geoJsonFile = new File("C:\\Users\\Carina\\Documents\\ADS\\bd2\\banco-geoespacial\\projeto\\BancoDeDadosGeosespaciais\\dados-json.txt");
            FileInputStream fis = new FileInputStream(geoJsonFile);
            // Ler o GeoJSON como um nó JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(fis);

            // Exibir o conteúdo
            //System.out.println("Tipo: " + rootNode.findParents("geometry"));
            //System.out.println("Features: " + rootNode.get("features"));

            GeometryFactory geometryFactory = new GeometryFactory();
            List<Coordinate> coord = new ArrayList<>();
            List<LineString> linhas = new ArrayList<>();
            // Iterar pelas features
            
            //GeometryDTO dto = objectMapper.readValue(rootNode.findPath("geometry").binaryValue(), GeometryDTO.class);
            	
            for(JsonNode coordenadas: rootNode.findValues("geometry")){
            	for(JsonNode node: coordenadas) {
	            	double latitude = node.get("lat").asDouble();
	            	double longitude = node.get("lon").asDouble();
	            	coord.add(new Coordinate(longitude, latitude));
            	
            	}
            		
            	linhas.add(geometryFactory.createLineString(coord.toArray(new Coordinate[coord.size()])));
            }
    
            	
            	
            //}
            
            //geometryFactory.createMultiLineString(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    }
}
