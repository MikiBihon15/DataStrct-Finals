import java.util.Scanner;
import java.util.ArrayList; // Added for internal list handling in tree traversal

public class Library {

    // --- Data Model: LibraryBook ---
    public static class LibraryBook {
        public int isbn; 
        public String title;
        public String author;

        public LibraryBook(int isbn, String title, String author) {
            this.isbn = isbn;
            this.title = title;
            this.author = author;
        }

        @Override
        public String toString() {
            return String.format("[ISBN: %d, Title: %s, Author: %s]", isbn, title, author);
        }
    }

    // --- Helper Nodes for Linked List and Tree ---
    private static class ListNode {
        public LibraryBook data;
        public ListNode next;
        public ListNode(LibraryBook data) { this.data = data; this.next = null; }
    }
    private static class TreeNode {
        public LibraryBook data;
        public TreeNode left, right;
        public TreeNode(LibraryBook data) { this.data = data; this.left = this.right = null; }
    }

    // --- 1. Custom Array List (New Arrivals Shelf) ---
    public static class CustomArrayList {
        private LibraryBook[] arr;
        private int size;
        private static final int INIT_CAP = 5;

        public CustomArrayList() {
            this.arr = new LibraryBook[INIT_CAP];
            this.size = 0;
        }

        private void expand() {
            LibraryBook[] newArr = new LibraryBook[arr.length * 2];
            System.arraycopy(arr, 0, newArr, 0, size);
            arr = newArr;
        }
        
        public void add(LibraryBook item, int idx) {
            if (idx < 0 || idx > size) throw new IndexOutOfBoundsException("Index: " + idx);
            if (size == arr.length) expand();
            
            for (int i = size; i > idx; i--) arr[i] = arr[i - 1];
            
            arr[idx] = item;
            size++;
        }

        public LibraryBook search(String title) {
            for (int i = 0; i < size; i++) {
                if (arr[i].title.equalsIgnoreCase(title)) return arr[i];
            }
            return null;
        }
        
        public void sort() {
            for (int i = 0; i < size - 1; i++) {
                for (int j = 0; j < size - 1 - i; j++) {
                    if (arr[j].isbn > arr[j + 1].isbn) {
                        LibraryBook temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            }
        }

        public void clear() {
            this.arr = new LibraryBook[INIT_CAP];
            this.size = 0;
        }

        public void listAll() {
            if (size == 0) { System.out.println(" (New Arrivals Shelf is empty.)"); return; }
            System.out.println(" [New Arrivals Shelf Contents (Size: " + size + ")]:");
            for (int i = 0; i < size; i++) {
                System.out.println(" " + (i + 1) + ". " + arr[i]);
            }
        }
        
        // Method to retrieve all books in the array list
        public ArrayList<LibraryBook> getAllBooksAsList() {
            ArrayList<LibraryBook> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add(arr[i]);
            }
            return list;
        }
        
        public int size() { return size; }
    }

    // --- 2/3/4. Custom Linked List (Circulation List, Stack, Queue) ---
    public static class CustomLinkedList {
        private ListNode head; 
        private int size;

        public CustomLinkedList() { this.head = null; this.size = 0; }

        public void addAtStart(LibraryBook item) {
            ListNode newNode = new ListNode(item);
            newNode.next = head;
            head = newNode;
            size++;
        }

        public void addAtEnd(LibraryBook item) {
            if (head == null) { addAtStart(item); return; }
            ListNode current = head;
            while (current.next != null) current = current.next;
            current.next = new ListNode(item);
            size++;
        }

        public void addAtIndex(LibraryBook item, int idx) {
            if (idx < 0 || idx > size) throw new IndexOutOfBoundsException("Index: " + idx);
            if (idx == 0) { addAtStart(item); return; }
            
            ListNode current = head;
            for (int i = 0; i < idx - 1; i++) current = current.next;
            
            ListNode newNode = new ListNode(item);
            newNode.next = current.next;
            current.next = newNode;
            size++;
        }

        public LibraryBook search(String title) {
            ListNode current = head;
            while (current != null) {
                if (current.data.title.equalsIgnoreCase(title)) return current.data;
                current = current.next;
            }
            return null;
        }

        public void sort() {
            if (size <= 1) return;
            for (int i = 0; i < size; i++) {
                ListNode curr = head;
                ListNode next = head.next;
                for (int j = 0; j < size - 1 - i; j++) {
                    if (curr.data.isbn > next.data.isbn) {
                        LibraryBook temp = curr.data;
                        curr.data = next.data;
                        next.data = temp;
                    }
                    curr = next;
                    next = next.next;
                }
            }
        }

        public void clear() { this.head = null; this.size = 0; }
        
