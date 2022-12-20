//Bresenham 画圆
var sb=function(x,y,z){
	Script.setBlock(Script.getPos().floor().addV(x,y,z),'wool')
};
var sby=function(x,y,z){
	sb(x,y,z);
	sb(z,y,x);
	sb(z,y,-x);
	sb(x,y,-z);
	sb(-x,y,-z);
	sb(-z,y,-x);
	sb(-z,y,x);
	sb(-x,y,z)
};
var l=25;
var x=0;
var y=l;
var p=3-2*l;
for(;x<=y;x++){
	sby(x,0,y);
	if(p>=0){
		p+=4*(x-y)+10;
		y--;
	}else{
		p+=4*x+6;
	}
};