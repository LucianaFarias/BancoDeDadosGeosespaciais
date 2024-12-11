package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilialDAO {
    private final String url = "jdbc:sqlite:loja.db"; // URL do banco SQLite

    // Método para adicionar uma nova filial
    public void adicionarFilial(Filial filial) {
        String sql = "INSERT INTO Filial(nome, cidade, latitude, longitude) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, filial.getNome());
            pstmt.setString(2, filial.getCidade());
            pstmt.setDouble(3, filial.getLatitude());
            pstmt.setDouble(4, filial.getLongitude());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar filial: " + e.getMessage());
        }
    }

    // Método para listar todas as filiais
    public List<Filial> listarFiliais() {
        List<Filial> filiais = new ArrayList<>();
        String sql = "SELECT * FROM Filial";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Filial filial = new Filial(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cidade"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")
                );
                filiais.add(filial);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar filiais: " + e.getMessage());
        }
        return filiais;
    }
}
