
$('.change-currency').change(function(){
	var to = $('.change-currency').val();
	console.log(to);
	var from = $('.item-price').first().text().replace(/[^a-zA-Z]+/g, '');
	console.log(from);
		$.ajax({
			type: 'POST',
			url: 'converter',
			data: {FromCurrency: from, ToCurrency: to}
		
			})
			.done(function(data){
				console.log(data);
				$('#search-results-container').empty();
				if(!data){
					$('#search-results-container').empty().css('display', 'none');
					Console.log('Could not convert.')
				} else {
					$('.item-price').each(function() {
						var price = $(this).text().replace(/[^0-9\.]/g, '');
						if(price) {
							$(this).empty().append((price*data).toFixed(2)+to);
						}
					});
				}
			})
			.fail(function(){
				console.log("Error switching currencies");
			});
});