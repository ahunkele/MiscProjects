from utilities import *

def flood(color_of_tile, flooded_list, screen_size):
    for elm in flooded_list:
        for adj in [up(elm), down(elm), right(elm), left(elm)]:
            if in_bounds(adj, screen_size) and color_of_tile[elm] == color_of_tile[adj] and adj not in flooded_list:
                flooded_list.append(adj)
            else:
                pass
            
       
        

        
        
        
   
