package main.teste;

import main.oracle.UI;

public class Teste_UI_A_Filtro_Ordenacao extends AbstractOracleTeste {
	
	public Teste_UI_A_Filtro_Ordenacao () {
		this.ui = new UI("UI_A");
    	this.incluirContainerPublico = false;
    	this.where = "UI_A.SK_DATA BETWEEN 20141001 AND 20141031";
    	this.orderBy = "UI_A.SK_DATA DESC";
	}

}
