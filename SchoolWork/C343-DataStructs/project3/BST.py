from stack import ArrayStack



def Min_N(n):
    if n.left is not None:
        return Min_N(n.left)
    else:
        return n
    
def Max_N(n):
    if n.right is not None:
        return Max_N(n.right)
    else:
        return n
    
def greater_ancestor(n):
    p = n.parent
    if p is not None and n == p.right:
        return greater_ancestor(p)
    else:
        return p

def least_ancestor(n):
    p = n.parent
    if p is not None and n == p.left:
        return least_ancestor(p)
    else:
        return p

def countkids(n):
    x = 0
    if n.right != None:
        x += 1
    elif n.left != None:
        x += 1
    else:
        x += 0
    return x



class BSTNode:
    def __init__(self, key, left=None, right=None, parent=None):
        self.key = key
        self.left = left
        self.right = right
        self.parent = parent
        

    def Newsearch(self, k):
        if k == self.key:
            return self
        elif less_than(k, self.key): 
            if self.left is None:
                return None
            else:
                return self.left.Newsearch(k)
        elif less_than(self.key, k):
            if self.right is None:
                return None
            else:
                return self.right.Newsearch(k)
        else:
            pass

    def Newinsert(self, k, less_than):
        if less_than(k, self.key):
            if self.left is None:
                self.left = BSTNode(k, None, None, self)
                return self.left
            else:
                return self.left.Newinsert(k, less_than)
            
        elif less_than(self.key, k):
            if self.right is None:
                self.right = BSTNode(k, None, None, self)
                return self.right
            else:
                return self.right.Newinsert(k, less_than)

    def pred(self, n):
        if n.left is None:
            return least_ancestor(n)
        else:
            return Max_N(n.left) 
                

    def suc(self, n):
        if n.right is None:
            return greater_ancestor(n) 
        else:
            return Min_N(n.right) 

    def del_n(self, n):
        if n:
            x = countkids(n)
            if x == 0:
                if n.parent.left == n:
                    n.parent.left = None
                    return n
                else:
                    n.parent.right = None
                    return n
            elif x==1:
                if n.left:
                    child = n.left
                else:
                    child = n.right
                if n.parent:
                    if n.parent.left == n:
                        n.parent.left = child
                        return n
                    else:
                        parent.right = child
                        return n
            else:
                n.parent = n
                nex = n.right
                while nex.left:
                    n.parent = nex
                    nex = nex.left
                n.key = nex.key
                if n.parent.left == nex:
                    n.parent.left = nex.right
                else:
                    n.parent.right = nex.right
            
    
        
def less_than(x,y):
    return x < y

class BinarySearchTree:
    def __init__(self, root = None, less=less_than):
        self.root = root
        self.parents = True
        self.less = less

    # takes value, returns node with key value
    def insert(self, k):
        if self.root is None:
            return BSTNode(k)
        else:
            return self.root.Newinsert(k, self.less)
            
    
        

    # takes node, returns node
    # return the node with the smallest key greater than n.key
    def successor(self, n):
        if self.root is None:
            return None
        else:
            return self.root.suc(n)
        

    # return the node with the largest key smaller than n.key
    def predecessor(self, n):
        if self.root is None:
            return None
        else:
            return self.root.pred(n)
        
        

    # takes key returns node
    # can return None
    def search(self, k):
        if self.root is None:
            return None
        else:
            return self.root.Newsearch(k)
        
            
    # takes node, returns node
    def delete_node(self, n):
        if self.root is None:
            return None
        else:
            return self.root.del_n(n)
        

