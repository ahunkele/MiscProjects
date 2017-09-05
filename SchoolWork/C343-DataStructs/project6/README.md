My partner is Joel Park 

my init function defins 2 empty lists. one to hold all the keys and one to hold tuples of keys and values associated with a key.
then i initiate a table of the size of the dictionary. if the dictionary size is 0 just make a 1 bucket list. getitem function 
just looks in all the tuples at the first index of the tuple and sees if the key is in it.  set item sets a position to enter in the table, adds the key to keyed 
and adds a tuple of (key,value). Del function item just deletes the key and the tuple. Keys function just returns the list Keyed. 
