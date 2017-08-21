
$(function(){
	
var inputTags = document.getElementsByTagName('input');

for (var i = 0; i < inputTags.length; i++) {
	if(inputTags[i].type == "text")
		inputTags[i].addEventListener('click',highlight);
}

var textareaTag = document.getElementsByTagName('textarea');

for (var i = 0; i < textareaTag.length; i++) {
	textareaTag[i].addEventListener('click',highlight);
}
	
function highlight(){
	
	if(this.type == "text" || this.id == "Address"){
		var id = this.id;
		var ox = data[id][0].x; 
		var oy = data[id][0].y;
		
		var owidth = data[id][2].x;
		var oheight = data[id][2].y;
		
		drawPoly(img,ox,oy,owidth,oheight);
		
		//console.log(ox+","+oy+","+owidth+","+oheight)

	}		
}

function draw(base64) {

	var image = document.getElementById("image");
		
	var img = new Image();
	
	img.src = base64;

  	var canvas = document.createElement('canvas');
  	
  	if (canvas.getContext) {

	    var ctx = canvas.getContext('2d');
	  	
	    img.onload = function(){
	    	
	    	canvas.height = img.height;
	    	canvas.width = img.width;

		  	image.height = (image.width / img.width) * img.height;
		  	
		  	ctx.drawImage(img, 0, 0, img.width, img.height);
		  
		  	image.src = canvas.toDataURL();
		  	
		  	//image.src = base64;
		  	/*console.log(image.height +" : "+image.width )
		  	console.log(img.height +" : "+img.width )*/

	    };
  	}
}

function drawPoly(base64,ox,oy,owidth,oheight){
	
	
	var image = document.getElementById("image");
	
	var img = new Image();  
	img.src = base64;
	
  	var canvas = document.createElement('canvas');
  	
	if (canvas.getContext) {
		var ctx = canvas.getContext('2d');

		img.onload = function() {
	  	
	    	canvas.height = img.height;
	    	canvas.width = img.width;

	    	owidth = (owidth - ox);
			oheight = (oheight - oy);
			
	   		image.height = (image.width / img.width) * img.height;
	    	
		    ctx.drawImage(img, 0, 0, img.width, img.height)
		  	//ctx.strokeRect(ox, oy, owidth, oheight);
			ctx.strokeStyle="green";
		    
			ctx.lineWidth = (img.width / 500 ) * 3;
			
			ox = ((img.width/compressedDim.width) * ox) - ((img.width / 500 ) * 5);
			oy = ((img.width/compressedDim.width) * oy) - ((img.width / 500 ) * 5);
			owidth = ((img.width/compressedDim.width) * owidth) + ((img.width / 500 ) * 15);
			oheight = ((img.width/compressedDim.width) * oheight) + ((img.width / 500 ) * 15);
			
	   		ctx.strokeRect(ox, oy, owidth,oheight);
	   		
		  	image.src = canvas.toDataURL();
		  	
	  	};
		
	}   
   
}

draw(img);


//On form submit

document.getElementById("submit").addEventListener("click", function(){
	
	var client_id = "3MVG959Nd8JMmavQe5kgiSSQJpws6EydIsyaTN07ms2UOmCxXdesnlc3jjJZagffJVi2.4__c3gJUWMfLPG0j";
	var client_secret = "7967524131757639248";
	var grant_type = "password";
	var username = "dharmvir_singh@herofincorp.com.herodev2";
	var password= "test@1234";
	
	var url = "https://fincorp--herodev2.cs57.my.salesforce.com/services/oauth2/token?client_id="+client_id+"&client_secret="+client_secret+"&grant_type="+grant_type+"&username="+username+"&password="+password;
	
	$.ajax({
		url : url, 
		type: 'POST',
	    crossDomain: true,
		success : function(res){
					console.log(res);
					console.log("access token : "+res.access_token);
				}
	
	});
	
});


});