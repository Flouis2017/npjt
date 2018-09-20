/*var head = document.head || document.getElementsByTagName("head")[0];
var script = document.createElement("script");
script.type = "text/javascript";
script.src = "static/js/jquery-3.1.1.min.js";
head.appendChild(script);*/

function getContextPath(){
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var contextPath = pathName.substr(0,index+1);
    return contextPath;
}

var $contextPath = getContextPath();