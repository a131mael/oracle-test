package main.teste;

import main.oracle.UI;

public class Teste_UI_A_Simples extends AbstractOracleTeste {
	
	public Teste_UI_A_Simples () {
		this.ui = new UI("UI_A");
    	this.incluirContainerPublico = false;
    	this.where = null;
    	this.orderBy = null;
	}

}
