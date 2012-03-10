$(document).ready(function(){
    $('#new_task_form').submit(function(event){
	event.preventDefault();
	$.ajax({
	    url: $('#new_task_form')[0].action,
	    type: 'POST',
	    data: $(this).serialize(),
	    success: function(data, status, xhr){
		$('#tasks').html(xhr.responseText);
	    }
	});
    });

});
