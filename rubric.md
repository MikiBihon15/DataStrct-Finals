# Project Assessment Rubric

**Project:** University Biology Catalog (Library System)
**Section:** C2A
**Course:** Data Structures
**Assessment Date:** December 1, 2025
**Student:** Mikhail Dustin C. Elpedes

## Grading Breakdown

### 1. Data Structure Completeness & Implementation (50%)

**Score:** 48/50

| Data Structure | Implementation           | Functions                                                    | Notes                                      |
| -------------- | ------------------------ | ------------------------------------------------------------ | ------------------------------------------ |
| ArrayList      | **Custom (Array-based)** | add(idx), search, sort (bubble), clear, listAll              | Dynamic resizing with expand()             |
| LinkedList     | **Custom (Singly)**      | addAtStart, addAtEnd, addAtIndex, search, sort, clear, listAll | Uses ListNode, all required functions      |
| Stack          | **Custom (via LinkedList)** | push (addAtStart), pop, listAll                           | LIFO operations properly implemented       |
| Queue          | **Custom (via LinkedList)** | enqueue (addAtEnd), dequeue, listAll                      | FIFO operations properly implemented       |
| Tree           | **Custom (BST)**         | add (recursive), search (recursive), clear, listAll (in-order) | TreeNode based, proper BST logic         |

**Deductions:**

- -2: Uses java.util.ArrayList for helper method (getAllBooksAsList), though core implementations are fully custom

**Positive Notes:**

- All 5 data structures implemented with ALL required functions
- Excellent code organization with inner classes
- LibraryBook data model with proper toString()
- Bubble sort implemented for both ArrayList and LinkedList
- Recursive BST insert and search
- Clean separation of concerns

### 2. Visualization (30%)

**Score:** 30/30

- Excellent menu-driven interface with main menu and submenus
- Clear section headers and formatting
- Shows contents after each operation
- Combined inventory view (listAllBooks) showing all structures
- Proper empty state handling ("Shelf is empty")
- Interactive circulation desk demo

### 3. Short Paper (20%)

**Score:** 20/20

- **File:** Documentation_Reflection.pdf
- Excellent reflection on implementation experience
- Discusses concept evolution (grocery store → library)
- Technical details: Scanner input buffer issues, managing 5 structures
- Covers challenges with ArrayList shifting, LinkedList separation
- Good learning outcomes and conclusions

---

## Final Grade: **98/100**

### Summary

Outstanding project with excellent implementation of all 5 data structures. The Library System theme is well-executed with a Biology/Zoology catalog concept. All required functions (add at start/end/index, list all, search, sort, clear) are implemented across all structures. The code is well-organized with proper inner classes and clean menu logic. The reflection paper provides excellent insight into the development process and challenges faced.

### Highlights

- All custom implementations (no reliance on Java built-ins for core logic)
- Complete function coverage across all 5 structures
- Professional menu-driven interface
- Thoughtful paper discussing real debugging challenges
- Solo project with solid commit history

---

_Assessment generated on December 1, 2025_
