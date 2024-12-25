package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.FilialDTO;
import dto.LocalizacaoDTO;
import exception.FilialInvalidaException;

public class FilialDAOJDBC implements IFilialDao {

    @Override
    public void cadastrarFilial(FilialDTO dto) {
        String sql = "INSERT INTO filial (nome, latitude, longitude) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoJDBC.getInstancia().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dto.getNome());
            stmt.setDouble(2, dto.getEndereco().getLatitude());
            stmt.setDouble(3, dto.getEndereco().getLongitude());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar filial: " + e.getMessage(), e);
        }
    }

    @Override
    public List<FilialDTO> listarFiliais() {
        String sql = "SELECT * FROM filial";
        List<FilialDTO> filiais = new ArrayList<>();

        try (Connection conn = ConexaoJDBC.getInstancia().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FilialDTO filialDTO = new FilialDTO();
                filialDTO.setId(rs.getInt("id"));
                filialDTO.setNome(rs.getString("nome"));
                filialDTO.setEndereco(new LocalizacaoDTO());
                filialDTO.getEndereco().setLatitude(rs.getInt("latitude"));
                filialDTO.getEndereco().setLatitude(rs.getInt("longitude"));

                filiais.add(filialDTO);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar filiais: " + e.getMessage(), e);
        }

        return filiais;
    }

    @Override
    public FilialDTO buscarFilialPorId(FilialDTO dto) throws FilialInvalidaException {
        if (dto == null) {
            throw new FilialInvalidaException();
        }

        String sql = "SELECT * FROM filial WHERE id = ?";
        FilialDTO filialDTO = null;

        try (Connection conn = ConexaoJDBC.getInstancia().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dto.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                filialDTO = new FilialDTO();
                filialDTO.setId(rs.getInt("id"));
                filialDTO.setNome(rs.getString("nome"));
                filialDTO.setEndereco(new LocalizacaoDTO());
                filialDTO.getEndereco().setLatitude(rs.getDouble("latitude"));
                filialDTO.getEndereco().setLatitude(rs.getDouble("longitude"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar filial por ID: " + e.getMessage(), e);
        }

        return filialDTO;
    }
    
    public List<FilialDTO> buscarFiliaisProximas(FilialDTO filial) {
        String sql = "SELECT b.* "
        		+ "FROM filial a, filial b "
        		+ "WHERE a.id = ? AND b.id <> ?"
        		+ "ORDER BY Distance(a.endereco, b.endereco)";
        List<FilialDTO> filiais = new ArrayList<>();

        try (Connection conn = ConexaoJDBC.getInstancia().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, filial.getId());
        	stmt.setInt(2, filial.getId());
        	ResultSet rs = stmt.executeQuery();
        	
            while (rs.next()) {
                FilialDTO filialDTO = new FilialDTO();
                filialDTO.setId(rs.getInt("id"));
                filialDTO.setNome(rs.getString("nome"));
                filialDTO.setEndereco(new LocalizacaoDTO());
                filialDTO.getEndereco().setLatitude(rs.getInt("latitude"));
                filialDTO.getEndereco().setLatitude(rs.getInt("longitude"));

                filiais.add(filialDTO);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar filiais: " + e.getMessage(), e);
        }

        return filiais;
    }
    
    
}