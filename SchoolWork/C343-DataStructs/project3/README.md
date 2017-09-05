Search function: it looks through the left subtree or the right subtree depending on the value you passed it.
or it will also return the root of the tree if the root is equal to the key. 

Insert: insert checks to see if there is a root. If not it makes a Node of said value. else it checks what subtree to traverse and goes
untill it hits none and then adds it to the tree. 

Predecessor: pred looks for the least_ancestor only if the node.left is none: else it takes the Max of whatever is to the left of the node

Successor: this looks to see if n.right is none and if it is it looks for the greater ancestor else it retuns the min of the node.left

Delete: Delete checks 3 cases if the node has no children, if it has 1 child, and if it has 2 chidren. Based on which case it is 
it picks the right procedure to switch the pointers and parent nodes around. 

For AVL
All the functions i did were the same in BST. I was not able to finish Delete and Insert
