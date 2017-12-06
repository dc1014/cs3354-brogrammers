package brogrammers;

/**
 * This class sets the debug status and that is used throughout the program.
 * It was mainly used for developing and testing the GUI without the need for the server
 * to be running
 * 
 * @author Mauhib
 */
class Debugger
{
    /**
     * static variable single_instance of type Singleton
     */
    private static Debugger single_instance = null;
 
    public boolean dbg;

    /**
     * @return debugging status
    */
    public boolean isDebug() {
        return dbg;
    }
    
    /**
     * Sets debugging status
     * @param dbg the status to set
     */
    public void setDebug(boolean dbg) {
        this.dbg = dbg;
    }
    
    /**
     * This is private to forbid instantiation
     */
    private Debugger(){}
 
    /**
     * 
     * @return Singleton instance 
     */
    public static Debugger getInstance()
    {
        if (single_instance == null)
            single_instance = new Debugger();
 
        return single_instance;
    }
}