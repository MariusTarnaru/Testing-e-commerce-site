package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseTestClass;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

public class UserPages extends BaseTestClass {

    String search = "search_query_top";
    String submiSearch = "submit_search";
    String content = "tab-content";

    public void accessToSite() throws IOException {

        setUpTest();
        driver.findElement(By.id("lgcookieslaw_accept")).click();
        //tearDown();

    }

    public void searchByName(String name) {
        final WebElement searchEl = driver.findElement(By.id(search));
        final WebElement submitEl = driver.findElement(By.name(submiSearch));

        searchEl.click();
        searchEl.sendKeys(name);
        submitEl.click();

        String textSearched = driver.findElement(By.className("lighter")).getText().toLowerCase(Locale.ROOT);
        char[] chars = new char[7];
        textSearched.toString().getChars(1,8,chars,0);
        String textRead = String.valueOf(chars);

        Assert.assertEquals( name.toLowerCase(), textRead);
    }

    public void takeListOfProducts() {
        List<WebElement> productList = driver.findElements(By.xpath("//ul[@class='product_list grid row']/li"));
        for (WebElement product : productList) {
            System.out.println(product.getText());
        }
        System.out.println("Lista de produse cautate contine: " + productList.size() + " elemente");  // 48 elemente li

        Assert.assertTrue(productList.size() == 48);
    }


}
