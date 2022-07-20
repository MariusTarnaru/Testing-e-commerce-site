package steps;

//import cucumber.api.java.en.Given;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.UserPages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserSteps extends UserPages{

    UserPages userPages = new UserPages();

    @Step
    @Given("access to the site")
    public void accessToTheSite() throws IOException {
        accessToSite();
    }

    @Step
    @When("search by product {string}")
    public void searchByProduct(String name) {
        searchByName(name);
    }

    @Then("take a list of all products")
    public void takeAListOfAllProducts() {
        takeListOfProducts();
    }

}
