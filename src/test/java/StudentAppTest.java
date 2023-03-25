import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.xml.xpath.XPath;
import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class StudentAppTest {

    WebDriver driver;
    WebDriverWait driverWait;
    Faker dateFaker = new Faker();
    private final String APP_URL = "http://app.acodemy.lv/";
    @BeforeMethod
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(APP_URL);
    }
    @AfterMethod
    public void tearDown(){
        driver.close();
        driver.quit();
    }

    @Test
    public void openStudentApp() {
        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ant-table-title']//button")));

        WebElement addStudentButton = driver.findElement(By.xpath("//div[@class='ant-table-title']//button"));
        addStudentButton.click();

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        WebElement nameField = driver.findElement(By.id("name"));
        String name = dateFaker.pokemon().name();
        nameField.sendKeys(name);

        WebElement emailField = driver.findElement(By.id("email"));
        String email = dateFaker.internet().emailAddress();
        emailField.sendKeys(email);

        WebElement genderField = driver.findElement(By.id("gender"));
        genderField.click();

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='OTHER']")));

        WebElement valuesFromDropDown = driver.findElement(By.xpath("//div[@title='OTHER']"));
        valuesFromDropDown.click();
        System.out.println();

        WebElement submitButton = driver.findElement(By.xpath("//div[@class='ant-form-item-control-input-content']//button"));
        submitButton.click();

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ant-notification-notice-message")));
        WebElement notificationMessage = driver.findElement((By.className("ant-notification-notice-message")));
        WebElement notificationDescription = driver.findElement(By.className("ant-notification-notice-description"));

        Assert.assertEquals(notificationMessage.getText(), "Student successfully added");
        Assert.assertEquals(notificationDescription.getText(), name + " was added to the system");

        // Close pop-up
        // Check if it closed


        WebElement popUpCloseButton = driver.findElement(By.className("ant-notification-notice-close"));
        popUpCloseButton.click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(popUpCloseButton)));
    }
}
//driver.findElement(By.xpath("//div[@class='ant-form-item-control-input-content']//button")).click();