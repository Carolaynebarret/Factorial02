/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package questao02;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author carol
 */
class CriarBanco {

    private final ConectarBanco conectarBanco;

    public CriarBanco(ConectarBanco Conectar_Banco) {
        this.conectarBanco = Conectar_Banco;
    }

    public void criarTabela() throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS fact"
                + "("
                + "id integer PRIMARY KEY,"
                + "fatorial text"
                + ");";
        //executando o sql de criar tabelas
        boolean conectou = false;

        try {
            conectou = this.conectarBanco.conectar();

            Statement stmt = this.conectarBanco.criarStatement();

            stmt.execute(sql);
            //  System.out.println("Tabela numero Criada!!!");

        } catch (SQLException e) {

        } finally {
            if (conectou) {
                this.conectarBanco.desconectar();
            }
        }
    }

}
