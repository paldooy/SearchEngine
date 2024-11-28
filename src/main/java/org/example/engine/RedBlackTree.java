package org.example.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class RedBlackTree {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        String data;
        boolean color;
        Node left, right, parent;

        Node(String data) {
            this.data = data;
            this.color = RED; // New nodes are red by default
            left = right = parent = null;
        }
    }

    private Node root;
    private Node TNULL;

    // Preorder
    private void preOrderHelper(Node node) {
        if (node != TNULL) {
            System.out.print(node.data + " ");
            preOrderHelper(node.left);
            preOrderHelper(node.right);
        }
    }

    private void fixInsert(Node k) {
        Node u; // uncle

        while (k.parent != null && k.parent.color == RED) {
            if (k.parent == k.parent.parent.left) {
                u = k.parent.parent.right; // uncle
                if (u != null && u.color == RED) {
                    k.parent.color = BLACK;
                    u.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    rightRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.left; // uncle
                if (u != null && u.color == RED) {
                    k.parent.color = BLACK;
                    u.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    leftRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = BLACK;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public void insert(String key) {
        Node node = new Node(key);
        node.parent = null;
        node.data = key;
        node.left = TNULL;
        node.right = TNULL;

        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.data.compareTo(x.data) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data.compareTo(y.data) < 0) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = BLACK;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        fixInsert(node);
    }

    public List<String> getSuggestions(String prefix) {
        List<String> results = new ArrayList<>();
        getSuggestionsHelper(root, prefix, results);
        return results;
    }

    private void getSuggestionsHelper(Node node, String prefix, List<String> results) {
        if (node != TNULL) {
            if (node.data.startsWith(prefix)) {
                results .add(node.data);
            }
            getSuggestionsHelper(node.left, prefix, results);
            getSuggestionsHelper(node.right, prefix, results);
        }
    }


    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(Node node, String indent, boolean last) {
        if (node != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R---- "); // R untuk Right
                indent += "     "; // Tambahkan spasi untuk indentasi
            } else {
                System.out.print("L---- "); // L untuk Left
                indent += "|    "; // Tambahkan garis vertikal untuk menunjukkan hubungan
            }

            // Menampilkan warna berdasarkan status node
            String color = node.color == RED ? "\u001B[31m" : "\u001B[30m"; // Merah untuk red, Hitam untuk black
            System.out.println(color + node.data + "\u001B[0m"); // Reset warna setelah mencetak

            printTree(node.left, indent, false); // Traversal kiri
            printTree(node.right, indent, true);  // Traversal kanan
        }
    }
    private void printTreeHelper(Node node, String prefix, boolean isLeft) {
        if (node != TNULL) {
            System.out.print(prefix);
            System.out.print(isLeft ? "├── " : "└── ");
            System.out.println(node.data + (node.color == RED ? " (R)" : " (B)"));
            printTreeHelper(node.left, prefix + (isLeft ? "│   " : "    "), true);
            printTreeHelper(node.right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RedBlackTree tree = new RedBlackTree();
        tree.insert("Juara");
        tree.insert("J4ara");
        tree.insert("Java");
        tree.insert("JavaFX");
        tree.insert("JavaScript");
        tree.insert("JDBC");
        tree.insert("JUnit");
        tree.insert("youtube.com");

        tree.printTree();
    }
}