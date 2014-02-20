package controllers;

import java.util.List;

import models.Transacao;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.Response;
import play.libs.WS.WSRequestHolder;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;

public class Application extends Controller {

	public static Result index() {
		int quantidade = Integer.parseInt(request()
				.getQueryString("quantidade"));
		List<Transacao> transacoes = Ebean.find(Transacao.class)
				.setMaxRows(quantidade).findList();
		List<Transacao> transacoes2 = Ebean.find(Transacao.class).setFirstRow(quantidade)
				.setMaxRows(quantidade).findList();
		List<Transacao> transacoes3 = Ebean.find(Transacao.class).setFirstRow(quantidade*2)
				.setMaxRows(quantidade).findList();
		
		return ok(views.html.index.render(transacoes,transacoes2,transacoes3));
	}
	
	public static Promise<Result> webservice(){
		WSRequestHolder url = WS.url("https://www.google.com.br/?gfe_rd=cr&ei=oWsBU6n8E8Ki8we43IDoCA#q=federer");
		Promise<Response> esperandoAResposta = url.get();
		return esperandoAResposta.map(new Function<WS.Response, Result>() {

			@Override
			public Result apply(Response response) throws Throwable {
				return ok(response.getBody());
			}
		});
		
	}

}
