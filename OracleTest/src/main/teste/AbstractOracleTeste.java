package main.teste;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import main.config.Config;
import main.oracle.Corte;
import main.oracle.Count;
import main.oracle.Paginacao;
import main.oracle.SQLConfig;
import main.oracle.UI;
import main.util.Timer;

public abstract class AbstractOracleTeste {

    protected UI ui;
    protected boolean incluirContainerPublico;
    protected String where;
    protected String orderBy;

    private Timer timer;
    private Map<Integer, int[]> containers;
    private boolean initialized;
    private String arquivo;
    private String cabecalho;

    private void init() {
        if (!this.initialized) {
            this.timer = new Timer();
            this.containers = new HashMap<Integer, int[]>();
            for (int quantidade : Config.QUANTIDADE_CONTAINERS) {
                this.containers.put(quantidade, UI.getContainers(quantidade, this.incluirContainerPublico));
            }
            this.initialized = true;

            if (this.arquivo == null) {
                this.arquivo = Config.ARQUIVO_LOG_PATH + "/" + getClass().getSimpleName() + "_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                        + ".csv";
            } else if (this.arquivo.isEmpty()) {
                this.arquivo = Config.ARQUIVO_LOG_PATH + "/" + getClass().getSimpleName() + "_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                        + ".csv";
            }
            this.cabecalho = "";
        }
    }

    private void reset() {

    }

