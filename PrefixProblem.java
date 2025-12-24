public class PrefixProblem {
    static class Node{
        Node [] children = new Node[26];
        boolean endOfWord = false;
        int freq;

        public Node() {
            for(int i=0; i<children.length;i++){
                children[i] = null;
            }
            freq =1;
        }
    }
    public static Node root =new Node();

    public static void insert(String words){
        Node curr = root;
        for(int i=0;i < words.length();i++){
            int idx = words.charAt(i)-'a';
            if(curr.children[idx]==null){
                curr.children[idx] = new Node();
            }else{
                curr.children[idx].freq++;
            }
            curr = curr.children[idx];
        }
        boolean endOfWord = true;
    }

    public static int countUnique(Node root){
        if(root == null){
            return 0;
        }
        int count = 0;
        for(int i=0; i< root.children.length;i++){
            if(root.children[i] != null){
                count+=countUnique(root.children[i]);
            }
        }
        return count+1;
    }

    public static void findPrefix(Node root, String ans){
        if(root == null){
            return;
        }
        if(root.freq ==1){
            System.out.print(ans+" ");
            return;
        }
         for(int i=0;i<root.children.length;i++){
            if(root.children[i]!=null){
                findPrefix(root.children[i], ans+(char)(i+'a'));
            }
         }
    }
    // public static void main(String[] args) {
    //    // String arr[] = {"dog", "dove", "zebra", "duck", "cat"};
    //    String str ="ababa";
    //     for(int i=0; i <str.length();i++){
    //         String suffix = str.substring(i);
    //         insert(suffix);
    //      // root.freq =-1; // because frequency will not stop at root
    //      // findPrefix(root, "");
    //     }
    //  System.out.println(countUnique(root));
    // }

    public static String ans="";
    public static void longestWord(Node root, StringBuilder temp){
        if(root== null){
            return;
        }

        for(int i=0;i<26;i++){
            if(root.children[i]!=null && root.children[i].endOfWord ==true){
                char ch = (char)(i+'a');
                temp.append(ch);
                if(temp.length()>ans.length()){
                   ans = temp.toString();
                }

                longestWord(root.children[i], temp);
                temp.deleteCharAt(temp.length()-1);
            }
        }
    }
    public static void main(String[] args) {
        String words[]= {"a","app","appl","banana","apple","apply"};
        for(int i=0;i<words.length;i++){
            insert(words[i]);
        }

        longestWord(root, new StringBuilder(""));
        System.out.println(ans);
    }
}