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
import Entidades.Jogo;

public class JogoService {

	public Integer inserir(Jogo obj) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO jogo (nome,genero) VALUES (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, obj.getNome());
			pstmt.setString(2, obj.getGenero());
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

	public Jogo buscar(Integer id) {
		List<Jogo> lista = listar();
		Jogo obj = new Jogo();
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId() == id) {
				obj = lista.get(i);
			}
		}
		return obj;
	}

	public void alterar(Jogo obj) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			PreparedStatement pstmt = connection.prepareStatement("UPDATE jogo set nome=?,genero=? where id=?");
			pstmt.setString(1, obj.getNome());
			pstmt.setString(2, obj.getGenero());
			pstmt.setInt(3, obj.getId());
			pstmt.execute();
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Algum erro ocorreu.");
			e.printStackTrace();
		}
	}

	public void deletar(Jogo obj) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();

			PreparedStatement pstmt = connection.prepareStatement("DELETE FROM jogo WHERE id=?");
			pstmt.setInt(1, obj.getId());
			pstmt.execute();
			JOptionPane.showMessageDialog(null, "Excluido");
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Algum erro ocorreu.");
			e.printStackTrace();
		}
	}

	public List<Jogo> listar() {
		Connection connection = null;
		List<Jogo> lista = new ArrayList<>();
		try {
			connection = ConnectionFactory.getConnection();
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM jogo");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Jogo obj = new Jogo();
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
				obj.setGenero(rs.getString("genero"));
				lista.add(obj);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return lista;
	}

}
