package main.util;

import java.util.ArrayList;
import java.util.List;

public class Timer {
	
	private long inicio = 0;
    private long fim = 0;
    private List<Long> tempos = new ArrayList<Long>();

    public void start() {
        this.inicio = System.currentTimeMillis();
    }

    public void end() {
        this.fim = System.currentTimeMillis();
        this.tempos.add((fim - inicio));
        this.inicio = 0;
        this.fim = 0;
    }

    public void reset() {
        this.inicio = 0;
        this.fim = 0;
        this.tempos.clear();
    }
    
    public String toCSV() {
    	return new TimerResult(this.tempos).toCSV();
    }
}