        public void listAll(String name) {
            if (head == null) { System.out.println(" (" + name + " is empty.)"); return; }
            ListNode current = head;
            int count = 1;
            System.out.println(" [" + name + " Contents (Size: " + size + ")]:");
            while (current != null) {
                System.out.println(" " + count++ + ". " + current.data);
                current = current.next;
            }
        }
        
        public LibraryBook pop() { // Stack POP (LIFO)
            if (head == null) return null;
            LibraryBook data = head.data;
            head = head.next;
            size--;
            return data;
        }
        public LibraryBook dequeue() { return pop(); } // Queue DEQUEUE (FIFO)
        public int size() { return size; }
    }

    // --- 5. Custom Binary Search Tree (Main Catalog Index) ---
    public static class CustomTree {
        private TreeNode root;

        public void add(LibraryBook item) { root = insertR(root, item); }
        private TreeNode insertR(TreeNode root, LibraryBook item) {
            if (root == null) return new TreeNode(item);
            if (item.isbn < root.data.isbn) {
                root.left = insertR(root.left, item);
            } else { 
                root.right = insertR(root.right, item);
            }
            return root;
        }

        public LibraryBook search(int isbn) { return searchR(root, isbn); }
        private LibraryBook searchR(TreeNode root, int isbn) {
            if (root == null || root.data.isbn == isbn) return root == null ? null : root.data;
            return isbn < root.data.isbn ? searchR(root.left, isbn) : searchR(root.right, isbn);
        }

        public void clear() { this.root = null; }

        public void listAll() {
            if (root == null) { System.out.println(" (Catalog Index is empty.)"); return; }
            System.out.println(" [Main Catalog Index (Sorted by ISBN)]:");
            inOrderR(root);
        }
        
        private void inOrderR(TreeNode node) {
            if (node != null) {
                inOrderR(node.left);
                System.out.println(" " + node.data);
                inOrderR(node.right);
            }
        }
        
        // New method to retrieve all books in a list for the combined view
        public ArrayList<LibraryBook> getAllBooks() {
            ArrayList<LibraryBook> allBooks = new ArrayList<>();
            getAllBooksR(root, allBooks);
            return allBooks;
        }
        
        // Recursive helper to perform in-order traversal and collect books
        private void getAllBooksR(TreeNode node, ArrayList<LibraryBook> list) {
            if (node != null) {
                getAllBooksR(node.left, list);
                list.add(node.data);
                getAllBooksR(node.right, list);
            }
        }
    }

    // --- Driver and Menu Logic ---
    private static final Scanner scanner = new Scanner(System.in);
    private static final CustomArrayList newArrivals = new CustomArrayList();
    private static final CustomLinkedList circulation = new CustomLinkedList(); 
    private static final CustomTree catalogDB = new CustomTree();
    private static int nextISBN = 1000;

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println(" UNIVERSITY BIOLOGY CATALOG DRIVER (5 Structures Demo) ");
        System.out.println("===============================================================");
        
        // Initial setup for Biology/Zoology theme
        newArrivals.add(new LibraryBook(101, "Ecology of Coral Reefs", "Vernon"), 0);
        circulation.addAtEnd(new LibraryBook(501, "Marine Mammal Behavior", "Lee"));
        catalogDB.add(new LibraryBook(555, "Introduction to Cell Biology", "Chen"));
        nextISBN = 1000;

        mainMenuLoop();
        scanner.close();
    }

