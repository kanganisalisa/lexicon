import structure5.*;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class LexiconTrie implements Lexicon {

    /* Root of the LexiconTrie */
    protected LexiconNode root;

    /* Size (number of nodes) of the LexiconTrie */
    protected int size;

    /* Number of words in the LexiconTrie */
    protected int numWords;

    /* String representing the characters in the alphabet */
    protected static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    /**
    * Constructor for LexiconTrie
    * Initializes instance variables to default values
    **/
    public LexiconTrie() {
      root = new LexiconNode('+', false);
      size = 0;
      numWords = 0;
    }

    /**
    * adds a word to the LexiconTrie
    * @param word is the String that will be added
    * @post word, if not already in the Trie, is added
    * @return false if the word is already in the Trie
    **/
    public boolean addWord(String word) {

      word = word.toLowerCase();

      if (containsWord(word)) {
        return false;
      }

      return addWordHelper(word, root);
    }

    /**
    * helper method to add a word to the LexiconTrie
    * @param word is the String that will be added
    * @param currentRoot is a reference to the current root
    * @pre in the initial call to this method, root is passed in as currentRoot
    * @return true if the word is successfully added to the Trie
    **/
    private boolean addWordHelper(String word, LexiconNode currentRoot) {

      // get the next Node
      char ch = word.charAt(0);
      Assert.pre(alphabet.contains(ch + ""), "Character is not a letter.");
      LexiconNode nextRoot = currentRoot.getChild(ch);

      // when the last letter is reached
      if (word.length() == 1) {

        // if the letter already exists
        if (nextRoot != null) {
          nextRoot.setIsWord(true); // turn on isWord flag
          numWords++;
          return true;
        }
        // otherwise, add the letter and turn on isWord flag
        currentRoot.addChild(new LexiconNode(ch, true));
        numWords++;
        return true;
      }

      String restOfWord = word.substring(1);

      // if the letter already exists, recurse on the rest of word
      if (nextRoot != null) {
        return addWordHelper(restOfWord, nextRoot);
      }
      // otherwise, add the letter and recurse on the rest of word
      LexiconNode child = new LexiconNode(ch, false);
      currentRoot.addChild(child);
      nextRoot = currentRoot.getChild(ch);
      return addWordHelper(restOfWord, nextRoot);
    }

    /**
    * adds words from a text file to the LexiconTrie
    * @param filename is the file containing the words
    * @pre the file exists
    * @post all the words in the file are added to the Trie
    * @return the number of words from the file added to the Trie
    **/
    public int addWordsFromFile(String filename) {

      int wordsAdded = 0;

      try {

        File wordsFile = new File(filename);
        Scanner scn = new Scanner(wordsFile);

        // scan the file
        while (scn.hasNextLine()) {
          String word = scn.nextLine();
          // if the word is not already in the Trie
          if (!containsWord(word)) {
            addWord(word);
            wordsAdded++;
          }
        }
        scn.close();
        return wordsAdded;

      } catch (FileNotFoundException e) {
        System.out.println("File not found");
      }
      return wordsAdded;
    }

    /**
    * removes a word from the LexiconTrie
    * @param word is the String that will be removed
    * @post the word, if in the Trie, is removed (isWord flag is turned off)
    * @return false is word is not in the Trie
    **/
    public boolean removeWord(String word) {

      word = word.toLowerCase();

      if (!containsWord(word)) {
        return false;
      }

      return removeWordHelper(word, root);
    }

    /**
    * helper method to remove a word to the LexiconTrie
    * @param word is the String that will be removed
    * @param currentRoot is a reference to the current root
    * @pre in the initial call to this method, root is passed in as currentRoot
    * @return true if the word is successfully removed to the Trie
    **/
    private boolean removeWordHelper(String word, LexiconNode currentRoot) {

      // get the next Node
      char ch = word.charAt(0);
      LexiconNode nextRoot = currentRoot.getChild(ch);

      // when the last letter is reached
      if (word.length() == 1) {
        nextRoot.setIsWord(false); // turn off the isWord flag
        numWords--;
        return true;
      }
      // recurse on the rest of the word
      return removeWordHelper(word.substring(1), nextRoot);
    }

    /**
    * accessor method for the number of words in the LexiconTrie
    * @return numWords, the total number of words in the Trie
    **/
    public int numWords() {

      return numWords;

    }

    /**
    * checks if a word is in the LexiconTrie
    * @param word is the String that will be checked
    * @post a boolean will be returned indicating if the word is in the Trie
    **/
    public boolean containsWord(String word) {

      word = word.toLowerCase();

      return containsWordHelper(word, root);
    }

    /**
    * helper method to check if a word is in the LexiconTrie
    * @param word is the String that will be checked
    * @param currentRoot is a reference to the current root
    * @pre in the initial call to this method, root is passed in as currentRoot
    * @return true if the word is in the Trie
    **/
    public boolean containsWordHelper(String word, LexiconNode currentRoot) {

      // get the next Node
      char ch = word.charAt(0);
      LexiconNode nextRoot = currentRoot.getChild(ch);

      // when the last letter is reached
      if (word.length() == 1) {

        // check that it exists in the Trie and isWord flag is on
        if (nextRoot != null && nextRoot.isWord()) {
          return true;
        }
        return false;
      }

      // if any of the letters do not exist in the Trie
      if (nextRoot == null) {
        return false;
      }
      return containsWordHelper(word.substring(1), nextRoot);
    }

    /**
    * checks if a prefix is in the LexiconTrie
    * @param word is the String that will be checked
    * @post a boolean will be returned indicating if the prefix is in the Trie
    **/
    public boolean containsPrefix(String prefix) {

      prefix = prefix.toLowerCase();

      return containsPrefixHelper(prefix, root);

    }

    /**
    * helper method to check if a prefix is in the LexiconTrie
    * @param word is the String that will be checked
    * @param currentRoot is a reference to the current root
    * @pre in the initial call to this method, root is passed in as currentRoot
    * @return true if the prefix is in the Trie
    **/
    private boolean containsPrefixHelper(String word, LexiconNode currentRoot) {

      // get the next Node
      char ch = word.charAt(0);
      LexiconNode nextRoot = currentRoot.getChild(ch);

      if (word.length() == 1) {
        // only difference from containsWordHelper is checks if isWord is
        // flagged as false rather than true
        if (nextRoot != null && !nextRoot.isWord()) {
          return true;
        }
        return false;
      }

      if (nextRoot == null) {
        return false;
      }
      return containsPrefixHelper(word.substring(1), nextRoot);
    }

    /**
    * helper method that recursively builds a Vector of words in the LexiconTrie
    * @param current maintains the current Lexicon Node
    * @param words the Vector of words
    * @param wordSoFar maintains the contents of a word
    * @pre in the initial call to this method, root is passed in as currentRoot,
    * words is an empty Vector, and wordSoFar is an empty String
    * @post words is populated with words in the Trie
    **/
    private void iteratorHelper(LexiconNode current, Vector<String> words, String wordSoFar) {

      Vector<LexiconNode> children = current.getChildren();

      // if the current Node is not the root, add to wordSoFar
      if (!current.equals(root)) {
        wordSoFar += current.getLetter();
      }

      // if the current Node's isWord is flagged true, add wordSoFar to words
      if (current.isWord()) {
        words.add(wordSoFar);
      }

      // recurse over current's children
      for (LexiconNode child : children) {
        iteratorHelper(child, words, wordSoFar);
      }
    }

    /**
    * creates an iterator for iterating over the words in the LexiconTrie
    * @return an applicable iterator
    **/
    public Iterator<String> iterator() {

      Vector<String> words = new Vector<String>();
      iteratorHelper(root, words, "");
      return words.iterator();

    }

    /**
    * Optional (extra credit) implementation
    **/
    public Set<String> suggestCorrections(String target, int maxDistance) {
      return null;
    }

    /**
    * Optional (extra credit) implementation
    **/
    public Set<String> matchRegex(String pattern) {
      return null;
    }

}
