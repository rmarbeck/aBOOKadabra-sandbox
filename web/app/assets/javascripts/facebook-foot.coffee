getRoute = (target) ->
    if target.attr("id") is 'tbd'
       jsRoutes.controllers.FacebookLogin.ajaxReceiveUserInfo()
         
window.authResponseChangeFunction = (response) ->
    if response.status is 'connected'
        connected(response)
    else  if response.status is 'not_authorized'
        not_authorized(response)
    else
        not_connected(response)
        
connected = (response) ->
    console.log('Welcome!  Fetching your information.... ')
    fireFacebookInfo(response)
    console.log('token : '+ response.authResponse.accessToken)
    console.log('UID : '+ response.authResponse.userID)
    FB.api('/me', (response) ->
       console.log('Good to see you, ' + response.name + '.')
       $("#facebook_result").html('Good to see you, ' + response.name + '.'))

not_authorized = (response) ->
   logout_actions()

not_connected = (response) ->
   logout_actions()

logout_actions = () ->
   console.log('change.')
   $("#logout").addClass("hidden")
   $("#login").removeClass("hidden")
   $("#facebook_result").html('toto')

fireFacebookInfo = (response) ->
    jsRoutes.controllers.FacebookLogin.ajaxReceiveUserInfo().ajax
      data :
         token			: response.authResponse.accessToken
         uid  			: response.authResponse.userID
         signedRequest	: response.authResponse.signedRequest
         expiresIn		: response.authResponse.expiresIn
      success  : (res, status, xhr) -> 

      error    : (xhr, status, err) ->
  

$('a#logout').click (event) ->
    console.log('Clicked')
    FB.logout()
    
$('#login').click (event) ->
    console.log('Clicked')
    FB.login()
