	$(document).ready(function() {
	    
		$.ajaxSetup({ cache: true });
		$.getScript('//connect.facebook.net/fr_FR/all.js', function(){
			FB.init({
				appId		: '377916862340216',
				channelUrl	: '//www.abookadabra.com/assets/html/facebook/channel.html',
				status     	: true,
				cookie     	: true,
				xfbml      	: true
			});     
			
			$('#loginbutton,#feedbutton').removeAttr('disabled');

			FB.Event.subscribe('auth.authResponseChange', authResponseChangeFunction);		

		});			  

	});