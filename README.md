# Personal Interview Preparation

This repository includes a bunch of code that I have written in Java to implement various different data structures, algorithms and solutions to online problems. I am using this readme to act as a running guide of everything I have implemented and as a place for me to jot notes down on various pertinent things. I hope that if you stumble across this that it can help you understand a difficult concept that I gone through, I like to make simple analogy's between a concept and things in the real world as it helps me conceptualize it better so if you also benefit from that kind of thing this will likely help you.  

Utilised the following for my preparation: 

- [Cracking the Coding Interview](http://www.crackingthecodinginterview.com/)
- [Interview Cake](https://www.interviewcake.com)
- [HackerRank](https://www.hackerrank.com/)
- [CareerCup](https://www.careercup.com/)
- [LeetCode](https://leetcode.com/)

> NOTE: I may make reference to questions or concepts from these particular resources. I will not be adding the questions into this readme but I will walk through any solutions I feel the need to write down to clarify for my own benefit.

## Glossary
- [General Concepts](#concepts)
    - [Big O Notation](#bigonotation)
    - [Random Access Memory]()
    - [Binary Numbers]()
- [Data Structures](#datastructures)
    - Linked Lists (#linkedlist)
- Algorithms
- Language Notes
    - Java
    - JavaScript
    - Ruby
- Interview Tactics

## General Concepts

This section goes over some important concepts to understand before you dive into the thick of things. 

### Big O Notation

Big O notation is the language that is used for talking about how efficient an algorithm is. It is great because it allows us to compare algorithms fairly. We express the runtime in terms of how quickly it grows relative to the input, as the input gets arbitrarily large. The reason for this is because it is impossible to get the exact runtime because there are varying circumstances across different machines, such as processor etc. 

A couple of examples: 

```
public void printFirstElement(int[] strings) {
    System.out.println(strings[0]); // O(1)
}
```

This example has a time complexity of O(1), because as the input grows the runtime will not change, it will remain constant. 

Alternatively, this example:

```
public void printAllElements(int[] strings) {
    for (String string : strings) { // O(n)
        System.out.print(string);
    }
}
```

This example has a time complexity of O(n), because as the input grows arbitrarily so will our runtime. We express `n` to be the number of inputs, so in this example for every added input our runtime will grow linearly. 

One more example on the basics:

```
public void printAllPossibleOrderedPairs(int[] arrayOfItems) { // O(n*n) = O(n^2)
    for (int firstItem : arrayOfItems) { // O(n)
        for (int secondItem : arrayOfItems) { //O(n)
            int[] orderedPair = new int[]{firstItem, secondItem};
            System.out.println(Arrays.toString(orderedPair));
        }
    }
}

```

In this example, for every input we iterate `n` times, therefore our time complexity must be O(n*n) or O(n^2).

Those are just some very basic examples of asymptotic analysis (big o analysis), and when doing for larger problems it can become more complex, but there are some rules that can help us: 

Take the example: 

```
public void printThings(int[] array) { // O(2n)?? --> O(n)
    for (int item : array) { // O(n)
        System.out.println(item);
    }
    
    for (int item2 : array) { // O(n)
        System.out.println(item2);
    }
}
```

The time complexity of this is O(n + n), so really O(2n). But is it? Technically yes, however we throw out constants when doing analysis so the real runtime is only O(n). The reason we throw the constants out is because asymptotic analysis, as mentioned above, is analyzing the runtime relative to the input as the input becomes arbitrarily large. So as the input becomes larger, the constants become less significant so we can just completely ignore them. 

One more thing to consider when doing analysis, take the example: 

```
public void printThings(int[] array) { 
    for (int firstItem : array) { // O(n)
        for (int secondItem : array) { // O(n)
            System.out.println(secondItem);
        }
    } // O(n^2)
    
    int middleIndex = array.length/2;
    int index = 0;
    while (index < middleIndex) { // O(n/2)
        System.out.println(array[index]);
        index++;
    }
    
    
    for (int i = 0; i < 100; i++) { // O(100)
        System.out.println("Hello");
    }
}
```

Now we are adding some complexity, at first glance we can say the complexity of this is O(n^2 + n/2 + 100). First lets take out the constants as mentioned to do above, now we have O(n^2 + n). So now what? Well we drop the less significant terms, so our time complexity is really only O(n^2). The reason we drop the less significant terms is the same reason why we drop constants, because our analysis is examining the growth of our runtime as our input gets arbitrarily large, the larger the inputs get larger the less significant terms become less significant.  

In interviews it is important to express that you are saying that the big o of an algorithm is at its worst case, an algorithm is only as good as its worst runtime. It is nice to separate yourself by adding other cases into your description though, it can further prove to your interviewer that you know your stuff, take the example below:

```
public Boolean isNeedleInHaystack(int[] haystack, int needle) {
    for (int item : haystack) {
        if (item == needle) {
            return true;
        }
    }
    return false;
}

```

My explanation of the function above would be something like: "The worst case time complexity of this algorithm is O(n), because we cannot assume where the needle is in the haystack. However, it is definitely possible that the runtime could be O(1) if the needle is the first item in the haystack, therefore O(1) is our best case."

So far, all we've talked about is time complexity. Time complexity is great if we are trying to optimize our algorithm to be more time efficient. Sometimes we don't care as much about time as we do memory consumption, this is when we examine the space complexity of an algorithm.

## Random Access Memory

I know this isn't a computer architecture focused article, however understanding how the programs we write execute internally is critical to your understanding of which data structure is suitable for a scenario.

The most important is random access memory, also called RAM, RAM is a special type of storage a computer has that is fast and volatile. It is responsible for storing currently executing program data, so the variables you declare in your code etc. (note it does not store things like files, such as audio files etc. those go in non-volatile storage likely on the disk). For simplicity sake, you can think of RAM to be a big bookshelf, and each shelf has a unique address and holds exactly one byte (one byte is 8 bits). The really cool thing, and probably the most important thing, is that the processor communicates with RAM through a memory interface, this allows the processor to read and write data to RAM.
 
The memory interface is where all of the magic happens, it has a direct connection to every shelf in the bookshelf, this is what makes RAM so fast, we don't have to traverse the entire bookshelf, it can just go to the shelf that it is connected to directly, this is why in later sections you'll see that array access is O(1). This property of RAM is a large reason why it's called RAM, it can access a random memory address right away, no need for searching, a hard drive for example has to spin the disk to find the address it wants to save. 

There is also **one more thing** that the processor does proactively, this important to know, and its that the processor has its own super fast cache that it uses to store recently accessed items, this way it can grab things from its own cache without going out to RAM to get it, so this is even faster than RAM. Because of this cache the processor has, the processor can do some fancy things, such as when it reads from RAM it can grab the contents of the addresses in RAM that are nearby the one it intends to get. This is good because it assumes that data stored close together are related in some way and there is a chance that the nearby data may also have to be accessed soon, so proactively putting it in its cache is a good idea, this is why **storing data in sequential addresses** is faster. 

## Binary Numbers

Binary Numbers
- base 2 numbers (binary)
- base 10 numbers (decimal)
- sequential powers of two
- base 16 numbers (hexadecimal)
java does integer overflow
integers are 32 bits (YouTube is a classic example)
longs are 64 bits
fixed width numbers, take up constant space, simple operations also hace constant time because they are fixed width
tradeoff is that there is a cap to the number it can hold

twos complement

## Logarithms

logarithms are essentially asking what power do we need to raise the base to in order to get the result
what are they used for? good to solve for x when x is an exponent

often have to ask yourself how many times do we have to divide n to get to 1
or
how many times do we have to double 1 to get back to n

logb(b^x) = x

binary search, searching for number in sorted array, done by splitting O(logn)
merge sort, divide array in halves, sort the halves and then merge the halves back together, recursive call O(nlogn)

number of nodes on the last level of a binary tree is n+1/2, perfect tree
height is number of times we have to double 1 to get n+1/2
log2(n+1) for height, considering tree with 2 levels (3 nodes)
the base can be implied to be 2

## Triangular Series

a triangular series is a series of numbers where each number could be a row of an equilateral triangle

example 1,2,3,4,5

the sum of such is 15, making it a triangular number

series always starts with 1 and increases by 1 with each number
nice because no matter what we can find the sum of series


properties: 
pairs of numbers on each side will always add up to the same value 
the value will always be 1 more then series' n

total sum of a triangular series is n^2+n/2

## Recursion

A function that returns itself, base case and recursive case.
- recusion is prone to a stack overflow error, where our call stack is too big and runs out of space
- some languages do tail call optimization to prevent the call stack from being very large, performs optimizstion on recursive algorithms, Java, Python dont allow, some C, Ruby and Javascript implementations do
best to assume that our compiler or interpreter doesnt do this

## Dynamic Programming

optimal substrcuture
overlapping subproblems
the unbounded napsack problem
0/1 Knapsack

## Memoization

technique used to prevent overlapping subproblems, algorithms caches results and picks from there if already been computed
usually results are stored inside of a hash map
fibinocci is great example of this
common startegy for dynamic programming problems, problems that are coposed of solutions to the same problem with smaller inputs
another good strat, more common is going bottom up 

## Bottom Up (Tabulation)

- a way to avoid recursion, as recursion has a memory cost because of the call stack it creates
- typically start at the beginning, whereas recursion starts at end and works its way back

## Data Structures

### Arrays

Arrays
O(1) lookup (most important property) because of memory inteface and ram direct connection
however only works if each item in array is same size (bytes)
array is contiguous in memory, no gaps
these properties make the array predictable

limitations, all data needs to be the same size (same), and if large then ram needs a lot of contiguous space, which is rare

#### Pointer Arrays

Pointers
helps wasted space problem
think baby names
fixes array limitations
although not cache-friendly
still O(1) for the sake of big o notation

#### Strings

Strings
mapping of numbers to character is called character encoding
one type of character encoding is called ASCII

#### Dynamic Arrays

Dynamic Arrays
think word processor, don't know the users input before hand
built on top of normal arrays
Javascript, Ruby and Python all use dynamic arrays by default
when capacity is full it will make a new bigger array
copy each element into new array
free up original space 
append the new item
doubling appends are O(n)
but because number of O(1) appends also double it amortizes to O(1)
so really average case is O(1)
tradeoff, you dont have to specify size, but also slower appends in some scenarios

### Hash Tables

hashing function to make key an index in an array
hash collisions occur when hashing functions provide same index for two things 
one way to fix this is by having a pointer as values in the table, pointer points to a linked list of values that came to that index
collisions are rare enough we call it O(1)
get fast look ups by key, except some lookups are slow
only fast lookups in one direction, O(n) to find key for value, same as finding index for value

### Linked Lists

A linked list is a simple data structure that consists of nodes linked together. A SinglyLinkedListNode, its own data structure, consists of data and a pointer to the next node in the list. See below for base characteristics of a linked list:

- **Head** - A pointer to the head node of the list, i.e. the front of the list, important because we must know where the list starts.
- **Tail** - A pointer to the end of the list, this is optional but can help in some situations. 
- **Size** - A count of the number of nodes in the linked list, this is optional but a nice luxury to have, set this when adding or deleting from a list.

Important characteristics of a SinglyLinkedListNode: 

- **Data** - Some sort of data attribute, because after all we want to store data in the list, this is usually a integer value.
- **Next** - This is a pointer to the next node in the list, if the node is the last node in the list then this is null. 
- **Prev** - This is a pointer to the previous node, used in doubly linked list implementations, the first nodes prev is null.

So with that knowledge, all a linked list does is store a list of items in a ordered sequence. What can also do this? An array (among other things), therefore it is vital that you consider the advantages/disadvantages of either data structure when designing your program, see below:

#### Advantages of Linked Lists

- Constant time, O(1), insertion and deletion time. This is because to insert and delete all you need to do is change some pointers. 
- Ability to continue to expand, linked lists can grow until there is physically no space left on the machine. Arrays are limited by their size defined at initialization, even dynamic arrays (vectors) have an inherit cost when they automatically resize. 

#### Disadvantages of Linked Lists

- Searching/access time is slow, O(i), because we need to traverse the entire list until we find our ith item, unless you already have a pointer to the item you wish to access. In an array, access time is O(1) because you provide the index of the item you want to access.
- Not cache friendly because nodes are note stored contiguously in memory.

There are different types of linked lists as well, such as a doubly linked list, this is a list that allows you to easily traverse in two directions, up and down the list.
circular linked list

## Queues

FIFO
enqueue
dequeue
peek
isEmpty

## Stacks 

LIFO
push 
pop
peek
isEmpty

## Trees

### Binary Trees

a tree where each node has at most two children (left and right)
perfect trees are ones that are completely full
n = 2^h - 1

#### Binary Search Trees

breadth (level order)

depth
pre
post
in

#### Red Black Trees

#### AVL Tree

rotations

#### Binary Heap


## Graphs

abstract data structure that consists of nodes (vertices) connected by edges
  
two nodes connected by an edge are called adjacent or neighbors
the degree of a node is the number of edges connected to it
directed and undirected graphs
directed graph nodes have an in degree and a out degree
could be a weighted graph
cyclic (circles occur in graph) and cyclic
a loop is an edge that connects a node to itself
ways to represent a graph
edge list int[][] = { {2,1}, {3,1))
adjacency list each item in the lsit is a node, each node has a list of adjacent nodes
adjacency matrix 1's and 0's

## Algorithms

Binary Search - to be used on a sorted list of numbers

Mergesort - split the array in half then sort the halfs, then merge the halfs together O(nlogn)

quick sort
- create a pivot (normally last element)
- compare all elements against pivot and put elements to smaller to the right and larger to the left of the wall

Counting sort - good time complexity, bad space complexity, good when you know the range
create an array to count number of times a number shows up, the index of element represents the number 
then create a new array that is to hold the sorted numbers
then input each of the numbers in inital array the number of times logged in order

Breadth First Search
- method of exploring a tree or graph
- move left to right across tree
- good for trees that are wider than they are deep

advantages 
will find the shortest path between starting point and any other reachable node, depth first search will no necessarily find the shortest path

disadvantages
a bfs on a binary tree requires more memory than a depth first search generally

Depth First Search
- also a method for exploring a binary tree or graph
- like a corn maze, try one path and hit the dead end, try another path

advantages
- less memory
- easily implemented with recursion

disadvantages
-doesnt necessarily find the shortest path


in place algorithms
algorithms that modify its input, sometimes called destructive
working inplace is a good way to save space
have side effects though

node edge graph
in/pre/postorder traversals
AVL trees
B-Tress
Red black tree


greedy
iterates through the problem space taking the best so far
always makes the choice that seems the best in the moment
makes a locally-optimal choice in the hope that this choice will lead to a globally-optimal solution.


## Language Notes

functions in javascript create new scopes
assignments don't get hoisted
but declarations do in javascript
closures
primitives in javascript are pass by value
 
solve problems with Java
but be prepared to solve with Javascript
be very strong with JS trivia

closure, a function that accesses a variable outside of itself, good to mimic private members in traditional oop

Ruby is convention over configuration 

java uses short circuit evaluation

garbage collector automatically frees up things in memory we are not using 
how? it looks at what we can't free up and frees everything else, this is called tracing garbade collection
other option is call reference counting, we keep each object keeps track of how many things reference it, if the reference count is zero we put it in the garbage
C doesnt have a garbage collector, C++ has both, manual memory management.

In java everything is mutable, with exception to strings
String builder in java makes strings mutable


## Interview Tactics

Tips and Tricks
Metacognition -  do you think about coding well?
Ownership - Do you see your work through, do I fix things that aren't quite right

example of interesting technical problem solved
example of interpersonal conflict I overcame
example of leadership or ownership
something I should have done differently in past project
trivia about your favorite language, something I like and don't like
questions about the company, product/business/strat(testing, scrum)
nerd out about shit (middleware would be nice)
im excited about what they are doing, React Native particularly, GraphQL
share opinions on things
make feel collaborative "we"
- learn new technologies constantly - 
work at FB-size scale - smart co-workers - 
good physical location or weather or activities - 
high potential for career growth, doing high ownership and high impact work

## Problems

Egg Problem
egg floors problem
1. work you way up each floor until an egg breaks
having two eggs allows us to skip floors
how do we choose which floors to skip

We want to skip as few floors as possible the first time we drop an egg, so if our first egg breaks right away we don't have a lot of floors to drop our second egg from.
We always want to be able to reduce the number of floors we're skipping by one. We don't want to get towards the top and not be able to skip any floors any more.

Shuffling Cards

Rolling Dice

## Contributing

It is very possible that someone does stumble upon this, it is equally possible that I make an incorrect statement and that same person wants to help me out by fixing it. If you are such person, I'd love the help so please follow the contribution guide below: 

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request# interview-prep
