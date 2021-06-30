package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ConnectionFactory.ConnectionFactory;
import Entidades.Ficha;

public class FichaService {
	private JogoService jogoService = new JogoService();
	private UsuarioService usuarioService = new UsuarioService();
	private PersonagemService personagemService = new PersonagemService();

	public void inserir(Ficha obj) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("INSERT INTO ficha (personagem_id,jogo_id,usuario_id) VALUES (?,?,?)");
			pstmt.setInt(1, obj.getPersonagem().getId());
			pstmt.setInt(2, obj.getJogo().getId());
			pstmt.setInt(3, obj.getUsuario().getId());
			pstmt.execute();
			JOptionPane.showMessageDialog(null, "Enviado.");
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Algum erro ocorreu.");
			e.printStackTrace();
		}
	}

	public void alterar(Ficha obj) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			PreparedStatement pstmt = connection
					.prepareStatement("UPDATE ficha set personagem_id=?,jogo_id=?,usuario_id=? where id=?");
			pstmt.setInt(1, obj.getPersonagem().getId());
			pstmt.setInt(2, obj.getJogo().getId());
			pstmt.setInt(3, obj.getUsuario().getId());
			pstmt.setInt(4, obj.getId());
			pstmt.execute();
			JOptionPane.showMessageDialog(null, "Alterado.");
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Algum erro ocorreu.");
			e.printStackTrace();
		}
	}

	public Ficha buscar(Integer id) {
		List<Ficha> lista = listar();
		Ficha obj = new Ficha(null, null, null);
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId() == id) {
				obj = lista.get(i);
			}
		}
		return obj;
	}

	public void deletar(Integer id) {
		Ficha ficha = buscar(id);
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();

			PreparedStatement pstmt = connection.prepareStatement("DELETE FROM ficha WHERE id=?");
			pstmt.setInt(1, id);
			pstmt.execute();
			personagemService.deletar(ficha.getPersonagem());
			JOptionPane.showMessageDialog(null, "Excluido");
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Algum erro ocorreu.");
			e.printStackTrace();
		}
	}

	public List<Ficha> listar() {

		Connection connection = null;
		List<Ficha> lista = new ArrayList<>();

		try {
			connection = ConnectionFactory.getConnection();
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM ficha");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Ficha obj = new Ficha(null, null, null);
				obj.setId(rs.getInt("id"));
				obj.setJogo(jogoService.buscar(rs.getInt("jogo_id")));
				obj.setPersonagem(personagemService.buscar(rs.getInt("personagem_id")));
				obj.setUsuario(usuarioService.buscar(rs.getInt("usuario_id")));
				lista.add(obj);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return lista;
	}

}
