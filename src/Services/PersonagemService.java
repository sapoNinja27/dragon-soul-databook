package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ConnectionFactory.ConnectionFactory;
import Entidades.Personagem;

public class PersonagemService {

	public Integer inserir(Personagem obj) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(
					"INSERT INTO personagem (nome,nivel,descricao) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, obj.getNome());
			pstmt.setInt(2, obj.getNivel());
			pstmt.setString(3, obj.getDestricao());
			pstmt.execute();
			final ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				final int lastId = rs.getInt(1);
				return lastId;
			}
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Algum erro ocorreu.");
			e.printStackTrace();
		}
		return 0;
	}

	public Personagem buscar(Integer id) {
		List<Personagem> lista = listar();
		Personagem obj = new Personagem(null, null, null);
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId() == id) {
				obj = lista.get(i);
			}
		}
		return obj;
	}

	public void alterar(Personagem obj) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("UPDATE personagem set nome=?,nivel=?,descricao=? where id=?");
			pstmt.setString(1, obj.getNome());
			pstmt.setInt(2, obj.getNivel());
			pstmt.setString(3, obj.getDestricao());
			pstmt.setInt(4, obj.getId());
			pstmt.execute();
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Algum erro ocorreu.");
			e.printStackTrace();
		}
	}

	public void deletar(Personagem obj) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();

			PreparedStatement pstmt = connection.prepareStatement("DELETE FROM personagem WHERE id=?");
			pstmt.setInt(1, obj.getId());
			pstmt.execute();
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Algum erro ocorreu.");
			e.printStackTrace();
		}
	}

	public List<Personagem> listar() {

		Connection connection = null;
		List<Personagem> lista = new ArrayList<>();

		try {
			connection = ConnectionFactory.getConnection();
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM personagem");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Personagem obj = new Personagem(null, null, null);
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
				obj.setNivel(rs.getInt("nivel"));
				obj.setDestricao(rs.getString("descricao"));
				lista.add(obj);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return lista;
	}

}
