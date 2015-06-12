package main.teste;

import main.oracle.UI;

public class Teste_Join_A_B_Simples extends AbstractOracleTeste {

	public Teste_Join_A_B_Simples () {
		this.ui = new UI("UI_A").join(new UI("UI_B"), "UI_B.SK_UI_B = UI_A.SK_UI_B");
    	this.incluirContainerPublico = false;
    	this.where = null;
    	this.orderBy = null;
	}
}
