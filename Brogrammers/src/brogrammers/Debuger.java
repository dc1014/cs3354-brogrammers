package brogrammers;

class Debugger
{
    // static variable single_instance of type Singleton
    private static Debugger single_instance = null;
 
    public boolean dbg;

    public boolean isDebug() {
        return dbg;
    }

    public void setDebug(boolean dbg) {
        this.dbg = dbg;
    }
    
    
 
    private Debugger(){}
 
    public static Debugger getInstance()
    {
        if (single_instance == null)
            single_instance = new Debugger();
 
        return single_instance;
    }
}