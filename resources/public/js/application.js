$(document).ready(function(){
    // Add task and refresh task list
    $('#new_task_form').submit(function(event){
	event.preventDefault();
	var form = $('#new_task_form');
	$.ajax({
	    url: form[0].action,
	    type: 'POST',
	    data: form.serialize(),
	    success: function(data, status, xhr){
		$('#task-column-1').append(xhr.responseText);
		form[0].reset();
	    }
	});
    });

    // Add Task on Enter
    $('#task').keypress(function(event){
	if (event.which == 13){
	    $('#new_task_form .add-task-btn').submit();
	    return false;
	}
    });

    // Github OAuth Login
    $('#github-sign-in').click(function(){
	$('#github-sign-in-form').trigger('submit');
    });

    // Select active project (TODO: Load active project contents)
    $(".project-link").click(function(){
	$(".project-link.active").removeClass('active');
	$(this).addClass('active');
    });

    $('.task-column').sortable({
	connectWith: ".task-column",
	stop: function(event, ui){
	    console.log("TODO: Update position and status");
	    console.log($(this).attr('id'));
	    console.log($(ui.item).attr('id'));	    
	    console.log(ui.item.index());
	}
    }).disableSelection();
});
