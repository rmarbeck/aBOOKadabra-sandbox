package controllers;

import static play.data.Form.form;

import com.abookadabra.utils.amazon.api.Request;
import com.abookadabra.utils.amazon.api.RequestBuilder;
import com.abookadabra.utils.amazon.api.RequestBuilderHelper;
import com.abookadabra.utils.amazon.api.RequestHelper;
import com.abookadabra.utils.amazon.api.SearchRequestBuilder;
import com.abookadabra.utils.amazon.api.models.SearchAnswer;

import play.*;
import play.libs.WS;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.mvc.*;
import views.html.facebook.*;

public class FacebookLogin extends Controller {

    public static SimpleResult GO_INDEX = redirect(
            routes.FacebookLogin.index()
        );
	
    public static Result index() {
        return ok(index.render());
    }

    public static Result testJQuery() {
        return ok(test_with_jquery.render());
    }
    
    public static Result ajaxReceiveUserInfo() {
    	Logger.info(form().bindFromRequest().get("token"));
    	Logger.info(form().bindFromRequest().get("uid"));
    	Logger.info(form().bindFromRequest().get("signedRequest"));
    	Logger.info(form().bindFromRequest().get("expiresIn"));
    	session().put("name", form().bindFromRequest().get("uid"));
        return ok(test_with_jquery.render());
    }
    
	/**
	 * Building the router for ajax calls
	 */
	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(
				Routes.javascriptRouter("jsRoutes",
						controllers.routes.javascript.FacebookLogin.ajaxReceiveUserInfo()
						)
				);
	}
}
