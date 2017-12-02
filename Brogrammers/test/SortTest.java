/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import brogrammers.Bookmark;
import comparator.*;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nisha
 */
public class SortTest {


    /**
     * Test of compare method, of class AlphabeticalComparator.
     */
    // implement tests in this function
    @Test
    public void testCompareAlpha() {
         ArrayList<Bookmark> bookmarks;
      
            bookmarks = new ArrayList<>();
            bookmarks.add(new Bookmark("Alpha"));
            bookmarks.add(new Bookmark("Gamma"));
            bookmarks.add(new Bookmark("Beta"));  
            
            bookmarks.sort(new AlphabeticalComparator());
            assertEquals(bookmarks.get(0).toString(), "Alpha");
            assertEquals(bookmarks.get(1).toString(), "Beta");
            assertEquals(bookmarks.get(2).toString(), "Gamma");
            
    }
    
     @Test
    public void testCompareNegAlpha() {
         ArrayList<Bookmark> bookmarks;
      
            bookmarks = new ArrayList<>();
            bookmarks.add(new Bookmark("Alpha"));
            bookmarks.add(new Bookmark("Gamma"));
            bookmarks.add(new Bookmark("Beta"));  
             
            bookmarks.sort(new NegativeAlphabeticalComparator());
            assertEquals(bookmarks.get(0).toString(), "Gamma");
            assertEquals(bookmarks.get(1).toString(), "Beta");
            assertEquals(bookmarks.get(2).toString(), "Alpha");
            
            
    }
    
}
