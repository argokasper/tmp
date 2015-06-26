/**
 * 
 */

$('.send-button').click(function(){
	var searchTerm = $('#search').val().trim();
		$.ajax({
			type: 'POST',
			url: 'Tester.java',
			data: {search: searchTerm},
			dataType: 'json'
			})
			.done(function(data){
				console.log(data);
				$('#search-results-container').empty();
				if(data.error){
					$('#search-results-container').empty().css('display', 'none');
				} else {
					data.forEach(function(item){
						$('#search-results-container').css({'display': 'block'}).append('<div class="item"></div>');
					});
				}
			})
			.fail(function(){
				console.log("Error showing results");
			});
});