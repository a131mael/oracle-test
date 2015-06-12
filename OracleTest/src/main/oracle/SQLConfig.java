package main.oracle;



public class SQLConfig  {
	
	public final Corte corte;
	public final Paginacao paginacao;
	public final Count count;
	
	public static final SQLConfig DISTINCT_ROWNUM = new SQLConfig(Corte.DISTINCT, Paginacao.ROWNUM);
	public static final SQLConfig DISTINCT_ROW_NUMBER = new SQLConfig(Corte.DISTINCT, Paginacao.ROW_NUMBER);
	public static final SQLConfig GROUP_BY_ROWNUM = new SQLConfig(Corte.GROUP_BY, Paginacao.ROWNUM);
	public static final SQLConfig GROUP_BY_ROW_NUMBER = new SQLConfig(Corte.GROUP_BY, Paginacao.ROW_NUMBER);
	public static final SQLConfig EXISTS_ROWNUM = new SQLConfig(Corte.EXISTS, Paginacao.ROWNUM);
	public static final SQLConfig EXISTS_ROW_NUMBER = new SQLConfig(Corte.EXISTS, Paginacao.ROW_NUMBER);
	public static final SQLConfig IN_ROWNUM = new SQLConfig(Corte.IN, Paginacao.ROWNUM);
	public static final SQLConfig IN_ROW_NUMBER = new SQLConfig(Corte.IN, Paginacao.ROW_NUMBER);
	
	public static final SQLConfig PADRAO = DISTINCT_ROWNUM;
	
	public static final SQLConfig[] CONFIGS = {
		DISTINCT_ROWNUM, 
		DISTINCT_ROW_NUMBER, 
		GROUP_BY_ROWNUM, 
		GROUP_BY_ROW_NUMBER,
		EXISTS_ROWNUM, 
		EXISTS_ROW_NUMBER,
		IN_ROWNUM, 
		IN_ROW_NUMBER
	};
	
	public SQLConfig(Corte corte, Paginacao paginacao, Count count) {
		this.corte = corte;
		this.paginacao = paginacao;
		this.count = count;
	}
	
	private SQLConfig(Corte corte, Paginacao paginacao) {
		this.corte = corte;
		this.paginacao = paginacao;
		this.count = Count.SUBSELECT;
	}
	
	public String toCSV() {
		return "Corte=" + this.corte.toString() + ";Paginação=" + this.paginacao.toString() + ";Count=" + this.count.toString();
	}
	
	@Override
	public String toString() {
		return "Corte=" + this.corte.toString() + ";Paginação=" + this.paginacao.toString() + ";Count=" + this.count.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof SQLConfig)) {
            return false;
        }
		
		SQLConfig c = (SQLConfig) obj;
		return (this.corte.equals(c.corte) && this.paginacao.equals(c.paginacao) && this.count.equals(c.count));
	}

}
