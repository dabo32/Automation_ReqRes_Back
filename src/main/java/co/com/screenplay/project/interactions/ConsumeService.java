package co.com.screenplay.project.interactions;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.interactions.*;

import java.util.Map;

public class ConsumeService extends RestInteraction {

    private final String resource;
    private final String method;
    private final Map<String, Object> body;

    public ConsumeService(String resource, String method, Map<String, Object> body) {
        this.resource = resource;
        this.method = method;
        this.body = body;
    }

    public static ConsumeService with(String resource, String method, Map<String, Object> body) {
        return new ConsumeService(resource, method, body);
    }

    @Override
    @Step("{0} executes a #method request to #resource")
    public <T extends Actor> void performAs(T actor) {
        switch (method.toUpperCase()) {
            case "GET":
                actor.attemptsTo(new Get(resource));
                break;
            case "POST":
                actor.attemptsTo(new Post(resource).with(request -> request.contentType(ContentType.JSON).body(body)));
                break;
            case "PUT":
                actor.attemptsTo(new Put(resource).with(request -> request.contentType(ContentType.JSON).body(body)));
                break;
            case "DELETE":
                actor.attemptsTo(new Delete(resource));
                break;
        }
    }
}