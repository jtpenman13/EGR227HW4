import java.util.*;

/**
 * Created by Dr. Han on 2/12/2019.
 */
public class Anagrams {
    private List<String> orderedDictionary;
    private Map<String, LetterInventory> inventoryMap;
    //the spec requires to use to map conserves time and space

    /**
     * Constructor
     * @param dictionary
     */
    public Anagrams(List<String> dictionary) {
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
        explore(new LetterInventory(text), new LinkedList<String>(),
                pruneDictionary(text), max);
    }

    private List<String> pruneDictionary(String text){
        List<String> prunedDict = new LinkedList<>();
        LetterInventory textInventory = new LetterInventory(text); //ok to instantiate here

        for(String word: orderedDictionary){
            if(textInventory.subtract(inventoryMap.get(word)) != null) {
                prunedDict.add(word);
            }
        }
        return prunedDict;
    }

    /**
     * explores Anagram solution given remaining text (remaining) and current chosen words (chosen)
     * Stores all answers in answers
     * Ensures all answers has at most max elements
     */
    private void explore(LetterInventory remaining, List<String> chosen,
                         List<String> prunedDictionary, int max){
        if(remaining.isEmpty()) {
            System.out.println(chosen); //print out!
        } else if( max == 0 || chosen.size() < max ){
            for (String s : prunedDictionary) {
                LetterInventory newRemaining = remaining.subtract(inventoryMap.get(s));
                //FYI, used map to conserve space
                //The spec requires map should be used to build letter inventory only once,
                //which happens in the constructor
                //Below code works functionally but takes up more memory and time
                //LetterInventory newRemaining = remaining.subtract(new LetterInventory(s));
                if (newRemaining != null) {
                    chosen.add(s); //choose s by appending s to chosen's end
                    explore(newRemaining, chosen, prunedDictionary, max);
                    chosen.remove(chosen.size() - 1);   //un-choose by removing s from chosen's end (making sure the same s is removed
                    //   when there are multiple occurrences of s)
                    // Note chosen.remove(s) will result in incorrect answer!!
                    // (in case of multiple occurrences of s in chosen)
                }
            }
        }
    }
}