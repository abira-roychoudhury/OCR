var inputTags = document.getElementsByTagName('input');
for (var i = 0; i < inputTags.length; i++) {
	if(inputTags[i].type == "text")
		inputTags[i].addEventListener('click',highlight);
}
	
	function highlight(){
		if(this.type == "text"){
			var id = this.id;
			var ox = data[id][0].x; 
			var oy = data[id][0].y;
			
			var owidth = data[id][2].x;
			var oheight = data[id][2].y;
			
			drawPoly(img,ox,oy,owidth,oheight);
			
		}		
	}

	function draw(base64) {

		var img = new Image();  
		img.src = base64;

	  	var canvas = document.getElementById('canvas');
	  	
	  	if (canvas.getContext) {

		    var ctx = canvas.getContext('2d');
		  	img.onload = function() {
		  	canvas.height = (canvas.width/img.width) * img.height 
		    ctx.drawImage(img, 0, 0, img.width, img.height, 0, 0, canvas.width, canvas.height);
		  };
	  	}
	}

	function drawPoly(base64,ox,oy,owidth,oheight){
		var img = new Image();  
		img.src = base64;
		var canvas = document.getElementById('canvas');
	  	
		owidth = (owidth - ox);
		oheight = (oheight - oy);

	    cpointx = ((canvas.width / img.width) * ox)-5
	    cpointy = ((canvas.height / img.height) * oy)-5

	    cwidth = ((canvas.width / img.width) * owidth)+10
	    cheight = ((canvas.height / img.height) * oheight)+10

		if (canvas.getContext) {
			var ctx = canvas.getContext('2d');

			img.onload = function() {
		  	
			    ctx.drawImage(img, 0, 0, img.width, img.height, 0, 0, canvas.width, canvas.height);
			  	//ctx.strokeRect(ox, oy, owidth, oheight);
				ctx.strokeStyle="green";
				ctx.lineWidth = 3;
		   		ctx.strokeRect(cpointx, cpointy, cwidth, cheight);

		  	};
			
		}   
	   
	}

	draw(img);