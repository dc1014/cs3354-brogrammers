package comparator;

import brogrammers.Bookmark;
import java.util.Comparator;

  public class AlphabeticalComparator implements Comparator<Bookmark> {

        @Override
        public int compare(Bookmark o1, Bookmark o2) {
            return o1.getChannel().compareTo(o2.getChannel());
        }
    }