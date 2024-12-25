package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;

public class ConexaoJDBC {

    private static ConexaoJDBC INSTANCIA;
    private Connection connection;

    private ConexaoJDBC() {
        try {
        	// Defina o caminho do seu banco SQLite
            String url = "jdbc:sqlite:caminho do arquivo";
           
            SQLiteConfig config = new SQLiteConfig();
            config.enableLoadExtension(true);

            connection = DriverManager.getConnection(url,config.toProperties());

            // create a database connection
            Statement stmt = connection.createStatement();
            stmt.setQueryTimeout(30);

            // loading SpatiaLite
            stmt.execute("SELECT load_extension('mod_spatialite')");

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public static ConexaoJDBC getInstancia() throws SQLException {
        if (INSTANCIA == null) {
            INSTANCIA = new ConexaoJDBC();
        }else if(INSTANCIA.connection.isClosed()) {
            INSTANCIA = new ConexaoJDBC();
        }
        return INSTANCIA;
    }

    public void fechar() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

	
	public ConexaoJDBC abrir() {
		if(ConexaoJPA.getInstancia().getFactory().isOpen()) {
			ConexaoJPA.getInstancia().fechar();
		}
		return INSTANCIA;
	}

}