package main.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimerResult implements Comparable<TimerResult> {
	
	private static final int BEFORE = -1;
	private static final int EQUAL = 0;
	private static final int AFTER = 1;
	
	private long media = 0;
    private long melhor = 0;
    private long pior = 0;
    private double desvioPadrao = 0;
    private List<Long> tempos = new ArrayList<Long>();
    
    public TimerResult(List<Long> tempos) {
    	this.tempos.addAll(tempos);
    	this.calcular();
    }
    
    private void calcular() {
    	// retira o pior e o melhor caso e faz a media do restante
        if (tempos.size() > 1) {
        	List<Long> temp = new ArrayList<Long>(this.tempos.size());
        	temp.addAll(this.tempos);
            Collections.sort(temp);

            this.melhor = temp.remove(0);
            this.pior = temp.remove(temp.size() - 1);
            this.media = 0;
            for (long tempo : temp) {
                this.media += tempo;
            }
            this.media = this.media / temp.size();

            double variancia = 0;
            for (long tempo : temp) {
                variancia += (this.media - tempo) * (this.media - tempo);
            }
            this.desvioPadrao = Math.sqrt(variancia / temp.size());
            this.desvioPadrao = (double) Math.round(this.desvioPadrao * 100) / 100;
        } else {
            this.media = this.tempos.get(0);
            this.melhor = 0;
            this.pior = 0;
            this.desvioPadrao = 0;
        }
    }
    
    public String toCSV() {
    	String out = "";
    	
    	for (int i = 0; i < this.tempos.size(); i++) {
    		out += "Execução " + (i+1) + "=" + this.tempos.get(i) + ";";
        }
    	
        out += "Média=" + this.media; 
        out += ";Desvio Padrão=" + this.desvioPadrao;
        out += ";Melhor Tempo=" + this.melhor;
        out += ";Pior Tempo=" + this.pior;
        
        return out;
    }
    
    @Override
    public String toString() {
    	String out = "";
        out += "Média = " + this.media; 
        out += ", Desvio Padrão = " + this.desvioPadrao;
        out += ", Melhor Tempo = " + this.melhor;
        out += ", Pior Tempo = " + this.pior;
        out += ", Execuções(" + this.tempos.size() + ") = ";
        
        for (long tempo: tempos) {
        	out += tempo + " ";
        }
        
        return out;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof TimerResult)) {
            return false;
        }

        TimerResult t = (TimerResult) obj;
        return ((this.media == t.media) && (this.melhor == t.melhor) && (this.pior == t.pior) && (this.tempos.size() == t.tempos.size()));
    }

    @Override
    public int compareTo(TimerResult t) {
        if (this == t) {
            return EQUAL;
        }

        if (this.media < t.media) {
            return BEFORE;
        } else if (this.media > t.media) {
            return AFTER;
        } else { // medias iguais, quem tiver o menor desvio padrão ganha
            // if (this.pior < t.pior) {
            if (this.desvioPadrao < t.desvioPadrao) {
                return BEFORE;
            } else if (this.desvioPadrao < t.desvioPadrao) {
                return AFTER;
            } else { // desvios padrão iguais, quem tiver o menor pior tempo ganha
                if (this.pior < t.pior) {
                    return BEFORE;
                } else if (this.pior > t.pior) {
                    return AFTER;
                } else { // piores tempos iguais, quem tiver o menor melhor tempo ganha
                    if (this.melhor < t.melhor) {
                        return BEFORE;
                    } else if (this.melhor > t.melhor) {
                        return AFTER;
                    } else { // tudo igual, então tanto faz
                        return BEFORE;
                    }
                }
            }
        }
    }

}
