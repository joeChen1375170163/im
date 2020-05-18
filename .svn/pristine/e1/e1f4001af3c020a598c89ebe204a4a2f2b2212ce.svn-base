//function loadjscssfile(fileName,fileType)
//{ 
//	var fileRef = null;
//	if (fileType=="js")
//	{ //if filename is a external JavaScript file 
//		fileRef = $("script");
//		fileRef.attr("type","text/javascript");
//		fileRef.attr("src", fileName);
//	} 
//	else if (fileType=="css")
//	{ //if filename is an external CSS file 
//		fileRef = $("link");
//		fileRef.attr("rel", "stylesheet");
//		fileRef.attr("type", "text/css");
//		fileRef.attr("href", fileName);
//	} 
//	if (fileRef !=  null) 
//	{
//		$("head").append(fileRef);
//	}
//}

//			loadjscssfile("../zxui/ui/form.js","js");
//			loadjscssfile("../zxui/ui/layer.js","js");
//			loadjscssfile("../zxui/ui/select.js","js");
//			loadjscssfile("../zxui/ui/table.js");
//			loadjscssfile("../zxui/css/layerStyle.css","css");
//			loadjscssfile("../zxui/css/pageStyle.css","css");
//			loadjscssfile("../zxui/css/tableStyle.css","css");
$.extend({
	       includePath: '',     
	       include: function(file) 
	       			{        
	    	   			var files = typeof file == "string" ? [file]:file;        
	    	   			for (var i = 0; i < files.length; i++) 
	    	   			{            
	    	   				var name = files[i].replace(/^s|s$/g, "");            
	    	   				var att = name.split('.');            
	    	   				var ext = att[att.length - 1].toLowerCase();            
	    	   				var isCSS = ext == "css";            
	    	   				var tag = isCSS ? "link" : "script";            
	    	   				var attr = isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";            
	    	   				var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'";            
	    	   				if ($(tag + "[" + link + "]").length == 0) 
	    	   					document.write("<" + tag + attr + link + "></" + tag + ">");        
	    	   			}   
	    	   		}
         });
//使用方法
$(function()
		{
			$.includePath = '../zxui/ui/'; 
			$.include(["form.js","layer.js","select.js","table.js"]);
		});
