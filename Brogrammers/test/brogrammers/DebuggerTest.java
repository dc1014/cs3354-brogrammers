/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brogrammers;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mauhib
 */
public class DebuggerTest {
    
    @Test
    public void testSingleton() {
        Debugger d1 = Debugger.getInstance();
        Debugger d2 = Debugger.getInstance();
        assertEquals("Test if debugger is a Singleton",true,d1==d2);
    }

    @Test
    public void testFunctionality() {
        Debugger d = Debugger.getInstance();
        d.setDebug(true);
        assertEquals("Test if debug is true", true ,d.isDebug());
        d.setDebug(false);
        assertEquals("Test if debug is false", false ,d.isDebug());
    }
    
}