    private static void mainMenuLoop() {
        int choice;
        do {
            System.out.println("\n---------------------------------------------------------");
            System.out.println("--- MAIN MENU ---");
            System.out.println("1. Add New Book (Shelf/Index)");
            System.out.println("2. Circulation / Returns (Linked List, Stack, Queue)");
            System.out.println("3. Search Catalog (Tree)");
            System.out.println("4. Show All Books (Shelf + Catalog)"); // NEW FEATURE
            System.out.println("5. Exit");
            System.out.print("Choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else { scanner.nextLine(); choice = 0; continue; }

            switch (choice) {
                case 1: addBookMenu(); break;
                case 2: circulationMenu(); break;
                case 3: treeMenu(); break;
                case 4: listAllBooks(); break; // NEW CASE
                case 5: System.out.println("\nSystem Shut Down. Goodbye!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    }
    
    private static LibraryBook getNewItem(String action) {
        System.out.print("\nACTION: " + action + "\nEnter Book Title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) { System.out.println("Title cannot be empty."); return null; }
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine().trim();
        return new LibraryBook(nextISBN++, title, author);
    }
    
    private static void printSearch(LibraryBook item) {
        System.out.println("Result: " + (item != null ? "FOUND " + item : "Not found."));
    }

    // --- NEW: Consolidated List Feature ---
    private static void listAllBooks() {
        System.out.println("\n--- COMPREHENSIVE LIBRARY INVENTORY ---");
        
        // 1. Get New Arrivals (Array List)
        System.out.println("\n[A. New Arrivals Shelf Contents (Array List)]");
        newArrivals.listAll();

        // 2. Get Main Catalog (BST)
        ArrayList<LibraryBook> catalogBooks = catalogDB.getAllBooks();
        System.out.println("\n[B. Main Catalog Index Contents (BST)] (Total: " + catalogBooks.size() + " books, sorted by ISBN)");
        if (catalogBooks.isEmpty()) {
            System.out.println(" (Catalog Index is empty.)");
        } else {
            for (int i = 0; i < catalogBooks.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + catalogBooks.get(i));
            }
        }
        System.out.println("----------------------------------------");
    }
    
    // --- Add Book Menu: Array List and Tree Insertion ---
    private static void addBookMenu() {
        int choice;
        do {
            System.out.println("\n--- ADD NEW BOOK ---");
            System.out.println("1. Add to New Arrivals Shelf (Array List)");
            System.out.println("2. Add to Main Catalog Index (Tree)");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choice: ");

            if (!scanner.hasNextInt()) { scanner.nextLine(); continue; }
            choice = scanner.nextInt(); scanner.nextLine();

            LibraryBook newBook;
            switch (choice) {
                case 1: 
                    newBook = getNewItem("Add to Shelf");
                    if (newBook == null) break;
                    newArrivals.add(newBook, newArrivals.size()); 
                    System.out.println("Book added to New Arrivals Shelf. Assigned ISBN: " + newBook.isbn); 
                    break;
                case 2:
                    newBook = getNewItem("Add to Catalog");
                    if (newBook == null) break;
                    catalogDB.add(newBook); 
                    System.out.println("Book added to Main Catalog (Indexed by ISBN). Assigned ISBN: " + newBook.isbn); 
                    break;
                case 3: return;
                default: System.out.println("Invalid choice.");
            }
        } while (true);
    }

    // --- Circulation Menu: Linked List, Stack, and Queue Operations ---
    private static void circulationMenu() {
        int choice;
        do {
            System.out.println("\n--- CIRCULATION AND SERVICE DESK ---");
            circulation.listAll("Circulation Queue");
            System.out.println("\n--- OPERATIONS ---");
            
            // Stack/Queue/Linked List Choices
            System.out.println("1. Checkout (Queue: ENQUEUE)");
            System.out.println("2. Process Returns (Stack: POP)");
            System.out.println("3. Process Checkout (Queue: DEQUEUE)");
            System.out.println("4. Search Circulation List");
            System.out.println("5. Sort Circulation List");
            System.out.println("6. Show New Arrivals Shelf");
            System.out.println("7. Back to Main Menu");
            System.out.print("Choice: ");

            if (!scanner.hasNextInt()) { scanner.nextLine(); continue; }
            choice = scanner.nextInt(); scanner.nextLine();

            switch (choice) {
                case 1: circulation.addAtEnd(getNewItem("Checkout")); System.out.println("Book added to checkout queue."); break;
                case 2: LibraryBook pop = circulation.pop(); 
                        System.out.println("PROCESSED RETURN (Stack): " + (pop != null ? pop.title : "Queue is empty.")); break;
                case 3: LibraryBook deq = circulation.dequeue();
                        System.out.println("PROCESSED CHECKOUT (Queue): " + (deq != null ? deq.title : "Queue is empty.")); break;
                case 4: System.out.print("Title to search: "); printSearch(circulation.search(scanner.nextLine())); break;
                case 5: circulation.sort(); System.out.println("Circulation list sorted by ISBN."); break;
                case 6: newArrivals.listAll(); break;
                case 7: return;
                default: System.out.println("Invalid choice.");
            }
        } while (true);
    }

    // --- Tree Menu: BST Search and Traversal ---
    private static void treeMenu() {
        int choice;
        do {
            System.out.println("\n--- MAIN CATALOG INDEX (BST) ---");
            catalogDB.listAll();
            System.out.println("\n--- OPERATIONS ---");
            System.out.println("1. Search by ISBN");
            System.out.println("2. Clear Index");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choice: ");

            if (!scanner.hasNextInt()) { scanner.nextLine(); continue; }
            choice = scanner.nextInt(); scanner.nextLine();

            switch (choice) {
                case 1: 
                    System.out.print("Enter ISBN to search: ");
                    if (!scanner.hasNextInt()) { scanner.nextLine(); break; }
                    int isbn = scanner.nextInt(); scanner.nextLine();
                    printSearch(catalogDB.search(isbn)); break;
                case 2: catalogDB.clear(); System.out.println("Catalog index cleared."); break;
                case 3: return;
                default: System.out.println("Invalid choice.");
            }
        } while (true);
    }
}