import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.batik.dom.util.HashTable;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Defines the structure for node in a singly linked list.
 * Author: Robert Saunders
 */
class SinglyLinkedListNode {
    public int value;
    public SinglyLinkedListNode next;

    /**
     * Default constructor for a node in a singly linked list.
     * Defaults next to null and value to zero.
     */
    SinglyLinkedListNode() {
        this.value = 0;
        this.next = null;
    }

    /**
     * SinglyLinkedListNode constructor that takes a value.
     * Defaults next to be null.
     * @param value Value to be set as node value.
     */
    SinglyLinkedListNode(int value) {
        this.value = value;
        this.next = null;
    }

    /**
     * SinglyLinkedListNode constructor that
     * @param value The node value to be set.
     * @param next The next node to be set.
     */
    SinglyLinkedListNode(int value, SinglyLinkedListNode next) {
        this.value = value;
        this.next = next;
    }
}

/**
 * Implementation of a singly linked list.
 * Singly linked list only have one way traversal.
 * Author: Robert Saunders
 */
public class SinglyLinkedList {


    public SinglyLinkedListNode head = null; // pointer to the head of the list
    public int size = 0; // counter to keep track of the size of the list

    /**
     * Constructor for linked list that sets initial head.
     * @param head The head node to be initially set.
     */
    SinglyLinkedList(SinglyLinkedListNode head) {
        this.head = head;
        this.size = 1;
    }

    /**
     * Checks if the list is empty.
     * @return True if empty.
     */
    public Boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Adds a node to the end of a linked list.
     * @param singlyLinkedListNodeToAdd The node to add to the list.
     */
    public void addNodeToTail(SinglyLinkedListNode singlyLinkedListNodeToAdd) {
        // first check if the list is empty
        // if so set the node to add as the head
        if (this.isEmpty()) {
            this.head = singlyLinkedListNodeToAdd;
            return;
        }

        // start at the head
        // traverse down the list until end
        SinglyLinkedListNode node = this.head;

        while (node.next != null) {
            node = node.next;
        }

        // set the new tail of the list
        node.next = singlyLinkedListNodeToAdd;
    }

    /**
     * Adds a node to the head of a linked list.
     * @param singlyLinkedListNodeToAdd The node to add to the list.
     */
    public void addNodeToHead(SinglyLinkedListNode singlyLinkedListNodeToAdd) {

        // check if the linked list is empty
        // set the head to be new node to add
        if (this.isEmpty()) {
            this.head = singlyLinkedListNodeToAdd;
            return;
        }

        // store the current head
        // set the new head
        // update the new heads next to be the old head
        SinglyLinkedListNode node = this.head;
        this.head = singlyLinkedListNodeToAdd;
        this.head.next = node;
    }

    /**
     * Deletes a node in a linked list, only using the reference to the node to delete.
     * @param nodeToDelete The node to delete.
     */
    public void deleteNode(SinglyLinkedListNode nodeToDelete) {
        // cannot solve this problem without a node to delete
        // or if the node to delete is the last in the list
        if (nodeToDelete == null || nodeToDelete.next == null) {
            return;
        }

        // instead of deleting the node to delete
        // we modify it with the contents of the next node
        // subsequently deleting that node instead
        SinglyLinkedListNode nextNode = nodeToDelete.next;
        nodeToDelete.value = nextNode.value;
        nodeToDelete.next = nextNode.next;
    }

    /**
     * Removes duplicates from a linkedlist, this method uses a buffer to keep track of values.
     * See below for alternative method that doesn't use a buffer.
     */
    public void removeDuplicatesWithBuffer() {

        // first check if the linked list is empty
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot remove duplicates from an empty list!");
        }

        // create a buffer to store values to cross references against
        // create a current (conductor) for list traversal
        // initialize a prev node pointer to update as we move through the list
        HashMap<Integer, Boolean> buffer = new HashMap<>();
        SinglyLinkedListNode current = this.head;
        SinglyLinkedListNode prev = null;

