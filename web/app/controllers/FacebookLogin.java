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

    public static Result test() {
        return ok(test.render());
    }
}
