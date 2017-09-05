from utilities import *

def flood(color_of_tile, flooded_list, screen_size):
    # print color_of_tile
    i = 0
    while i < len(flooded_list):
        if in_bounds(flooded_list[i], screen_size):
            #right
            if in_bounds(right(flooded_list[i]),screen_size):
                if flooded_list.count(right(flooded_list[i])) == 0:
                    if color_of_tile[flooded_list[i]] == color_of_tile[right(flooded_list[i])]:
                        flooded_list.append(right(flooded_list[i]))
            #down
            if in_bounds(down(flooded_list[i]),screen_size):
                if flooded_list.count(down(flooded_list[i])) == 0:
                    if color_of_tile[flooded_list[i]] == color_of_tile[down(flooded_list[i])]:
                        flooded_list.append(down(flooded_list[i]))
            #up
            if in_bounds(up(flooded_list[i]),screen_size):
                if flooded_list.count(up(flooded_list[i])) == 0:
                    if color_of_tile[flooded_list[i]] == color_of_tile[up(flooded_list[i])]:
                        flooded_list.append(up(flooded_list[i]))
            #left
            if in_bounds(left(flooded_list[i]),screen_size):
                if flooded_list.count(left(flooded_list[i])) == 0:
                    if color_of_tile[flooded_list[i]] == color_of_tile[left(flooded_list[i])]:
                        flooded_list.append(left(flooded_list[i]))
        i +=1
    pass

