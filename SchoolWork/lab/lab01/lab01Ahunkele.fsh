void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec2 uv = fragCoord.xy / iResolution.xy;
    
    //red color vector
    vec4 red = vec4(1,0,0,1.0);
    //black color vector
    vec4 black = vec4(0,0,0,1.0);
    //white color vector
    vec4 white = vec4(1,1,1,1.0);
    //green color vector
    vec4 green = vec4(0,1,0,1.0);
    
    if(uv.x < .5){
        //set left half of screen to red
        fragColor = red;
    }else if(uv.y > .5){
        //set top right to green 
        fragColor = green;
    }
    else{
        //set rest of screen to black
        fragColor = black;
    }
   
}