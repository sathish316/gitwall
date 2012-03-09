$(document).ready(function(){
    //$('#new_task_form').ajaxForm(options);
    $('#new_task_form').submit(function(){
	var options = {
	    target: '#results'
	}
	$(this).ajaxSubmit(options);
	return false;
    });

});
