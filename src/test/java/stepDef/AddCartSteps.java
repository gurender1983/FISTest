package stepDef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

public class AddCartSteps {

    WebDriver driver;
    Response response;
    String url = "https://www.ebay.com/";

    String apiUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @Given("User launch the ebay portal")
    public void user_launch_the_ebay_portal() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @When("User search an item {string}")
    public void user_search_an_item(String text) {
        driver.findElement(By.id("gh-ac")).sendKeys(text);
        driver.findElement(By.id("gh-btn")).click();
    }

    @When("User select item from search results")
    public void user_select_item_from_search_results() {
        driver.findElement(By.xpath("//li[contains(@id,'item')]//span[@role='heading']")).click();
    }

    @When("User add item to cart")
    public void user_add_item_to_cart() {
        Set<String> s = driver.getWindowHandles();
        Iterator<String> I = s.iterator();
        String I1 = I.next();
        String I2 = I.next();
        driver.switchTo().window(I2);
        driver.findElement(By.id("atcBtn_btn_1")).click();
    }

    @Then("Verify number of items in cart updated")
    public void verify_number_of_items_in_cart_updated() {
        Assert.assertEquals("1", driver.findElement(By.xpath("//li[@id='gh-minicart-hover']//i")).getText());
    }

    @Then("close the browser")
    public void close_the_browser() {
        driver.quit();
    }

    @Given("User send Get request")
    public void userSendGetRequest() {
        RequestSpecification request = RestAssured.given();
        response = request.get(apiUrl);

        System.out.println("\n Status Code: " + response.getStatusCode());
        System.out.println("---> Response JSON Body: " + response.getBody().asPrettyString());
    }

    @Then("Verify response")
    public void verifyResponse() {
        Assert.assertEquals("British Pound Sterling", response.getBody().path("bpi.GBP.description"));
    }
}
