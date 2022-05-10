/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package questao02;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author carol
 */
public class Fatorial {

    Scanner sc = new Scanner(System.in);
    BigInteger[] lista = new BigInteger[0];
    BigInteger resultado = BigInteger.ONE;
    int numeroEntrada = 0;
    int topo = 0;
    int numeroAtual = 1;
    int tamanhoListaBanco = 1;
    
//entrada do fatorial
    public void fatorial() throws SQLException {

        BigInteger mostrar_Resultado = BigInteger.ONE;

        while (true) {
            System.out.println("Escolha um número inteiro para ser fatorado "
                    + "\n" + "Digite -1 para mostrar todos do array "
                    + "\n" + "Digite  -2 para mandar pro banco "
                    + "\n" + "Digite  0 para sair");
            numeroEntrada = sc.nextInt();

            if (numeroEntrada >= lista.length) {
                // Copia toda a lista para um novo array, maior
                BigInteger[] novaLista = new BigInteger[numeroEntrada + 2];

                if (lista.length > 0) {   ///manda pro novo array o que ja tem
                    for (int i = 0; i < lista.length; i++) {
                        novaLista[i] = lista[i];
                    }
                }   // Substitui a lista original
                lista = novaLista;
            }
            if ((numeroEntrada < topo) && (numeroEntrada > 0)) {
                // Pega o numero e o resultado  do array 
                System.out.println("O numero ja esta no array = "
                        + numeroEntrada + " = " + lista[numeroEntrada]);
                mostrar_Resultado = lista[numeroEntrada];

            } else if (numeroEntrada >= topo) {
                //manda o numero a ser calculado para o array
                for (int i = topo; i <= numeroEntrada; i++) {

                    lista[i] = calcula_Fatorial(i);
                    mostrar_Resultado = lista[i];

                }
                System.out.println("O fatorial de  "
                        + numeroEntrada + " é = " + resultado
                        + " e o numero atual " + numeroAtual);
                topo = numeroEntrada + 1;

            }
            if (numeroEntrada == 0) {
                break;
            }
            if (numeroEntrada == -1) {
                for (int i = 0; i <= topo - 1; i++) {
                    System.out.println(i + " = " + lista[i]);
                }

            }
            if (numeroEntrada == -2) {
                envia_Banco();
            }
        }

    }
// calcula o fatorail

    BigInteger calcula_Fatorial (int n) {
        int i;
        for (i = numeroAtual; i <= n; i++) {
            resultado = resultado.multiply(BigInteger.valueOf(i));
        }
        numeroAtual = i;
        return resultado;
    }
// Manda para o banco

    public void envia_Banco() throws SQLException {

        ConectarBanco conexaoBanco = new ConectarBanco();
        CriarBanco criarBanco = new CriarBanco(conexaoBanco);
        criarBanco.criarTabela();

        conexaoBanco.conectar();
        ResultSet resultSet = null;
        Statement statement = null;

        String query = "SELECT * FROM fact; ";
        statement = conexaoBanco.criarStatement();

        resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            tamanhoListaBanco++;
        }

        if (tamanhoListaBanco >= lista.length) {
            System.out.println("Fatorial já existe no banco");
        }

        for (int i = tamanhoListaBanco; i <= topo - 1; i++) {

            //  System.out.println(i + " = " + lista[i]);
            Numero n = new Numero();
            n.setId(i);
            n.setFatorial(String.valueOf(lista[i]));

            String sqlInsert = "INSERT INTO fact("
                    + "id,"
                    + "fatorial"
                    + ")VALUES(?,?)"
                    + ";";
            PreparedStatement preparedStatement = conexaoBanco.criarPreparedStatement(sqlInsert);
            preparedStatement.setInt(1, n.getId());
            preparedStatement.setString(2, n.getFatorial());

            int resul = preparedStatement.executeUpdate();
        }

        conexaoBanco.desconectar();

    }

}
