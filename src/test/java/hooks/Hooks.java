package hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import redmine.managers.Context;

public class Hooks {

    @After
    public void afterAll(Scenario scenario) {
        Context.saveStashToAllure();
    }

}