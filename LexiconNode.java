import structure5.*;
import java.util.Iterator;

class LexiconNode implements Comparable<LexiconNode> {

    /* single letter stored in this node */
    protected char letter;

    /* true if this node ends some path that defines a valid word */
    protected boolean isWord;

    /* a data structure for keeping track of the children of this LexiconNode */
    protected Vector<LexiconNode> children;

    /**
    * Constructor for LexiconNode
    **/
    LexiconNode(char letter, boolean isWord) {
      this.letter = Character.toLowerCase(letter);
      this.isWord = isWord;
      children = new Vector<LexiconNode>();
    }

    /**
    * Compare this LexiconNode to another (the characters stored at the Nodes)
    * @param o is the other LexiconNode
    * @return the int corresponding to comparing the characters (-1, 0, 1)
    **/
    public int compareTo(LexiconNode o) {
      return letter - o.getLetter();
    }

    /**
    * Accessor method for this LexiconNode's letter
    * @return this LexiconNode's letter
    **/
    public char getLetter() {
      return letter;
    }

    /**
    * Accessor method for this LexiconNode's children
    * @return this LexiconNode's children
    **/
    public Vector<LexiconNode> getChildren() {
      return children;
    }

    /**
    * Mutator method to set this LexiconNode's isWord flag
    * @param bool value of the isWord flag
    * @post isWord is set to bool
    **/
    public void setIsWord(boolean bool) {
      isWord = bool;
    }

    /**
    * Accessor method for this LexiconNode's isWord flag
    * @return this LexiconNode's isWord flag
    **/
    public boolean isWord() {
      return isWord;
    }

    /**
    * Add LexiconNode child to correct position in children
    * @param ln is the child to add
    * @post child is added to children maintaining alphabetical order, unless
    * the child already exists in children
    **/
    public void addChild(LexiconNode ln) {

      // check to see if letter is already in children
       if (getChild(ln.getLetter()) != null) {
         return;
       }

      // otherwise add child to children maintaining alphabetical order
      for (int i = 0; i < children.size(); i++) {
        if (ln.compareTo(children.get(i)) < 0) {
          children.add(i, ln);
          return;
        }
      }
      children.addLast(ln);
    }

    /**
    * Get LexiconNode child for 'ch' out of children
    * @param ch is the character stored in the LexiconNode to get
    * @return this LexiconNode's child with 'ch',
    **/
    public LexiconNode getChild(char ch) {

      for (int i = 0; i < children.size(); i++) {
        LexiconNode currentNode = children.get(i);
        if (currentNode.getLetter() == ch) {
          return currentNode;
        }
      }
      return null;
    }

    /**
    * Remove LexiconNode child for 'ch' from child data structure
    * @param ch the character of the LexiconNode to be removed
    * @post the child, if in children, is removed from children
    **/
    public void removeChild(char ch) {
      LexiconNode node = getChild(ch);
      children.remove(node);
    }

    /**
    * Creates an iterator that iterates over children in alphabetical order
    * @return an iterator
    **/
    public Iterator<LexiconNode> iterator() {
        return children.iterator();
     }

    // public static void main(String[] args) {
    //
    //    LexiconNode root = new LexiconNode('+', true);
    //    root.addChild(new LexiconNode('b', false));
    //    root.addChild(new LexiconNode('b', false));
    //    root.addChild(new LexiconNode('a', false));
    //    root.addChild(new LexiconNode('c', false));
    //
    //    Vector<LexiconNode> children = root.getChildren();
    //
    //    for (LexiconNode child : children) {
    //       System.out.println(child.getLetter());
    //    }
    //
    //  }

}
