import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PedidoDTO;
import mapper.MapperPedido;

public class PedidoDAOJDBC {


    public PedidoDAOJDBC() {
       
    }

    public List<PedidoDTO> listarPedidos() throws SQLException {
        List<PedidoDTO> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";

        try (Connection conn = ConexaoJDBC.getInstancia().getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
        	 ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                PedidoDTO pedido = mapper.toDTO(resultSet);
                pedidos.add(pedido);
            }
        }

        return pedidos;
    }

    public PedidoDTO buscarPedidoPorId(PedidoDTO pedido) throws SQLException {
        String sql = "SELECT * FROM Pedido WHERE id = ?";

        try (Connection conn = ConexaoJDBC.getInstancia().getConnection();
			PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pedido.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                pedido = mapper.toDTO(resultSet);
            } else {
                throw new SQLException("Pedido não encontrado.");
            }
        }

        return pedido;
    }
}