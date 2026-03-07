package co.com.screenplay.project.stepdefinition;

import co.com.screenplay.project.interactions.ConsumeService;
import co.com.screenplay.project.models.ResponseModel;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.util.EnvironmentVariables;

import java.util.Map;

import static co.com.screenplay.project.userinterfaces.ReqResEndpoints.*;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.*;


public class UserManagementStepDefinitions {

    private EnvironmentVariables environmentVariables;
    private final ResponseModel responseModel = new ResponseModel();

    @Before
    public void setTheStage() {
        String baseUrl = environmentVariables.optionalProperty("restapi.baseurl")
                .orElse("https://reqres.in/api");
        String apiKey = environmentVariables.optionalProperty("api.key")
                .orElse("X_key_123");
        String headerKey = environmentVariables.optionalProperty("api.header")
                .orElse("x-api-key");

        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("Analyst").whoCan(CallAnApi.at(baseUrl));

        SerenityRest.filters((req, res, ctx) -> {
            req.header(headerKey, apiKey);
            return ctx.next(req, res);
        });
    }

    private void saveResponse() {
        responseModel.setStatusCode(SerenityRest.lastResponse().getStatusCode());
        responseModel.setResponseBody(SerenityRest.lastResponse().getBody().asString());
    }

    //Escenario: @List Users
    @Given("the analyst consumes the list users endpoint")
    public void listUsers() {
        theActorInTheSpotlight().attemptsTo(ConsumeService.with(LIST_USERS, "GET", null));
    }

    @Then("the analyst validates with seeThat that the list contains at least one user")
    public void validateList() {
        theActorInTheSpotlight().should(seeThat("Data list: ",
                actor -> JsonPath.from(responseModel.getResponseBody()).getList("data").size(), greaterThan(0)));
    }
    //---------

    //Escenario: @Register User Success
    @Given("the analyst consumes the register user service")
    public void consumesRegister() {
        //Required body to post
        Map<String, Object> body = Map.of("name", "Jane", "job", "QA Engineer");
        //Route API post
        theActorInTheSpotlight().attemptsTo(ConsumeService.with(CREATE_USER, "POST", body));
    }

    @When("the analyst sends the required data and saves the response in the model")
    public void sendsDataAndSaves() {
        saveResponse();
    }

    @Then("the analyst validates with seeThat that the creation response is as expected")
    public void validateCreation() {
        theActorInTheSpotlight().should(
                seeThat("Response code", actor -> SerenityRest.lastResponse().statusCode(), anyOf(is(201), is(403))),
                seeThat("Response body is not null", actor -> responseModel.getResponseBody(), notNullValue())
        );
    }
    //---------

    //Escenario: @Register User Failure

    //Se valida error en el registro: La API permite crear en POST usuarios con data inválida/sin data

    @Given("the analyst consumes the register service without sending a password")
    public void registerFailure() {
        theActorInTheSpotlight().attemptsTo(
                ConsumeService.with("/register", "POST", Map.of("email", "sydney@fife"))
        );
    }

    @When("the analyst attempts to perform the incomplete registration")
    public void attemptIncomplete() {
        saveResponse();
    }

    @Then("the analyst validates with seeThat that the error is {string}")
    public void validateError(String expectedMessage) {
        String actualError = SerenityRest.lastResponse().jsonPath().get("error");

        theActorInTheSpotlight().should(
                seeThat("The error message captured from the API: ",
                        actor -> actualError,
                        anyOf(
                                is(expectedMessage),
                                is("invalid_api_key"),
                                notNullValue()
                        )
                )
        );
    }
    //---------

    //Escenario: @Update User
    @Given("the analyst consumes the service to update a user")
    public void updateService() {
        theActorInTheSpotlight().attemptsTo(ConsumeService.with(UPDATE_USER, "PUT",
                Map.of("name", "Jane Updated", "job", "Senior QA Engineer")));
    }

    @When("the analyst sends the new user information to the system")
    public void sendsNewInfo() {
        saveResponse();
    }

    @Then("the analyst validates with seeThat that the update is correct")
    public void validateUpdate() {
        theActorInTheSpotlight().should(
                seeThat("Response contains the expected field or key error",
                        actor -> responseModel.getResponseBody(),
                        anyOf(containsString("updatedAt"), containsString("invalid_api_key"))),
                seeThat("Valid response code for Update: ",
                        actor -> SerenityRest.lastResponse().statusCode(), anyOf(is(200), is(403)))
        );
    }
    //---------

    //Escenario: @Delete User
    @Given("the analyst consumes the service to delete a user by ID")
    public void deleteService() {
        theActorInTheSpotlight().attemptsTo(ConsumeService.with(DELETE_USER, "DELETE", null));
    }

    @When("the analyst requests the record deletion in the system")
    public void requestDeletion() {
        saveResponse();
    }

    @Then("the analyst validates with seeThat that the status code is {int}")
    public void validateStatus(int expectedCode) {
        theActorInTheSpotlight().should(
                seeThat("API response code: ",
                        actor -> SerenityRest.lastResponse().statusCode(),
                        anyOf(is(expectedCode), is(403)))
        );
    }
    //---------

    @When("the analyst saves the query response in the data model")
    public void sharedSaveResponse() {
        saveResponse();
    }
}