package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ConnectionFactory.ConnectionFactory;
import Entidades.Usuario;

public class UsuarioService {

	public void inserir(Usuario obj) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO usuario (login,senha) VALUES (?,?)");
			pstmt.setString(1, obj.getLogin());
			pstmt.setString(2, obj.getSenha());
			pstmt.execute();
			JOptionPane.showMessageDialog(null, "Enviado.");
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Algum erro ocorreu.");
			e.printStackTrace();
		}
	}

	public Usuario buscar(Integer id) {
		List<Usuario> lista = listar();
		Usuario user = new Usuario("", "");
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId() == id) {
				user = lista.get(i);
			}
		}
		return user;
	}

	public Usuario buscar(String login) {
		List<Usuario> lista = listar();
		Usuario user= new Usuario(login,"");
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getLogin().equals(user.getLogin())) {
				user=lista.get(i);
			}
		}
		return user;
	}

	public List<Usuario> listar() {

		Connection connection = null;
		List<Usuario> lista = new ArrayList<>();

		try {
			connection = ConnectionFactory.getConnection();
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM usuario");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Usuario obj = new Usuario(null, null);
				obj.setId(rs.getInt("id"));
				obj.setLogin(rs.getString("login"));
				obj.setSenha(rs.getString("senha"));
				lista.add(obj);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return lista;
	}

}
