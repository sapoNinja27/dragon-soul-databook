package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import Entidades.Ficha;
import Entidades.Personagem;
import JObjects.Botao;
import JObjects.Tabela;
import Main.Main;
import Services.FichaService;
import Services.PersonagemService;

public class Buscar {
	Botao edit = new Botao(550, 210, 97, 20, "Editar", Color.white, 2, 15);
	Botao excluir = new Botao(550, 240, 97, 20, "Excluir", Color.white, 2, 15);
	Botao voltar = new Botao(550, 270, 97, 20, "Voltar", Color.white, 2, 15);
	Tabela tab=new Tabela(50,100,150,15);
	private PersonagemService personagemService= new PersonagemService();
	FichaService fichaService=new FichaService();
	public int id;
	public Buscar() {
		List<Ficha> fichas= fichaService.listar();
		for (int i = 0; i < fichas.size(); i++) {
			Personagem personagem= personagemService.buscar(fichas.get(i).getPersonagem().getId());
			tab.setTexto(personagem.getNome(),fichas.get(i).getId());
		}
		Main.botoes.add(excluir);
		Main.botoes.add(edit);
		Main.botoes.add(voltar);
		
	}

	public void tick() {
		
		if (voltar.clicou()) {
			Main.menu.state = "Menu";
			Main.menu.resetar();
			Main.botoes.remove(voltar);
		}
		
		if (edit.clicou()) {
			Main.menu.state = "Editar";
			Main.menu.editar= new Editar(tab.getSelecionado());
			Main.menu.resetar();
			Main.botoes.remove(edit);
		}
		if (excluir.clicou()) {
			fichaService.deletar(tab.getSelecionado());
			Main.menu.resetar();
			Main.botoes.remove(excluir);
		}
	}

	public void render(Graphics g) {
		tab.render(g);
		tick();
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("Pesquisar ", 50, 40);
		edit.render(g);
		excluir.render(g);
		voltar.render(g);
	}
}
