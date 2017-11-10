// 鼠标滚动事件
function mouseScroll(){
	var agent=navigator.userAgent;
	var contentLeft=document.getElementsByClassName("content-l")[0];
	var footerHeight=document.getElementsByClassName("chen-footer")[0].offsetHeight;
	var relHeight=footerHeight+20;
	if (/.*Firefox.*/.test(agent)) {
	    document.addEventListener("DOMMouseScroll", function(e) {
	        height;
	    });
	} else {
    	document.onmousewheel = height;
	}
}

function height(e){
	var e = e || window.event;
	var contentLeft=document.getElementsByClassName("content-l")[0];
	var footerHeight=document.getElementsByClassName("chen-footer")[0].offsetHeight;
	var scrollHeight=document.body.scrollHeight;
	var innerHeight=window.innerHeight;
	var scrollTop=document.body.scrollTop;
	var bottom=scrollHeight-scrollTop-innerHeight;
	var relHeight=footerHeight+20;
	if ( bottom<=relHeight) {
        contentLeft.style.bottom=75+"px";
    } else {
        contentLeft.style.bottom=25+"px";
    }
}
// Ajax获取后台数据
function getData(){
	var xmlHttp;
	if(window.XMLHttpRequest){
		xmlHttp=new XMLHttpRequest();
	}else{
		xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlHttp.onreadystatechange=function(){
		if(xmlHttp.readyState==4){
			
		}
	}
}