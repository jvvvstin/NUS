import java.util.ArrayList;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';
    final char EMPTY = '\0';

    private class TrieNode {
        // TODO: Create your TrieNode class here.
        public char key;
        public TrieNode[] children = new TrieNode[63];
        public boolean end;
        public boolean isEmpty = false;

        public TrieNode search(char key) {
            return this.children[charToIndex(key)];
        }

        public boolean contains(char key) {
            return this.children[charToIndex(key)] != null;
        }

        public void insert(TrieNode node) {
            if (node.key == EMPTY) {
                node.end = true;
                node.isEmpty = true;
            }
            this.children[charToIndex(node.key)] = node;
        }

        public TrieNode() {
            this.key = '\0';
            this.isEmpty = true;
            this.end = false;
        }

        public TrieNode(char key) {
            this.key = key;
            this.end = false;
        }
    }

    public TrieNode root = null;

    public int charToIndex(char c) {
        if (c == EMPTY) {
            return 0;
        }
        if (c <= 57) {
            return c - 47;
        } else if (c >= 65 && c <= 90) {
            return c - 54;
        } else {
            return c - 60;
        }
    }

    public char indexToChar(int index) {
        if (index == 0) {
            return EMPTY;
        }
        if (index <= 10) {
            return (char) (index + 47);
        } else if (index <= 36) {
            return (char) (index + 54);
        } else {
            return (char) (index + 60);
        }
    }

    public Trie() {
        // TODO: Initialise a trie class here.
        this.root = new TrieNode();
    }

    /**
     * Inserts string s into the Trie.
     *
     * @param s string to insert into the Trie
     */
    void insert(String s) {
        // TODO
        if (s == null) {
            return;
        }
        if (s.isEmpty()) {
            TrieNode node = new TrieNode(EMPTY);
            this.root.insert(node);
        }
        TrieNode node = this.root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!node.contains(c)) {
                node.insert(new TrieNode(c));
            }
            node = node.search(c);
        }
        node.insert(new TrieNode(EMPTY));
        this.root.isEmpty = false;
    }

    /**
     * Checks whether string s exists inside the Trie or not.
     *
     * @param s string to check for
     * @return whether string s is inside the Trie
     */
    boolean contains(String s) {
        // TODO
        if (this.root == null) {
            return false;
        }
        if (this.root.isEmpty) {
            return false;
        }
        if (s.isEmpty()) {
            return this.root.contains(EMPTY);
        }
        TrieNode node = this.root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (!node.contains(c)) {
                return false;
            }
            if (i == s.length() - 1) {
                TrieNode child = node.search(c);
                return child.contains(EMPTY);
            }
            node = node.search(c);
        }

        return true;
    }

    /**
     * Searches for strings with prefix matching the specified pattern sorted by lexicographical order. This inserts the
     * results into the specified ArrayList. Only returns at most the first limit results.
     *
     * @param s       pattern to match prefixes with
     * @param results array to add the results into
     * @param limit   max number of strings to add into results
     */
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        // TODO
        if (this.root.isEmpty) {
            return;
        }
        TrieNode node = this.root;
        int[] limitArr = { limit };
        search(s, results, limitArr, node, "");
    }

    public void search(String s, ArrayList<String> results, int[] limitArr, TrieNode node, String current) {
        // if string is empty
        if (s.isEmpty()) {
            // check if results list has exceeded the limit
            if (results.size() == limitArr[0]) {
                return;
            }
            // check if the current node has a terminating character, which is placed in index 0
            if (node.children[0] != null) {
                // if there exists a terminating character, append the current node letter to the current word
                StringBuilder sb = new StringBuilder(current);
                sb.append(node.key);
                // add the word to the results list
                results.add(sb.toString());
            }
            // check if the current node is a terminating character
            if (node.key != EMPTY) {
                // if the current node is not a terminating character, means there are more child nodes
                StringBuilder sb = new StringBuilder(current);
                sb.append(node.key);
                // update the current string to include the most recent node's character
                current = sb.toString();
            }
            // loop through each children node
            for (int i = 0; i < node.children.length; i++) {
                // checks if the character node exists
                if (node.children[i] != null) {
                    // if it exists, recursively search for all the possible words
                    search(s, results, limitArr, node.children[i], current);
                }
            }
            return;
        }
        char c = s.charAt(0);
        // updates the remaining string left to be searched to be one less than the entire string
        // i.e. if string is originally "pe", now it will be "e" that is passed to the recursive search function
        String newString = s.substring(1);
        // checks if the first character of the string to be searched is a WILDCARD '.'
        if (c == WILDCARD) {
            // if it is, recursively search all the child nodes of the current node
            for (int i = 0; i < node.children.length; i++) {
                // checks that the character node exists
                if (node.children[i] != null) {
                    // checks that the character node that exists, is not a terminating character
                    if (node.children[i].key != EMPTY) {
                        // this check prevents the additional adding of the child node, since it will be done
                        // by recursive search function, once the string passed in is an empty string ""
                        // if not, the result would have an extra character after the string passed in
                        // i.e. if string passed in was pe, then all results would have an extra of the current node
                        // such as peeck, peeter etc.
                        if (s.length() > 1) {
                            StringBuilder sb = new StringBuilder(current);
                            sb.append(node.children[i].key);
                            search(newString, results, limitArr, node.children[i], sb.toString());
                        } else {
                            search(newString, results, limitArr, node.children[i], current);
                        }
                    }
                }
            }
            return;
        }
        // if the character was not a WILDCARD '.', checks if the current node has the next letter in its children
        if (node.contains(c)) {
            // if it has, get the child node
            TrieNode child = node.search(c);
            // similarly, this check is needed to ensure that we do not double add the current node's character to
            // result. the adding of the node's character will be done by the search function's recursive call when
            // the string is empty "". removing this check will result in an additional letter (belonging to the last
            //  child node) being added to the end of the result, i.e. peter becomes peterr
            if (s.length() > 1) {
                StringBuilder sb = new StringBuilder(current);
                sb.append(child.key);
                search(newString, results, limitArr, child, sb.toString());
            } else {
                search(newString, results, limitArr, child, current);
            }
        }
    }

    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }


    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("a");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");

        String[] result1 = t.prefixSearch("pi..ed", 10);
        String[] result5 = t.prefixSearch("", 10);

        String[] result2 = t.prefixSearch("pe", 10);
        String[] result3 = t.prefixSearch("pe.e.", 10);
        String[] result4 = t.prefixSearch("pe", 10);

        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}
