package main.teste;

import main.oracle.UI;

public class Teste_UI_A_Simples_Publica extends AbstractOracleTeste {

	public Teste_UI_A_Simples_Publica () {
		this.ui = new UI("UI_A");
    	this.incluirContainerPublico = true;
    	this.where = null;
    	this.orderBy = null;
	}
}
