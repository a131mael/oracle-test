package main;

import main.config.Config;
import main.oracle.Corte;
import main.oracle.Paginacao;
import main.oracle.SQL;
import main.oracle.UI;

public class TesteJoin {
	
	static int[] containers = { 1, 2 };
	
	public static void main(String[] args) {
		
//		SQL join;
//		SQL a = new SQL("UI_A", Corte.DISTINCT, Paginacao.ROW_NUMBER);
//		SQL b = new SQL("UI_B", Corte.EXISTS, Paginacao.ROWNUM);
//		SQL c = new SQL("UI_C", Corte.GROUP_BY, Paginacao.ROWNUM);
		
//		UI join;
//		UI a = new UI("UI_A", Corte.DISTINCT, Paginacao.ROWNUM);
//		UI b = new UI("UI_B", Corte.DISTINCT, Paginacao.ROWNUM);
//		UI c = new UI("UI_C", Corte.SEM_CORTE, Paginacao.ROWNUM);
//		
		//System.out.println(a.count(null));
		//System.out.println(a.pagina(1, 100, null, null));
		//System.out.println(b.count(null));
		//System.out.println(b.pagina(1, 100, null, null));
		
//		join = a.join(b, "UI_B.SK_UI_B = UI_A.NUMERO_1");
//		join = a.join(b, "UI_B.SK_UI_B = UI_A.NUMERO_1").join(c, "UI_C.SK_UI_C = UI_B.NUMERO_2");
//		
//		System.out.println();
//		System.out.println("----- JOIN -----");
		//System.out.println(join.count(null));
		//System.out.println(join.pagina(1, 100, null, null));
		
//		System.out.println("CONT(*):");
//		System.out.println(join.count(containers, null));
//		System.out.println();
//		System.out.println("Paginação:");
		//System.out.println(join.pagina(containers, Config.TAMANHO_PAGINA, 1, null, null));
	}

}
