$(document).ready(function(){
    var options = {
	clearForm: true,
	target: '#tasks_result'
    }
    $('#new_task_form').ajaxForm(options);
});
