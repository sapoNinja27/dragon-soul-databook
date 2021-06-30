package ConnectionFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection(){
        String schema = "fichas";
        try{
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            return DriverManager.getConnection("jdbc:mysql://localhost/"+schema+"?useTimezone=true&serverTimezone=UTC","root","");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
	/*
	 * Criação das tabelas no db
	 * DROP SCHEMA `fichas`; CREATE SCHEMA IF NOT EXISTS `fichas`; USE `fichas` ; CREATE
	 * TABLE IF NOT EXISTS `fichas`.`jogo` ( `id` INT NOT NULL AUTO_INCREMENT,
	 * `nome` VARCHAR(45) NULL DEFAULT NULL, `genero` VARCHAR(45) NULL DEFAULT NULL,
	 * PRIMARY KEY (`id`)); CREATE TABLE IF NOT EXISTS `fichas`.`personagem` ( `id`
	 * INT NOT NULL AUTO_INCREMENT, `nome` VARCHAR(45) NULL DEFAULT NULL, `nivel`
	 * INT NULL DEFAULT NULL, `descricao` VARCHAR(45) NULL DEFAULT NULL, PRIMARY KEY
	 * (`id`)); CREATE TABLE IF NOT EXISTS `fichas`.`usuario` ( `id` INT NOT NULL
	 * AUTO_INCREMENT, `login` VARCHAR(45) NULL DEFAULT NULL, `senha` VARCHAR(45)
	 * NULL DEFAULT NULL, PRIMARY KEY (`id`)); INSERT INTO
	 * fichas.usuario(login,senha) VALUES ('jp','1234'); CREATE TABLE IF NOT EXISTS
	 * `fichas`.`ficha` ( `id` INT NOT NULL AUTO_INCREMENT, `personagem_id` INT NOT
	 * NULL, `jogo_id` INT NOT NULL, `usuario_id` INT NOT NULL, PRIMARY KEY (`id`),
	 * INDEX `jogo_idx` (`jogo_id` ASC) VISIBLE, INDEX `personagem_idx`
	 * (`personagem_id` ASC) VISIBLE, INDEX `usuario_idx` (`usuario_id` ASC)
	 * VISIBLE, CONSTRAINT `jogo` FOREIGN KEY (`jogo_id`) REFERENCES `fichas`.`jogo`
	 * (`id`), CONSTRAINT `personagem` FOREIGN KEY (`personagem_id`) REFERENCES
	 * `fichas`.`personagem` (`id`), CONSTRAINT `usuario` FOREIGN KEY (`usuario_id`)
	 * REFERENCES `fichas`.`usuario` (`id`))
	 */