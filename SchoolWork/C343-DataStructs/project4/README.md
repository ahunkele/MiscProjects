Contributers: Joel Park, Kyle Fechtman

Our seq_align function is what creates the board. we add a space to s1 and s2 to make sure the top left score is a 0. then we init the entire board
then we make a direction variable which is equal to the function directions(board, len(s3) len(s4)) this function takes the board in and creates a list of the best path it took starting from the 
last point of the chart chart(len(x), len(y)). we then have a makestr function that goes through directions path and adds a space where the path says to. then finally we have a scorecheck function 
this checks the final score and assures that we have the correct score. 
