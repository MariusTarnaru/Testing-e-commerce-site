package steps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Step;
import pages.UserPages;

import java.io.IOException;

public class UserSteps {

    UserPages userPages;
    @Step
    @Given("access to the site")
    public void accessToTheSite() throws IOException {
        userPages.accessToSite();
    }

    @Step
    @And("take the menu {string}")
    public void takeTheMenu(String name) {
        userPages.teakeTheMenu(name);
    }

    @Step
    @When("search by product {string}")
    public void searchByProduct(String name) {
        userPages.searchByName(name);
    }

    @Step
    @Then("take a list of all products")
    public void takeAListOfAllProducts() {
        userPages.takeListOfProducts();
    }

    @Step
    @When("select {string} list from {string} navlist")
    public void selectListFromNavlist(String value, String navList) {
        userPages.selectOneListFromNavList(value, navList);
    }

    @Step
    @And("sort the product list by price in descending order")
    public void sortTheProductListByPriceInDescendingOrder() {
        userPages.sortTheProductListByPriceDescending();
    }

    @Step
    @When("press {string} button and count {string} products")
    public void pressButtonAndCountProducts(String name, String value) {
      this.userPages.pressButtonAndCountProducts(name, value);
    }

    @Step
    @When("select {string} from product container and get available quantity")
    public void selectFromProductContainer(String productName) {
        this.userPages.selectProduct(productName);
    }


}
