package main.oracle;

public class SQL {

    private String with;
    private String select;
    private String from;
    private String join;
    private String where;

    private SQLConfig config;

    public SQL(String tabela, SQLConfig config) {
        this.config = config;
        this.with = "";
        this.select = tabela + ".*";
        this.from = tabela;
        this.join = "";
        this.where = "";

        this.setConfig(config);
    }

    public void setConfig(SQLConfig config) {
        this.config = config;

        switch (this.config.corte) {
            case DISTINCT:
                this.with = "CORTE_" + this.from + " AS (SELECT DISTINCT SK_" + this.from + " FROM CONTAINER_" + this.from + " WHERE ({CONTAINERS}))";
                this.join = "JOIN CORTE_" + this.from + " ON " + this.from + ".SK_" + this.from + " = CORTE_" + this.from + ".SK_" + this.from + "";
                this.where = "";
                break;
            case GROUP_BY:
                this.with = "CORTE_" + this.from + " AS (SELECT SK_" + this.from + " FROM CONTAINER_" + this.from
                        + " WHERE ({CONTAINERS}) GROUP BY SK_" + this.from + ")";
                this.join = "JOIN CORTE_" + this.from + " ON " + this.from + ".SK_" + this.from + " = CORTE_" + this.from + ".SK_" + this.from + "";
                this.where = "";
                break;
            case EXISTS:
                this.with = "";
                this.join = "";
                this.where = "EXISTS (SELECT 1 FROM CONTAINER_" + this.from + " WHERE SK_" + this.from + " = " + this.from + ".SK_" + this.from
                        + " AND ({CONTAINERS}))";
                break;
            case IN:
                this.with = "";
                this.join = "";
                this.where = "" + this.from + ".SK_" + this.from + " IN (SELECT SK_" + this.from + " FROM CONTAINER_" + this.from
                        + " WHERE ({CONTAINERS}))";
                break;
            default:
                break;
        }

    }

    public String count(String where) {
        String sql = "";

        if (!this.with.isEmpty()) {
            sql += "WITH \n" + this.with + " \n";
        }

        sql += "SELECT COUNT(*) \n";
        sql += "FROM " + this.from;

        if (!this.join.isEmpty()) {
            sql += "\n" + this.join;
        }

        String whereOp = "\nWHERE ";
        if (!this.where.isEmpty()) {
            sql += whereOp + this.where;
            whereOp = "\nAND ";
        }

        if (where != null) {
            if (!where.isEmpty()) {
                sql += whereOp + where;
            }
        }

        return sql;
    }

    public String count(String where, int limite) {
        if (limite <= 0) {
            return this.count(where);
        }

        if (this.config.count.equals(Count.NORMAL)) {
            String where2 = "ROWNUM <= " + limite;

            if (where != null) {
                if (!where.isEmpty()) {
                    where2 += " AND " + where;
                }
            }

            return this.count(where2);
        }

        String sql = "SELECT COUNT(*) FROM ( \n";

        if (!this.with.isEmpty()) {
            sql += "\tWITH \n" + this.with + " \n";
        }

        sql += "\tSELECT /*+ FIRST_ROWS(" + limite + ") */ ROWNUM AS RNUM \n";
        sql += "\tFROM " + this.from;

        if (!this.join.isEmpty()) {
            sql += "\n" + this.join;
        }

        sql += "\n\tWHERE ROWNUM <= " + limite;

        if (!this.where.isEmpty()) {
            sql += " AND " + this.where;
        }

        if (where != null) {
            if (!where.isEmpty()) {
                sql += " AND " + where;
            }
        }

        sql += "\n)";

        return sql;
    }

    public String pagina(int inicio, int fim, String orderBy, String where) {
        if (this.config.paginacao.equals(Paginacao.ROW_NUMBER)) {
            return this.paginacaoRowNumber(inicio, fim, orderBy, where);
        } else {
            return this.paginacaoRownum(inicio, fim, orderBy, where);
        }
    }

    private String paginacaoRowNumber(int inicio, int fim, String orderBy, String where) {
        String sql = "SELECT * FROM ( \n";

        if (!this.with.isEmpty()) {
            sql += "\tWITH \n" + this.with + " \n";
        }

        if (orderBy == null) {
            orderBy = "NULL";
        } else if (orderBy.isEmpty()) {
            orderBy = "NULL";
        }

        sql += "\tSELECT /*+ FIRST_ROWS(" + (fim - inicio + 1) + ") */ " + this.select + ", ROW_NUMBER() OVER (ORDER BY " + orderBy + ") AS RNUM \n";
        sql += "\tFROM " + this.from;

        if (!this.join.isEmpty()) {
            sql += "\n" + this.join;
        }

        String whereOp = "\n\tWHERE ";
        if (!this.where.isEmpty()) {
            sql += whereOp + this.where;
            whereOp = "\n\tAND ";
        }

        if (where != null) {
            if (!where.isEmpty()) {
                sql += whereOp + where;
            }
        }

        sql += "\n) WHERE ROWNUM BETWEEN " + String.valueOf(inicio) + " AND " + String.valueOf(fim);

        return sql;
    }

    private String paginacaoRownum(int inicio, int fim, String orderBy, String where) {
        String sql = "SELECT * FROM ( \n";
        sql += "\tSELECT /*+ FIRST_ROWS(" + (fim - inicio + 1) + ") */ TOPN.*, ROWNUM AS RNUM FROM ( \n";

        if (!this.with.isEmpty()) {
            sql += "\t\tWITH \n" + this.with + " \n";
        }

        sql += "\t\tSELECT /*+ FIRST_ROWS(" + (fim - inicio + 1) + ") */ " + this.select + " \n";
        sql += "\t\tFROM " + this.from;

        if (!this.join.isEmpty()) {
            sql += "\n" + this.join;
        }

        String whereOp = "\n\t\tWHERE ";
        if (!this.where.isEmpty()) {
            sql += whereOp + this.where;
            whereOp = "\n\t\tAND ";
        }

        if (where != null) {
            if (!where.isEmpty()) {
                sql += whereOp + where;
            }
        }

        if (orderBy != null) {
            if (!orderBy.isEmpty()) {
                sql += "\n\t\tORDER BY " + orderBy;
            }
        }

        sql += "\n\t) TOPN";
        sql += "\n\tWHERE ROWNUM <= " + String.valueOf(fim);
        sql += "\n) WHERE RNUM >= " + String.valueOf(inicio);

        return sql;
    }

    public SQL join(SQL sql, String on) {
        SQL join = new SQL("", this.config);

        join.select = this.select;
        join.from = this.from + "\nJOIN " + sql.from + " ON " + on;

        if (!this.with.isEmpty() && !sql.with.isEmpty()) {
            join.with = this.with + ", \n" + sql.with;
        } else {
            join.with = (this.with.isEmpty()) ? sql.with : this.with;
        }

        if (!this.join.isEmpty() && !sql.join.isEmpty()) {
            join.join = this.join + "\n" + sql.join;
        } else {
            join.join = (this.join.isEmpty()) ? sql.join : this.join;
        }

        if (!this.where.isEmpty() && !sql.where.isEmpty()) {
            join.where = this.where + "\nAND " + sql.where;
        } else {
            join.where = (this.where.isEmpty()) ? sql.where : this.where;
        }

        return join;
    }
}
