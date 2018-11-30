package game;

public class Chronomètre {
    private long begin;
    private long end;
    private long current;
    private int limite;

    public Chronomètre(int limite) {
        //intialisation
        this.limite = limite;
    }
    
    public void start(){
        this.begin = System.currentTimeMillis();
    }
 
    public void stop(){
        this.end = System.currentTimeMillis();
    }
 
    public long getTime() {
        return this.end - this.begin;
    }
 
    public long getMilliseconds() {
        return this.end - this.begin;
    }
 
    public int getSeconds() {
        return (int) ((this.end - this.begin) / 1000.0);
    }
 
    public double getMinutes() {
        return (this.end - this.begin) / 60000.0;
    }
 
    public double getHours() {
        return (this.end - this.begin) / 3600000.0;
    }
    
    // Method to know if it remains time.
    public boolean remainsTime() {
        this.current = System.currentTimeMillis();
        int timeSpent;
        timeSpent = (int)((this.current - this.begin)/1000.0);
        return (timeSpent < this.limite);
    }
  
}
