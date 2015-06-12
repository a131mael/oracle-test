package main.oracle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import main.config.Config;

public class UI {

    public final String nome;
    private SQLConfig config;
    private SQL sql;

    public UI(String tabela) {
        this.nome = tabela;
        this.config = SQLConfig.PADRAO;
        this.sql = new SQL(tabela, this.config);
    }

    public UI(String tabela, SQLConfig config) {
        this.nome = tabela;
        this.config = config;
        this.sql = new SQL(tabela, this.config);
    }

    public void setConfig(SQLConfig newConfig) {
        this.config = newConfig;
        this.sql.setConfig(newConfig);
    }

    private String aplicarCorte(String sql, int[] containers) {
        String corte = "";
        for (int container : containers) {
            if (!corte.isEmpty()) {
                corte += " OR ";
            }
            corte += "ID_CONTAINER = " + container;
        }

        return sql.replace("{CONTAINERS}", corte);
    }

    public int count(int[] containers, String where, int limite) {
        int count = 0;
        String sql = this.aplicarCorte(this.sql.count(where, limite), containers);

        if (Config.IMPRIME_SQL) {
            System.out.println(sql);
        }

        if (!Config.SIMULAR) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = OracleConnection.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                stmt.setQueryTimeout(Config.TIMEOUT);
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    rs.close();
                } catch (Exception e) {}
                try {
                    stmt.close();
                } catch (Exception e) {}
            }
        }

        return count;
    }

    public int pagina(int[] containers, int tamanhoPagina, int pagina, String orderBy, String where) {
    	int count = 0;
        int inicio = (pagina - 1) * tamanhoPagina + 1;
        int fim = pagina * tamanhoPagina;
        String sql = this.aplicarCorte(this.sql.pagina(inicio, fim, orderBy, where), containers);

        if (Config.IMPRIME_SQL) {
            System.out.println(sql);
        }

        if (!Config.SIMULAR) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = OracleConnection.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                stmt.setQueryTimeout(Config.TIMEOUT);
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    rs.getInt("SK_" + this.nome);
                    count += 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    rs.close();
                } catch (Exception e) {}
                try {
                    stmt.close();
                } catch (Exception e) {}
            }
        }
        
        return count;
    }

    public UI join(UI ui, String on) {
        String tabela = this.nome + "\nJOIN " + ui.nome + " ON " + on;
        UI join = new UI(tabela, this.config);
        join.sql = this.sql.join(ui.sql, on);

        return join;
    }

    public static int[] getContainers(int quantidade, boolean incluirContainerPublico) {
        int[] containers = new int[quantidade];

        if (Config.SIMULAR) {
            Random rnd = new Random();
            for (int i = 0; i < quantidade; i++) {
                containers[i] = rnd.nextInt(quantidade) + 1;
            }
        } else {
            String sql = "SELECT ID_CONTAINER FROM (SELECT ID_CONTAINER FROM CTN_CONTAINER WHERE ID_CONTAINER > 0 ORDER BY DBMS_RANDOM.VALUE()) WHERE ROWNUM <= "
                    + quantidade;

            try (ResultSet rs = OracleConnection.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery(sql);) {
                int i = 0;
                while (rs.next()) {
                    containers[i] = rs.getInt("ID_CONTAINER");
                    i += 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (incluirContainerPublico) {
            int[] temp = new int[quantidade + 1];
            System.arraycopy(containers, 0, temp, 1, quantidade);
            temp[0] = Config.CONTAINER_PUBLICO;
            return temp;
        }

        return containers;
    }
}
