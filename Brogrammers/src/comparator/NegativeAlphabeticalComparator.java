/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nisha
 */
package comparator;
import brogrammers.Bookmark;
import java.util.Comparator;
public class NegativeAlphabeticalComparator implements Comparator<Bookmark> {
        @Override
        public int compare(Bookmark u, Bookmark v) {
            return (-1 *(u.getChannel().compareTo(v.getChannel())));
        }
}
