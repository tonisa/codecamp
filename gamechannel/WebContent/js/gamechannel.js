//implement JSON.stringify serialization
JSON.stringify = JSON.stringify || function (obj) {
    var t = typeof (obj);
    if (t != "object" || obj === null) {
        // simple data type
        if (t == "string") obj = '"'+obj+'"';
        return String(obj);
    }
    else {
        // recurse array or object
        var n, v, json = [], arr = (obj && obj.constructor == Array);
        for (n in obj) {
            v = obj[n]; t = typeof(v);
            if (t == "string") v = '"'+v+'"';
            else if (t == "object" && v !== null) v = JSON.stringify(v);
            json.push((arr ? "" : '"' + n + '":') + String(v));
        }
        return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
    }
};

function init(status){
	$('#mylist').empty();
	$('#logo').hide(500);
	$.get('api/games/'+status, function(data) {
		jQuery.each(data, function(i, obj) {
			if( obj.length != null && obj.length > 1){
				for (i=0; i<obj.length; i++) {
		            $('#mylist').append('<li data-theme="e" class="ff"><a href="#battle" onClick="show('+obj[i].id+')" data-transition="flip">'+obj[i].name+'<span class="ui-li-count">'+obj[i].playersTotal+'</span></a></li>');
		            $('#mylist').listview('refresh');
				}
			} else { 
	            $('#mylist').append('<li data-theme="e" class="ff"><a href="#battle" onClick="show('+obj.id+')" data-transition="flip">'+obj.name+'<span class="ui-li-count">'+obj.playersTotal+'</span></a></li>');
	            $('#mylist').listview('refresh');
			}
		});
		}, "json");
}

function show(id){
	$('#grid').html("");
	var source = new EventSource('push.jsp?id='+id);
	source.onmessage = function(e) {
		$('#grid').html(e.data);
	}; 
}