    public void testePaginacaoHorizontal() {
        this.init();
        this.reset();

        String cabecalho = "Paginacao";
        Count count = Count.NORMAL; // count fixo, pois na paginação não importa.
        SQLConfig config;

        for (Paginacao paginacao : Paginacao.values()) {

            for (Corte corte : Corte.values()) {

                for (int quantidade : Config.QUANTIDADE_CONTAINERS) {

                    config = new SQLConfig(corte, paginacao, count);
                    String cfg = "Teste=" + cabecalho + ";" + "Containers=" + quantidade + ";" + config.toCSV();

                    this.ui.setConfig(config);
                    this.timer.reset();
                    for (int i = 1; i <= Config.EXECUCOES; i++) {
                        if (Config.IMPRIME_PROGRESSO) {
                            System.out.println(getClass().getSimpleName() + ":" + cfg + " (" + i + " / " + Config.EXECUCOES + ") "
                                    + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + " ...");
                        }

                        this.timer.start();
                        try {
                            this.ui.pagina(this.containers.get(quantidade), Config.TAMANHO_PAGINA, Config.PAGINA, this.orderBy, this.where);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        this.timer.end();
                    }

                    String lista = "Lista de Containers=";
                    for (int c : this.containers.get(quantidade)) {
                        lista += c + " ";
                    }

                    this.gravaLog(cfg + ";" + this.timer.toCSV() + ";" + lista);
                }
            }
        }
    }

    public void testePaginacaoVertical() {
        this.init();
        this.reset();

        String cabecalho = "Paginacao";
        Count count = Count.NORMAL; // count fixo, pois na paginação não importa.
        SQLConfig config;

        for (int quantidade : Config.QUANTIDADE_CONTAINERS) {

            for (Paginacao paginacao : Paginacao.values()) {

                for (Corte corte : Corte.values()) {

                    config = new SQLConfig(corte, paginacao, count);
                    String cfg = "Teste=" + cabecalho + ";" + "Containers=" + quantidade + ";" + config.toCSV();

                    this.ui.setConfig(config);
                    this.timer.reset();
                    for (int i = 1; i <= Config.EXECUCOES; i++) {
                        if (Config.IMPRIME_PROGRESSO) {
                            System.out.println(getClass().getSimpleName() + ":" + cfg + " (" + i + " / " + Config.EXECUCOES + ") "
                                    + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + " ...");
                        }

                        this.timer.start();
                        try {
                            this.ui.pagina(this.containers.get(quantidade), Config.TAMANHO_PAGINA, Config.PAGINA, this.orderBy, this.where);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        this.timer.end();
                    }

                    String lista = "Lista de Containers=";
                    for (int c : this.containers.get(quantidade)) {
                        lista += c + " ";
                    }

                    this.gravaLog(cfg + ";" + this.timer.toCSV() + ";" + lista);
                }
            }
        }
    }

    public void testeCountVertical() {
        this.testeCountVertical(0);
    }

    public void testeCountHorizontal() {
        this.testeCountHorizontal(0);
    }

    public void testeCountHorizontal(int limite) {
        this.init();
        this.reset();

        String cabecalho = "Count(" + ((limite > 0) ? limite : "*") + ")";
        Paginacao paginacao = Paginacao.ROWNUM; // paginacao fixa, pois no count não importa.
        SQLConfig config;

        for (Count count : Count.values()) {

            for (Corte corte : Corte.values()) {

                for (int quantidade : Config.QUANTIDADE_CONTAINERS) {

                    config = new SQLConfig(corte, paginacao, count);
                    String cfg = "Teste=" + cabecalho + ";" + "Containers=" + quantidade + ";" + config.toCSV();

                    this.ui.setConfig(config);
                    this.timer.reset();
                    for (int i = 1; i <= Config.EXECUCOES; i++) {
                        if (Config.IMPRIME_PROGRESSO) {
                            System.out.println(getClass().getSimpleName() + ":" + cfg + " (" + i + " / " + Config.EXECUCOES + ") "
                                    + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + " ...");
                        }

                        this.timer.start();
                        try {
                            this.ui.count(this.containers.get(quantidade), this.where, limite);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        this.timer.end();
                    }

                    String lista = "Lista de Containers=";
                    for (int c : this.containers.get(quantidade)) {
                        lista += c + " ";
                    }

                    this.gravaLog(cfg + ";" + this.timer.toCSV() + ";" + lista);
                }
            }
        }
    }

    public void testeCountVertical(int limite) {
        this.init();
        this.reset();

        String cabecalho = "Count(" + ((limite > 0) ? limite : "*") + ")";
        Paginacao paginacao = Paginacao.ROWNUM; // paginacao fixa, pois no count não importa.
        SQLConfig config;

        for (int quantidade : Config.QUANTIDADE_CONTAINERS) {

            for (Count count : Count.values()) {

                for (Corte corte : Corte.values()) {

                    config = new SQLConfig(corte, paginacao, count);
                    String cfg = "Teste=" + cabecalho + ";" + "Containers=" + quantidade + ";" + config.toCSV();

                    this.ui.setConfig(config);
                    this.timer.reset();
                    for (int i = 1; i <= Config.EXECUCOES; i++) {
                        if (Config.IMPRIME_PROGRESSO) {
                            System.out.println(getClass().getSimpleName() + ":" + cfg + " (" + i + " / " + Config.EXECUCOES + ") "
                                    + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + " ...");
                        }

                        this.timer.start();
                        try {
                            this.ui.count(this.containers.get(quantidade), this.where, limite);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        this.timer.end();
                    }

                    String lista = "Lista de Containers=";
                    for (int c : this.containers.get(quantidade)) {
                        lista += c + " ";
                    }

                    this.gravaLog(cfg + ";" + this.timer.toCSV() + ";" + lista);
                }
            }
        }
    }
    
    public void teste() {
    	this.init();
    	this.reset();
    	
    	for (int i = 1; i <= Config.EXECUCOES; i++) {
    		
    		for (Corte corte : Corte.values()) {
    		
    			for (Paginacao paginacao : Paginacao.values()) {
    				
    				for (Count count : Count.values()) {
    					
    					SQLConfig config = new SQLConfig(corte, paginacao, count);
    					this.ui.setConfig(config);
    					
    					for (int quantidade : Config.QUANTIDADE_CONTAINERS) {
	    					
    						String lista = "Lista de Containers=";
		                    for (int c : this.containers.get(quantidade)) {
		                        lista += c + " ";
		                    }
		                    
		                    // COUNT
    						String cfg = "Teste=Count(" + Config.LIMITE_COUNT + ");Containers=" + quantidade + ";" + config.toCSV();
    						if (Config.IMPRIME_PROGRESSO) {
                                System.out.println(getClass().getSimpleName() + ":" + cfg + " (" + i + " / " + Config.EXECUCOES + ") "
                                        + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + " ...");
                            }
    						
    						int qt = 0;
    						long inicio;
    						long duracao;
    						
    						if (paginacao.equals(Paginacao.ROWNUM)) {
    							inicio = System.currentTimeMillis();
			    				//this.timer.start();
			                    try {
			                        qt = this.ui.count(this.containers.get(quantidade), this.where, Config.LIMITE_COUNT);
			                    } catch (Exception e) {
			                        e.printStackTrace();
			                    }
			                    //this.timer.end();
			                    duracao = System.currentTimeMillis() - inicio;
			                    //this.gravaLog(cfg + ";" + "Resultado=" + qt + ";" + this.timer.toCSV() + ";" + lista);
			                    this.gravaLog(cfg + ";Execução=" + i + ";Resultado=" + qt + ";Duração=" + duracao + ";" + lista);
    						}
		                    
		                    
		                    // PAGINACAO
		                    cfg = "Teste=Paginacao;" + "Containers=" + quantidade + ";" + config.toCSV();
		                    if (Config.IMPRIME_PROGRESSO) {
                                System.out.println(getClass().getSimpleName() + ":" + cfg + " (" + i + " / " + Config.EXECUCOES + ") "
                                        + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + " ...");
                            }
		                    
		                    //this.timer.start();
		                    inicio = System.currentTimeMillis();
	                        try {
	                            this.ui.pagina(this.containers.get(quantidade), Config.TAMANHO_PAGINA, Config.PAGINA, this.orderBy, this.where);
	                        } catch (Exception e) {
	                            e.printStackTrace();
	                        }
	                        //this.timer.end();
	                        duracao = System.currentTimeMillis() - inicio;
	                        //this.gravaLog(cfg + ";" + "Resultado=N/A;" + this.timer.toCSV() + ";" + lista);
	                        this.gravaLog(cfg + ";Execução=" + i + ";Resultado=N/A;Duração=" + duracao + ";" + lista);
    					}	                    
    				}
    			}
    		}    		
    	}
    }

    private void gravaLog(String resultado) {
        try (Writer log = new FileWriter(this.arquivo, true);) {
            System.out.println("Resultado = " + resultado);
            String[] valores = resultado.split(";");
            if (this.cabecalho.isEmpty()) {
                for (String chave : valores) {
                    this.cabecalho += chave.split("=")[0] + ";";
                }
                log.write(this.cabecalho + "\n");
            }

            String corpo = "";
            for (String valor : valores) {
                corpo += valor.split("=")[1] + ";";
            }

            log.write(corpo + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
