import javax.swing.tree.TreeNode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Question2 {
    private TrieNode root;          //root of your constructed trie
    private ArrayList<String> words;        //words to construct trie from
    /*Do not change this method */
    public Question2() {
        words = new ArrayList<>();
        root = null;
    }
    /*Do not change this method */
    public static void main(String[] args) {
        Question2 t = new Question2();
        t.readFromFile("words.txt");
        t.constructTree();
        if (t.root != null) {
            System.out.println(t.getWords(t.root, ""));
        }

    }
    /*
    Your implementation. Constructs a tree for the words given in the words arraylist. Sets the root node equal to the root of the trie. You should not require any additional classes/methods.
     */
    public void constructTree() {
        root=new TrieNode(' ');
        for (String w:words) {
           TrieNode trieNode=root;
            for (char c:w.toCharArray()) {
               trieNode=trieNode.getChildren().computeIfAbsent(c,cur->new TrieNode(c));
            }
        }
    }
    /*Do not change this method */
    public String getWords(TrieNode curNode, String curWord) {
        String result = "";

        char curChar = curNode.getLetter();

        if (curNode.getChildren().size() == 0) {
            return curWord.substring(1) + curChar + "\n";
        } else {
            for (TrieNode t : curNode.getChildren().values()) {
                result += getWords(t,curWord + curChar);
            }
            return result;
        }

    }
    /*Do not change this method */
    public void readFromFile(String fileName) {
        try {
            Scanner s = new Scanner(new File(fileName));
            while (s.hasNext()) {
                words.add(s.nextLine().trim());
            }
        } catch (Exception e) {
            System.out.println("Could not load file");
        }

    }


    /*Do not change this class */
    private class TrieNode {
        char letter;
        HashMap<Character,TrieNode> children;

        public TrieNode(char letter) {
            this.letter = letter;
            children = new HashMap<>();
        }

        public HashMap<Character, TrieNode> getChildren() {
            return children;
        }

        public char getLetter() {
            return letter;
        }
    }



}
