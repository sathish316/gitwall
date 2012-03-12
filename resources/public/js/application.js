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
		$('.sample_task').remove();
	    }
	});
    });

    // Add Task on Enter
    $('#task').keypress(function(event){
	if (event.which == 13){
	    $('#new_task_form .add-task-btn').submit();
	    return false;
	}
	$('.task-column').sortable('refresh');
    });

    // Github OAuth Login
    $('#github-sign-in').click(function(){
	$('#github-sign-in-form').trigger('submit');
    });

    // Select active project (TODO: Load active project contents)
    function loadProjectWall(){
	$("#wall").html('<br/><br/>');
	$("#wall").addClass('loading');
	$.ajax({
	    url: "/tasks?project=" + $('#project').val(),
	    type: 'GET',
	    success: function(data, status, xhr){
		$("#wall").html($(xhr.responseText).find("#wall").html());
		makeWallSortable();
		$("#wall").removeClass('loading');
	    },
	    error: function(){
		$("#wall").removeClass('loading');
	    }
	});
    }

    $(".project-link").click(function(){
	var isActive = $(this).hasClass('active');
	$(".project-link.active").removeClass('active');
	$("#project").val("default");
	if(!isActive){
	    $(this).addClass('active');
	    $("#project").val($(this).text());
	}
	loadProjectWall();
    });

    // Update card status when it is moved
    function makeWallSortable(){
	$('.task-column').sortable({
	    connectWith: ".task-column",
	    placeholder: "task-column-hover-over",
	    over: function(event, ui){
		$(this).parent('div').addClass('highlight-column');
	    },
	    out: function(event, ui){
		$(this).parent('div').removeClass('highlight-column');
	    },
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
		
		if(!card.hasClass('sample_task')){
		    $.ajax({
			url: ("/tasks/" + findTaskId(card)),
			type: 'PUT',
			data: {project: $('#project').val(), status: findStatusId(column)},
		    });
		    card.attr('class', 'card card_' + findStatusId(column));
		} else {
		    card.attr('class', 'card sample_task card_' + findStatusId(column));
		}
	    }
	}).disableSelection();
    }
    makeWallSortable();

    // Delete card
    $(".delete-task-container").live("mouseover", function(){
	if(!$(this).parent('.card').hasClass('sample_task')){
	    $(this).find('i').show();
	}
    });
    $(".delete-task-container").live("mouseout", function(){
	if(!$(this).parent('.card').hasClass('sample_task')){
	    $(this).find('i').hide();
	}
    });
    $(".delete-task-container i").live("click", function(){
	var card = $(this).closest('li');
	function findTaskId(card){
	    var taskIdPattern = /task-card-(\d+)/;
	    return card.attr('id').match(taskIdPattern)[1];
	}
	var taskId = findTaskId(card);
	if(!card.hasClass('sample_task')){
	    $.ajax({
		url: ("/tasks/" + findTaskId(card)),
		type: 'DELETE',
		data: {project: $('#project').val()},
		success: function(){
		    $(card).remove();
		}
	    });
	}
    });

});
