package main.config;

public abstract class Config {

    public static final String ORACLE_IP = "localhost";
    public static final String ORACLE_PORT = "1521";
    public static final String ORACLE_SID = "XE";
    public static final String ORACLE_USER = "gleidson";
    public static final String ORACLE_PWD = "gleidson";

    public static final int TAMANHO_PAGINA = 100;
    public static final int LIMITE_COUNT = 1001;
    public static final int PAGINA = 1;
    public static final int[] QUANTIDADE_CONTAINERS = { 1, 5, 10, 25, 50, 100 };
    public static final int CONTAINER_PUBLICO = 0;

    public static final int EXECUCOES = 5;
    public static final int TIMEOUT = 600; // 10 minutos

    // public static final boolean IMPRIME_CONTAINERS = true;
    public static final boolean IMPRIME_PROGRESSO = true;
    public static final boolean IMPRIME_SQL = false;
    public static final boolean SIMULAR = false;
    public static final boolean GERA_ARQUIVO_LOG = true;
    public static final String ARQUIVO_LOG_PATH = System.getProperty("user.dir");

}
