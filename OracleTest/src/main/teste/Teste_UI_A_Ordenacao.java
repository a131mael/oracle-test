package main.teste;

import main.oracle.UI;

public class Teste_UI_A_Ordenacao extends AbstractOracleTeste {
	
	public Teste_UI_A_Ordenacao () {
		this.ui = new UI("UI_A");
    	this.incluirContainerPublico = false;
    	this.where = null;
    	this.orderBy = "UI_A.DATA_INICIO DESC";
	}

}
