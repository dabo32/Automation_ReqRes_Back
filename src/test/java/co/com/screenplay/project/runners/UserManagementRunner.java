package co.com.screenplay.project.runners;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "co.com.screenplay.project.stepdefinition")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "@UserManagement")
public class UserManagementRunner {
}