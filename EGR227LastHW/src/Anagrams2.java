//Another solution implementation
import java.util.*;

/**
 * Created by Christopher on 2/7/2017.
 */
public class Anagrams2 {
    private List<String> orderedDictionary;
    private Map<String, LetterInventory> inventoryMap;
    //the spec requires to use to map conserves time and space

    /**
     * Constructor
     * @param dictionary
     */
    public Anagrams2(List<String> dictionary) {
        orderedDictionary = new LinkedList<>(dictionary);
        inventoryMap = new HashMap<>(); //used map to prevent re-creating the same object for each explore
        for(String s : dictionary) {
            inventoryMap.put(s, new LetterInventory(s)); //according to spec building letter inventory
                                                         // should only happen once in the constructor
                                                         //Thus using map is required
        }
    }

    /**
     * prints all possible answers of anagram with given max
     * @param text
     * @param max
     */
    public void print(String text, int max) {
        Collection<List<String>> answers = new LinkedList<>();
        explore(new LetterInventory(text), new LinkedList<String>(), answers, max);
        for(List<String> list : answers) {
            System.out.println(list);
        }
    }

    /**
     * explores Anagram solution given remaining text (remaining) and current chosen words (chosen)
     * Stores all answers in answers
     * Ensures all answers has at most max elements
     */
    private void explore(LetterInventory remaining, List<String> chosen,
                         Collection<List<String>> answers, int max){
        if(remaining.isEmpty()) {
            answers.add(new LinkedList<>(chosen)); //add the copy of current to answer set
            //answers.add(chosen) results in incorrect answer
            //  because of the reference semantics
            //if we just do add(chosen) then all elements in answer
            //  will be just the same pointing to the same address
        } else if( max == 0 || chosen.size() < max ){
            for (String s : orderedDictionary) {
                LetterInventory newRemaining = remaining.subtract(inventoryMap.get(s));
                //FYI, used map to conserve space
                //The spec requires map should be used to build letter inventory only once,
                //which happens in the constructor
                //Below code works functionally but takes up more memory and time
                //LetterInventory newRemaining = remaining.subtract(new LetterInventory(s));
                if (newRemaining != null) { //this automatically prunes the dictionary!
                    chosen.add(s); //choose s by appending s to chosen's end
                    explore(newRemaining, chosen, answers, max);
                    chosen.remove(chosen.size() - 1);   //un-choose by removing s from chosen's end (making sure the same s is removed
                    //   when there are multiple occurrences of s)
                    // Note chosen.remove(s) will result in incorrect answer!!
                    // (in case of multiple occurrences of s in chosen)
                }
            }
        }
    }
}