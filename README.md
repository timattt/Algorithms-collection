# Rampant-algorithms
Here are some useful algorithms implemented in Java.

Theoretical minimum can be found [here](http://e-maxx.ru/algo/)

## Algorithms
### Dynamics
* Find biggest zero sub matrix   
You are given an n-by-m matrix a. 
It is required to find in it such a submatrix consisting only of zeros, and among all such - 
having the largest area.
### GCD
* Normal GCD   
You are given two non-negative integers a and b. It is required to find their greatest common divisor.
* Advanced GCD   
While the "normal" Euclidean algorithm simply finds the greatest common divisor of two numbers a and b,
the extended Euclidean algorithm finds, in addition to the GCD, the coefficients x and y such that:
```
a * x + b * y = gcd(a, b);
```
### Graph
* Finding Euler path   
An Euler path is a path in a graph that goes through all of its edges. An Euler cycle is an Euler path, which is a cycle.
The goal is to find an Euler path in an undirected multigraph with loops.
* Checking the graph for acyclicity and finding the cycle   
Let a directed or undirected graph be given without loops and multiple edges. 
It is required to check whether it is acyclic, and if not, then find any cycle.
* Dijkstra's algorithm   
You are given a directed or undirected weighted graph with n vertices and m edges. 
The weights of all edges are non-negative. Some starting vertex s is indicated. 
It is required to find the lengths of the shortest paths from vertex s to all other vertices, 
and also provide a way to derive the shortest paths themselves.
* Search for components of strong connectivity   
A strongly connected component in a directed graph is a (maximal by inclusion) 
subset of vertices such that any two vertices of this subset are reachable from each other
The algorithm described below selects all strongly connected components in a given graph.
It will not be difficult to construct a condensation graph from them.
* Topological sort   
You are given a directed graph with n vertices and m edges. 
It is required to renumber its vertices in such a way that each edge leads from 
a vertex with a lower number to a vertex with a larger one.
* MST Prim   
You are given a weighted undirected graph G with n vertices and m edges. 
It is required to find a subtree of this graph that would connect all its vertices, 
and at the same time have the smallest possible weight.
* Search for articulation points.   
Let a connected undirected graph be given. 
A point of articulation is a vertex, the removal of which makes the graph disconnected.
* Find bridges   
Let an undirected graph be given. 
A bridge is an edge whose removal makes the graph disconnected 
(or, more precisely, increases the number of connected components). Find all bridges in a given graph.
* MST Kruskal   
A weighted undirected graph is given. 
It is required to find a subtree of this graph that would connect all its vertices, 
and at the same time have the smallest weight (i.e., the sum of the edge weights) of all possible ones.
* DFS   
This is one of the main graph algorithms.
As a result of depth-first search, the lexicographically first path in the graph is found.
* BFS   
Breadth First Search is one of the main algorithms for graphs.
Breadth First Search finds the shortest path in the unweighted graph, i.e. path containing the least number of edges.
* LCA   
Let a tree G be given. The input receives requests of the form (V1, V2), 
for each request it is required to find their least common ancestor, i.e. 
the vertex V, which lies on the path from the root to V1, on the path from 
the root to V2, and of all such vertices, the lowest one should be selected.
### Hafman   
The Huffman algorithm is an algorithm for optimal prefix coding of the alphabet with minimal redundancy.
### Search
* Binary search   
Binary search is a classic algorithm for finding
an element in a sorted array, using the array splitting in half.
* Ternary search   
Let a function f (x) be given that is unimodal on some segment [l; r]. 
Unimodality refers to one of two options. First: the function first strictly increases, 
then reaches a maximum (at one point or an entire segment), then strictly decreases. 
The second variant, symmetric: the function first decreases, decreases, reaches a minimum,
and increases. In the future, we will consider the first option, the second will be absolutely 
symmetrical to it.
It is required to find the maximum of the function f (x) on the segment [l; r].
### Simpson rule
It is required to calculate the value of a definite integral.
### Z-function
Let a string s of length n be given. 
Then the Z-function of this string is an array of length n, the i-th element of which is equal 
to the largest number of characters, starting from position i, 
that match the first characters of string s.
### Disjoint set union
This data structure provides the following capabilities. 
Initially, there are several elements, each of which is in a separate (its own) set. 
In one operation, you can combine any two sets, and you can also query which set the 
specified element is currently in. Also, in the classic version,
another operation is introduced - the creation of a new element, which is placed in a separate set.
# Segments tree
A segment tree is a data structure that allows you to efficiently 
(i.e., for asymptotics O (log n) implement operations of the following form: 
finding the sum or minimum of array elements in a given segment, while additionally 
changing the array elements is possible: as changing the value of one element and changing 
elements on the whole array subsegment.
# Smart stack and queue
Here we will consider three tasks: modifying the stack with the addition of finding the 
smallest element in O (1), similarly modifying the queue, and also applying them to the 
problem of finding the minimum in all subsegments of a fixed length of a given array in O (N).
# SQRT decomposition
Sqrt decomposition is a method or data structure that allows you to perform some typical 
operations (summing the elements of a subarray, finding the minimum / maximum, etc.) 
in O (sqrt n), which is much faster than O (n) for a trivial algorithm.
# Suffics automaton
A suffix automaton (or directed acyclic word graph) is a powerful data structure that 
allows you to solve many string problems.
# Trie
Trie is a data structure for storing a set of strings, which is a suspended tree with symbols on its edges. 
Strings are obtained by sequential writing of all characters stored on the edges between the boron root 
and the terminal vertex. The size of the bur is linearly dependent on the sum of the lengths of all strings, 
and the search in the bur takes time proportional to the length of the sample.
