package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import Entidades.Ficha;
import Entidades.Jogo;
import Entidades.Personagem;
import Entidades.Usuario;
import JObjects.Botao;
import JObjects.CampoDeTexto;
import JObjects.Tabela;
import Main.Main;
import Services.FichaService;
import Services.JogoService;
import Services.PersonagemService;

public class Cadastro {
	FichaService serv = new FichaService();
	Botao adicionar = new Botao(550, 240, 97, 20, "Adicionar", Color.white, 2, 15);
	Botao voltar = new Botao(550, 270, 97, 20, "Voltar", Color.white, 2, 15);

	CampoDeTexto jogo = new CampoDeTexto(450, 100, 75, 15, "Jogo");
	CampoDeTexto genero = new CampoDeTexto(450 + 80, 100, 75, 15, "Genero");

	CampoDeTexto nome = new CampoDeTexto(100, 100, 150, 15, "Nome");
	CampoDeTexto nivel = new CampoDeTexto(100, 125, 150, 15, "Nivel");
	CampoDeTexto descricao = new CampoDeTexto(100, 250, 300, 50, "Descricao");

	Tabela jogos = new Tabela(260, 100, 140, 15);
	Jogo selectedJogo = new Jogo();
	private JogoService jogoService = new JogoService();
	private PersonagemService personagemService = new PersonagemService();

	public Cadastro() {
		List<Jogo> jogos = jogoService.listar();
		for (int i = 0; i < jogos.size(); i++) {
			this.jogos.setTexto(jogos.get(i).getNome() + " " + jogos.get(i).getGenero(), i);
		}
		Main.campos.add(jogo);
		Main.campos.add(genero);
		Main.campos.add(nome);
		Main.campos.add(nivel);
		descricao.setBox(3);
		descricao.setSize(37 * 3);
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
			List<Jogo> jogos = jogoService.listar();
			if (jogos.size() > 0) {
				selectedJogo = jogos.get(this.jogos.getSelecionado());
			}
			Personagem personagem = new Personagem(nome.getTexto(), Integer.valueOf(nivel.getTexto()),
					descricao.getTexto());
			personagem.setId(personagemService.inserir(personagem));
			Usuario user = Main.usuario;
			Jogo jogo = new Jogo();
			if (this.jogo.getTexto() != "Jogo" && genero.getTexto() != "Genero") {
				jogo = new Jogo(this.jogo.getTexto(), genero.getTexto());
				jogo.setId(jogoService.inserir(jogo));
			} else {
				jogo = selectedJogo;
			}

			Ficha ficha = new Ficha(user, personagem, jogo);
			serv.inserir(ficha);
			Main.menu.state = "Menu";
			Main.menu.resetar();
			Main.botoes.remove(adicionar);
		}
	}

	public void render(Graphics g) {
		tick();
		jogos.render(g);
		jogo.render(g);
		genero.render(g);
		nome.render(g);
		nivel.render(g);
		descricao.render(g);

		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 12));
		g.drawString("Novo Jogo ", 450, 90);

		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("Cadastrar ", 50, 40);
		adicionar.render(g);
		voltar.render(g);
	}
}
