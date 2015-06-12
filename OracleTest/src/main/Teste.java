package main;

import main.config.Config;
import main.teste.AbstractOracleTeste;
import main.teste.Teste_UI_A_Filtro;
import main.teste.Teste_UI_A_Filtro_Ordenacao;
import main.teste.Teste_UI_A_Ordenacao;
import main.teste.Teste_UI_A_Simples;

public class Teste {

    static AbstractOracleTeste teste;

    public static void main(String[] args) {

        teste = new Teste_UI_A_Simples();
        teste.teste();
        teste = new Teste_UI_A_Filtro();
        teste.teste();
        teste = new Teste_UI_A_Filtro_Ordenacao();
        teste.teste();
        teste = new Teste_UI_A_Ordenacao();
        teste.teste();
//        teste.testeCountVertical(Config.LIMITE_COUNT);
//        teste.testeCountVertical();
//        teste.testePaginacaoVertical();
        
        
    }
}
