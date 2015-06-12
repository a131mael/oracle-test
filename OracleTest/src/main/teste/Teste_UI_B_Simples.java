package main.teste;

import main.oracle.UI;

public class Teste_UI_B_Simples extends AbstractOracleTeste {
	
	public Teste_UI_B_Simples () {
		this.ui = new UI("UI_B");
    	this.incluirContainerPublico = false;
    	this.where = null;
    	this.orderBy = null;
	}

}
