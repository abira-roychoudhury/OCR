
$(function(){
	
	$( "#imagePanel" )
    .mouseover(function() {
      $( '#image-panel-body' ).toggleClass('scrollDiv');
    })
    .mouseout(function() {
        $( '#image-panel-body' ).toggleClass('scrollDiv');
    });

	
var originalJson = {};
var correctedJson = {};
var inputTags = document.getElementsByTagName('input');

for (var i = 0; i < inputTags.length; i++) {
	if(inputTags[i].type == "text"){
		inputTags[i].addEventListener('click',highlight);
		originalJson[inputTags[i].id] = inputTags[i].value;
	}
}

var textareaTag = document.getElementsByTagName('textarea');

for (var i = 0; i < textareaTag.length; i++) {
	textareaTag[i].addEventListener('click',highlight);
	originalJson[textareaTag[i].id] = textareaTag[i].value;
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



function formOriginal(){
	
}

$("#submit").click
(
    function()
    {
    	$('#submitMessage').text("");
    	$("#submitLoader").show();
      	$('input, button, textarea').attr("disabled", true);
    	$(".container").addClass('haze');
    	//creating the correctedJson
    	for (var i = 0; i < inputTags.length; i++) {
    		if(inputTags[i].type == "text")
    			correctedJson[inputTags[i].id] = inputTags[i].value;
    	}
    	for (var i = 0; i < textareaTag.length; i++) {
    		correctedJson[textareaTag[i].id] = textareaTag[i].value;
    	}
    	
        $.ajax
        (
            {
                url:'/OCR/SubmitToSF',
                data: {"fileType":fileType,"salesforcerecordID":salesforcerecordID,"originalJson":originalJson,"correctedJson":correctedJson},
                type:'post',
                success:function(data){
                	console.log(data);
                	$('#submitMessage').text(data);
                	$("#submitLoader").hide();
                	$('input, button').attr("disabled", false);
                	$('.container').removeClass('haze');
                },
                error:function(err){alert(err);
                	console.log(err)
                	$("#submitLoader").hide();
                	$('input, button, textarea').attr("disabled", false);
                	$('.container').removeClass('haze');
                }
            }
        );
    }
);

});