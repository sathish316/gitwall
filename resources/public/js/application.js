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

    // Update card status when it is moved
    $('.task-column').sortable({
	connectWith: ".task-column",
	receive: function(event, ui){
	    var card = ui.item;
	    var column = $(this);

	    function findTaskId(card){
		var taskIdPattern = /task-card-(\d+)/;
		return card.attr('id').match(taskIdPattern)[1];
	    }

	    function findStatusId(column){
		var statusIdPattern = /task-column-(\d+)/;
		return column.attr('id').match(statusIdPattern)[1];
	    }

	    $.ajax({
		url: ("/tasks/" + findTaskId(card)),
		type: 'PUT',
		data: {status: findStatusId(column)},
	    });
	    card.attr('class', 'card card_' + findStatusId(column));
	}
    }).disableSelection();
});
