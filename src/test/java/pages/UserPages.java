package pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.BaseTestClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class UserPages extends PageObject {

    BaseTestClass baseTestClass;
    WebDriver driver = getDriver();
    private static final Logger log = LoggerFactory.getLogger(UserPages.class);

    String search = "search_query_top";
    String submiSearch = "submit_search";
    String content = "tab-content";
    String menu = "sf-menu";
    String listOfProducts = "//ul[@class='product_list grid row']/li";

    By navSelectList = By.xpath("//div[@id='categories_block_left']//ul[@class='tree dynamized']");

    @FindBy(css="div[class*='z-div'] input[type='text']")
    private WebElementFacade userNameField;


    public void accessToSite() throws IOException {

        baseTestClass.setUpTest();
        // accept cookies
        driver.findElement(By.id("lgcookieslaw_accept")).click();
    }

    public void searchByName(String name) {
        final WebElement searchEl = driver.findElement(By.id(search));
        final WebElement submitEl = driver.findElement(By.name(submiSearch));

        searchEl.click();
        searchEl.sendKeys(name);
        submitEl.click();

        String textSearched = driver.findElement(By.className("lighter")).getText().toLowerCase(Locale.ROOT);
        char[] chars = new char[7];
        textSearched.getChars(1,8,chars,0);
        String textRead = String.valueOf(chars);

        Assert.assertEquals( name.toLowerCase(), textRead);
    }

    public void teakeTheMenu(String name) {
        WebElement menuEl = driver.findElement(By.className(menu)).findElement(By.xpath("//a[@title='"+ name +"']"));
        log.warn("Titlul selectat este: " + menuEl.getText());
        Assert.assertEquals(name.toLowerCase(),menuEl.getText().toLowerCase());
        menuEl.click();
    }

    public void takeListOfProducts() {
        ArrayList<String> allProducts = new ArrayList<>();
        List<WebElement> productList = driver.findElements(By.xpath(listOfProducts));
        for (WebElement product : productList) {
            allProducts.add(product.getText());
        }
        log.warn("Product list contains: " + productList.size() + " elements");
        log.warn("First listed product is: " + allProducts.get(0));

        Assert.assertEquals(48, productList.size());
    }

    public void selectOneListFromNavList(String value, String navList) {

        List<WebElement> list = driver.findElements(By.xpath("//h2[contains(text(),'"+ navList +"')]//parent::div[@id='categories_block_left']//ul[@class='tree dynamized']//li"));
        log.warn(("Number of elements in the list is: " + list.size()));
        List<WebElement> textList = list.stream().filter(name->name.getText().contains(value)).collect(Collectors.toList());
        String desiredCategory = textList.get(0).getText().toLowerCase();
        textList.get(0).click();
        //boolean flag = textList.stream().anyMatch(s->s.equalsIgnoreCase(value));
       /* if (flag){
            // desired value name it is in the string list ?
            log.warn("Desired name is in the list and have name: ");
            textList.stream().filter(s->s.equalsIgnoreCase(value)).forEach(System.out::println);
            //click on <value> list submenu
            driver.findElement(By.xpath("//a[contains(text(),'"+ value +"')]")).click();
            // get category name
            desiredCategory = driver.findElement(By.className("title_block")).getText().toLowerCase();
        } */
        Assert.assertEquals(value.toLowerCase(), desiredCategory);
    }

    public void userRegistrationNames() {
       /* Faker faker = new Faker();
        String username = "test" + faker.number().randomNumber(10, false);
        Serenity.setSessionVariable("userName").to(username);
        String password = faker.internet().password();
        Serenity.setSessionVariable("password").to(password); */

    }

    public void sortTheProductListByPriceDescending() {
        WebElement selectElement = driver.findElement(By.id("selectProductSort"));
        Select selectedField = new Select(selectElement);
        selectedField.selectByValue("price:desc");
    }

    public void pressButtonAndCountProducts(String name, String value) {
        WebElement selectedButton = driver.findElement(By.xpath("//span[contains(text(),'" + name + "')]//parent::button")); ////span[contains(text(),'Show all')]//parent::button
        if (selectedButton.isDisplayed())
            selectedButton.click();

        List<WebElement> allSelectedCategoryProducts = driver.findElements(By.xpath("//ul[@class='product_list grid row']//li"));
        Integer numberOfProducts = allSelectedCategoryProducts.size();
        Assert.assertEquals((Integer)parseInt(value), numberOfProducts);
    }

    public void selectProduct(String productName) {
        WebElement product = driver.findElement(By.xpath("//div[@class='product-container']//a[contains(text(),'"+ productName +"')]"));
        product.click();
        String quantity = driver.findElement(By.id("quantityAvailable")).getText();
        log.warn("Current quantity is: " + quantity);
        //Assert.assertEquals("2", quantity);       //if need assertion
    }
    //input[@id='quantity_wanted']

}
