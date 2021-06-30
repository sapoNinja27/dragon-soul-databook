package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Entidades.Ficha;
import Entidades.Jogo;
import Entidades.Personagem;
import JObjects.Botao;
import JObjects.CampoDeTexto;
import Main.Main;
import Services.FichaService;
import Services.JogoService;
import Services.PersonagemService;

public class Editar {
	private JogoService jogoService = new JogoService();
	private PersonagemService personagemService = new PersonagemService();
	FichaService fichaService = new FichaService();

	Botao adicionar = new Botao(550, 240, 97, 20, "Salvar", Color.white, 2, 15);
	Botao voltar = new Botao(550, 270, 97, 20, "Voltar", Color.white, 2, 15);
	Ficha ficha = new Ficha(null, null, null);
	CampoDeTexto jogo;
	CampoDeTexto genero;
	CampoDeTexto nome;
	CampoDeTexto nivel;
	CampoDeTexto descricao;

	public Editar(Integer id) {
		ficha = fichaService.buscar(id);
		jogo = new CampoDeTexto(450, 100, 75, 15, ficha.getJogo().getNome());
		genero = new CampoDeTexto(450 + 80, 100, 75, 15, ficha.getJogo().getGenero());
		nome = new CampoDeTexto(100, 100, 150, 15, ficha.getPersonagem().getNome());
		nivel = new CampoDeTexto(100, 125, 150, 15, "" + ficha.getPersonagem().getNivel());
		descricao = new CampoDeTexto(100, 250, 300, 50, ficha.getPersonagem().getDestricao());
		Main.campos.add(jogo);
		Main.campos.add(genero);
		Main.campos.add(nome);
		Main.campos.add(nivel);
		Main.campos.add(descricao);

		Main.botoes.add(adicionar);
		Main.botoes.add(voltar);
	}

	public void tick() {
		if (voltar.clicou()) {
			Main.menu.state = "Menu";
			Main.menu.resetar();
			Main.botoes.remove(voltar);
		}
		if (adicionar.clicou()) {
			Personagem personagem = new Personagem(nome.getTexto(), Integer.valueOf(nivel.getTexto()),
					descricao.getTexto());
			personagem.setId(ficha.getPersonagem().getId());
			personagemService.alterar(personagem);
			Jogo jogo = new Jogo(this.jogo.getTexto(), genero.getTexto());
			jogo.setId(ficha.getJogo().getId());
			jogoService.alterar(jogo);

			ficha.setJogo(jogo);
			ficha.setPersonagem(personagem);
			fichaService.alterar(ficha);
			Main.menu.state = "Menu";
			Main.menu.resetar();
			Main.botoes.remove(adicionar);
		}
	}

	public void render(Graphics g) {
		tick();

		jogo.render(g);
		genero.render(g);
		nome.render(g);
		nivel.render(g);
		descricao.render(g);

		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("Editar ", 50, 40);
		adicionar.render(g);
		voltar.render(g);
	}
}