        // traverse the list
        // check buffer, remove node if needed
        // otherwise add to buffer and move to next
        while (current != null) {
            if (buffer.containsKey(current.value)) {
                prev.next = current.next;
            }
            else {
                buffer.put(current.value, true);
                prev = current;
            }
            current = current.next;
        }
    }

    /**
     * Removes duplicates from a linkedlist, this method does not use a buffer.
     * Instead we use a runner to compare are nodes prior to current node.
     */
    public void removeDuplicatesWithoutBuffer() {

        // first check if the list is empty
        // can't find duplicates if the list is empty
        if (isEmpty()) {
            throw new IllegalArgumentException("The list is empty so there can't be any duplicates!");
        }

        // have to start one behind to begin runner
        // set current to be the prev next
        SinglyLinkedListNode prev = this.head;
        SinglyLinkedListNode current = prev.next;

        // iterate through the list until end
        while (current != null) {
            // create a runner, runner always starts are head
            SinglyLinkedListNode runner = head;

            // keep running until runner reaches current
            while (runner != current) {
                // if the runner value and the current are equal
                // lets delete current and move to next iteration
                if (runner.value == current.value) {
                    SinglyLinkedListNode tmp = current.next;
                    prev.next = tmp;
                    current = tmp;
                    break;
                }
                runner = runner.next;
            }

            // if the runner reaches the current
            // update prev and current for next run
            if (runner == current) {
                prev = current;
                current = current.next;
            }
        }
    }

    public void findNthToLastNodeIterative(int n) {

    }

    /**
     * Reverses a linked list.
     */
    public void reverseList() {
        SinglyLinkedListNode prev = null; // create a pointer to prev node for reference
        SinglyLinkedListNode current = this.head; // set the current node to head
        SinglyLinkedListNode next = null; // create a point to next node for reference

        while (current != null) {
            next = current.next; // first update the next node
            current.next = prev; // make the current nodes next be the new previous, i.e. swap order
            prev = current; // update the previous
            current = next; // update the current
        }
        this.head = prev; // set head to be the prev
    }

    /**
     * Checks if a value is in the linked list.
     * @param value The value to search for.
     * @return True if the value is in the linked list.
     */
    public Boolean isValuePresent(int value) {

        // first check if the list is empty
        // if it is empty the value can't be present
        if (this.isEmpty()) {
            return false;
        }

        // start at the head
        // traverse down the list until find the value
        SinglyLinkedListNode node = this.head;

        while (node.next != null) {
            if (node.value == value) {
                return true;
            }
            node = node.next;
        }

        return false;
    }

    /**
     * To string override for a linked list.
     * @return The string representation of a linked list.
     */
    @Override
    public String toString() {
        StringBuilder linkedListString = new StringBuilder();

        SinglyLinkedListNode node = this.head;

        while(node.next != null) { // go node by node appending them to the string until last one

            linkedListString.append(node.value + " -> ");

            node = node.next; // move to the next node
        }

        linkedListString.append(node.value); // add the last node to the string

        return linkedListString.toString();
    }

    /**
     * Main execution method for testing.
     * @param args Arguments passed into testing.
     */
    public static void main(String[] args) {

        SinglyLinkedListNode firstSinglyLinkedListNode = new SinglyLinkedListNode(4);

        SinglyLinkedList myList = new SinglyLinkedList(firstSinglyLinkedListNode);

        SinglyLinkedListNode secondSinglyLinkedListNode = new SinglyLinkedListNode(3);

        myList.addNodeToTail(secondSinglyLinkedListNode);

        SinglyLinkedListNode newHeadNode = new SinglyLinkedListNode(1);

        myList.addNodeToHead(newHeadNode);

        System.out.println("Initial List: " + myList.toString());

        myList.reverseList();

        System.out.println("Reversed List: " + myList.toString());

        System.out.println("Is four in the list? " + myList.isValuePresent(4));
        System.out.println("Is two in the list? " + myList.isValuePresent(2));

        LinkedList<Integer> myNewList = new LinkedList<>();

        myNewList.add(1);
        myNewList.add(3);
        myNewList.add(4);

        System.out.println(myNewList.toString());
    }
}
