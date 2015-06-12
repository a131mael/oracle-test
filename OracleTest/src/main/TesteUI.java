package main;

import main.config.Config;
import main.oracle.Corte;
import main.oracle.Paginacao;
import main.oracle.UI;

public class TesteUI {

    static UI ui;
    static int[] containers = { 1, 2 };

    /*
    public static void main(String[] args) {
        ui = new UI("UI_A", Corte.DISTINCT, Paginacao.ROWNUM);
        testeUI(ui, containers);

        ui = new UI("UI_A", Corte.GROUP_BY, Paginacao.ROWNUM);
        testeUI(ui, containers);

        ui = new UI("UI_A", Corte.EXISTS, Paginacao.ROWNUM);
        testeUI(ui, containers);

        ui = new UI("UI_A", Corte.IN, Paginacao.ROWNUM);
        testeUI(ui, containers);

        ui = new UI("UI_A", Corte.DISTINCT, Paginacao.ROW_NUMBER);
        testeUI(ui, containers);

        ui = new UI("UI_A", Corte.GROUP_BY, Paginacao.ROW_NUMBER);
        testeUI(ui, containers);

        ui = new UI("UI_A", Corte.EXISTS, Paginacao.ROW_NUMBER);
        testeUI(ui, containers);

        ui = new UI("UI_A", Corte.IN, Paginacao.ROW_NUMBER);
        testeUI(ui, containers);
    }
    
    public static void testeUI(UI ui, int[] containers) {
    	testeCount(ui, containers);
        testeCountComFiltros(ui, containers);
        testePaginacao(ui, containers);
        testePaginacaoComFiltros(ui, containers);
        testePaginacaoComOrdenacao(ui, containers);
        testePaginacaoComOrdenacaoEFiltros(ui, containers);
    }
    
    public static void testeCount(UI ui, int[] containers) {
    	imprimeCabecalho(ui, "COUNT(*)");
    	System.out.println(ui.count(containers, null));
        System.out.println();
    }

    public static void testeCountComFiltros(UI ui, int[] containers) {
    	String filtros = "UI_A.NUMERO_1 = 50";
    	
    	imprimeCabecalho(ui, "COUNT(*) com filtros");
        System.out.println(ui.count(containers, filtros));
        System.out.println();
    }
    
    public static void testePaginacao(UI ui, int[] containers) {
    	imprimeCabecalho(ui, "Paginação");
    	
        System.out.println(ui.pagina(containers, Config.TAMANHO_PAGINA, 1, null, null));
        System.out.println();
    }
    
    public static void testePaginacaoComOrdenacao(UI ui, int[] containers) {
    	String ordenacao = "UI_A.NUMERO_1 DESC";
    	
    	imprimeCabecalho(ui, "Paginação ordenação");
    	System.out.println(ui.pagina(containers, Config.TAMANHO_PAGINA, 1, ordenacao, null));
        System.out.println();
    }
    
    public static void testePaginacaoComFiltros(UI ui, int[] containers) {
    	String ordenacao = "";
    	String filtros = "UI_A.NUMERO_1 = 50";
    	
    	imprimeCabecalho(ui, "Paginação com filtros");
        System.out.println(ui.pagina(containers, Config.TAMANHO_PAGINA, 1, ordenacao, filtros));
        System.out.println();
    }
    
    public static void testePaginacaoComOrdenacaoEFiltros(UI ui, int[] containers) {
    	String ordenacao = "UI_A.NUMERO_1 DESC";
    	String filtros = "UI_A.NUMERO_1 = 50";
    	
    	imprimeCabecalho(ui, "Paginação com filtros e ordenação");
        System.out.println(ui.pagina(containers, Config.TAMANHO_PAGINA, 1, ordenacao, filtros));
        System.out.println();
    }
    
    private static void imprimeCabecalho(UI ui, String tipo) {
	    String pcorte = "";
	    String ppag = "";
	
	    switch (ui.corte()) {
	        case DISTINCT:
	            pcorte = "DISTINCT";
	            break;
	        case GROUP_BY:
	            pcorte = "GROUP BY";
	            break;
	        case EXISTS:
	            pcorte = "EXISTS";
	            break;
	        case IN:
	            pcorte = "IN";
	            break;
	    }
	
	    switch (ui.paginacao()) {
	        case ROWNUM:
	            ppag = "ROWNUM";
	            break;
	        case ROW_NUMBER:
	            ppag = "ROW_NUMBER()";
	            break;
	        default:
	            break;
	    }
	    
	    System.out.println("***** " + tipo + " " + ui.tabela + ": " + pcorte + " / " + ppag + " *****");
        System.out.println();
    }
    */
}
