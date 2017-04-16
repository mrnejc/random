import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.templ.HandlebarsTemplateEngine;
import io.vertx.ext.web.templ.TemplateEngine;

/**
 * Created by nejc on 16.4.2017.
 */
public class SampleServer {
    public static void main(String[] args) {
        try {
            Vertx vertx = Vertx.vertx();
            Router router = Router.router(vertx);
            TemplateEngine engine = HandlebarsTemplateEngine.create();

            router.route("/sample-fail").handler(rc->{
                engine.render(rc, "path/to/another/template/sample-fail", h->{
                    if(h.succeeded()) {
                        rc.response().end(h.result());
                    } else {
                        h.cause().printStackTrace();
                        rc.response().end(h.cause().toString());
                    }
                });
            });

            router.route().handler(TemplateHandler.create(engine));

            vertx.createHttpServer().requestHandler(router::accept).listen(8080);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

}
