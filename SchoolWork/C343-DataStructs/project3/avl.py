# AVL Trees, by Elizabeth Feicke

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




class AVLNode:
    def __init__(self, key, left=None, right=None, parent = None):
        self.key = key
        self.left = left
        self.right = right
        self.parent = parent

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

        
        
        
def less_than(x,y):
    return x < y

class AVLTree:
    def __init__(self, root = None, less=less_than):
        self.root = root
        self.less = less

    # takes value, returns node with key value
    def insert(self, k):
        pass

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
        pass

