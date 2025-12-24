package Tries;

public class Trie {
    static class Node{
        Node children[] = new Node[26];
        boolean endOfWord = false;
        public Node(){
            for(int i=0;i<children.length;i++){
                children[i]= null;
            }
        }
    }
    public static Node root = new Node();

    public static void insert(String word){
        Node curr = root;
        for(int level= 0; level<word.length();level++){
        int idx = word.charAt(level)-'a';

        if(curr.children[idx] == null){
            curr.children[idx] = new Node();
        }
        curr = curr.children[idx];
        }
        curr.endOfWord = true;
    }

    public static boolean searchKey(String key){
        Node curr =root;
        for(int i=0;i<key.length();i++){
            int idx = key.charAt(i)-'a';
            if(curr.children[idx]==null){
                return false;
            }
            curr = curr.children[idx];
        }
        return curr.endOfWord ==true;
    }

    public static boolean wordBreak(String key){
        if(key.length() ==0){
            return true;
        }
        for(int i=1; i<=key.length();i++){
            if(searchKey(key.substring(0,i)) && wordBreak(key.substring(i))){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        //1
        // String words[] = {"the", "a","thee","any","there","their"};
        // for(int i=0;i<words.length;i++){
        //     insert(words[i]);
        // }
        // //2
        // System.out.println(searchKey("thee"));
        // System.out.println(searchKey("thor"));
        String words[]={"i","like","sam","samsung","mobie","ice"};
        for(int i=0;i<words.length;i++){
            insert(words[i]);
        }
        String key = "ilikesamsung";
        System.out.println(wordBreak(key));
    }
}
