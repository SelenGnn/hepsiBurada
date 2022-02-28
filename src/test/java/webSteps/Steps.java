package webSteps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Parser;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Steps {
    Parser parser = new Parser();
    final String EMPTY_STRING = "";
    final String NEW_LINE_STRING = "\n";
    final String DOUBLE_QUOTE_STRING = "\"";
    public static WebDriverWait wait;
    public static RemoteWebDriver driver;


    @After
    public void tearDown() {
        try {
            driver.close();
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }

    @Given("I open browser")
    public void OpenBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
    }

    @And("I maximize browser")
    public void MaximizeBrowser() {
        driver.manage().window().maximize();
    }

    @And("I open \"([^\"]*)\" page")
    public void OpenPage(String pageKey) throws IOException, ParseException {
        parser.setPageKey(pageKey);
        driver.get(parser.getPageObject("urlKey"));
    }


    @And("I fill:")
    public void fillDataMap(Map<String, String> dataMap) throws IOException, ParseException, InterruptedException {
        for (Map.Entry<String, String> item : dataMap.entrySet()) {
            fill(item.getKey(), item.getValue());
        }
    }

    public void fill(String elementKey, String elementValue) throws IOException, ParseException, InterruptedException {
        getElement(elementKey).clear();
        Thread.sleep(100);
        getElement(elementKey).sendKeys(elementValue);
    }

    public WebElement getElement(String elementKey) throws IOException, ParseException {
        String elementValue = parser.getElementKey(elementKey);
        By selector = bySelector(elementValue);
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        return driver.findElement(selector);
    }

    private By bySelector(String selector) {
        if (selector.matches("^#[\\w-]+$")) {
            return By.id(selector.substring(1));
        } else if (selector.charAt(0) == '/' || selector.charAt(0) == '(' || selector.startsWith("./")) {
            return By.xpath(selector);
        } else {
            return By.cssSelector(selector);
        }
    }

    @When("I click \"([^\"]*)\"")
    public void Click(String elementKey) throws IOException, ParseException, InterruptedException {
        String currentTab = driver.getWindowHandle();
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(currentTab)) {
                driver.close();
                driver.switchTo().window(tab);
            }
        }
        By selector = bySelector(parser.getElementKey(elementKey));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(selector));
            getElement(elementKey).click();
        } catch (TimeoutException e) {
            System.out.println("No Review");
            System.exit(0);
        }
    }

    @Then("I see \"([^\"]*)\" text in \"([^\"]*)\"")
    public void SeeTextIn(String text, String elementKey) throws IOException, ParseException {
        String originalElementText = getElement(elementKey).getText();
        String modifiedElementText = originalElementText.replaceAll(NEW_LINE_STRING, EMPTY_STRING).replaceAll(DOUBLE_QUOTE_STRING, EMPTY_STRING);
        Assert.assertEquals(text, modifiedElementText);
    }

    @And("I scroll the page")
    public void ScrollThePage() {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,864)");
    }
}


