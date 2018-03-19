/**
 * Created by Gbenro on 01/03/2018.
 */

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.codearte.jfairy.Fairy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.Random;

//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import org.junit.Before;
//import org.junit.After;
//import org.junit.Test;


public class FbnTest {
    //private AndroidDriver;
    AppiumDriver driver;
    AppiumDriver appdriver;
    DesiredCapabilities capabilitiesAndroid;
    DesiredCapabilities capabilities;
    WebDriver chromedriver;

    String firstName;
    String lastName;
    //String phoneNum;
    //String companyAdd;
    String emailAdd;
    String staff;
    String password;
    String pass;

    @BeforeSuite
    public void setUp() throws Exception {
        capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Spread");
        capabilities.setCapability("platformName", "Android");
        //capabilities.setCapability("appPackage", "com.swiftng.redcheetah.staging");
        capabilities.setCapability("app", "/Users/Gbenro/Downloads/FBNIMobile-Staging-1.3.0-app-debug.apk");
        capabilities.setCapability("appWaitActivity", "*");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4725/wd/hub"), capabilities);
        //driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        Fairy fairy = Fairy.create();
        firstName = fairy.person().firstName();
        lastName = fairy.person().lastName();
        //Faker faker = new Faker();
        emailAdd = firstName + "@putsbox.com";
        Random rand = new Random();
        int staffno = rand.nextInt(1000)+1;
        staff = Integer.toString(staffno);
    }

    @Test
    public void testALaunchPage() throws Exception{


        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("existing_customer_question")));
        Thread.sleep(30000);
        WebElement goButton = driver.findElement(By.id("existing_customer_question_icon"));
        goButton.click();

    }

    @Test
    public void testBInvalidPhoneNumber() throws Exception {


        Thread.sleep(30000);
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("phoneNumber")));

        WebElement phonenumber = driver.findElement(By.id("phoneNumber"));
        WebElement proceedButton = driver.findElement(By.id("phone_fragment_proceed_button"));
        phonenumber.sendKeys("08000000088");
        proceedButton.click();

        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.LinearLayout[1]/android.widget.TextView[1]"),"Could not find account with identifier 08000000088"));
        WebElement okButton = driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.Button[1]"));
        okButton.click();

    }


    @Test
    public void testCValidPhoneNumber() throws Exception {


        Thread.sleep(5000);
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("phoneNumber")));

        WebElement phonenumber = driver.findElement(By.id("phoneNumber"));
        WebElement proceedButton = driver.findElement(By.id("phone_fragment_proceed_button"));
        phonenumber.sendKeys("08000000008");
        proceedButton.click();

        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("otp_message")));

    }

    @Test
    public void testDInValidOtpLogin() throws InterruptedException {

        WebElement otp = driver.findElement(By.id("otp_input"));
        WebElement proceedButton = driver.findElement(By.id("phone_fragment_proceed_button"));
        otp.sendKeys("000001");
        proceedButton.click();
        Thread.sleep(30000);
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.LinearLayout[1]/android.widget.TextView[1]"),"The OTP entered is either invalid or has expired"));
        WebElement okButton = driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.Button[1]"));
        okButton.click();



    }

    @Test
    public void testEValidOtpLogin() throws InterruptedException {

       WebElement otp = driver.findElement(By.id("otp_input"));
       WebElement proceedButton = driver.findElement(By.id("phone_fragment_proceed_button"));
       otp.sendKeys("000000");
       proceedButton.click();
       // Thread.sleep(3000);
       (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("otp_message")));
       (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("pin")));
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("confirm_pin")));



    }

    @Test
    public void testFBlankPINLogin() throws InterruptedException {

        WebElement completeButton = driver.findElement(By.id("complete_button"));
        completeButton.click();
        Thread.sleep(3000);
        //(new WebDriverWait(driver, 30))
        //.until(ExpectedConditions.textToBePresentInElementLocated(By.id("message"),"PIN length must be 4"));
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.LinearLayout[1]/android.widget.TextView[1]"),"pin is required"));
        WebElement okButton = driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.Button[1]"));
        okButton.click();
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("otp_message")));
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("pin")));
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("confirm_pin")));
        //WebElement okButton = driver.findElement(By.name("Ok"));



    }

    @Test
    public void testFIncompletePINLogin() throws InterruptedException {

        WebElement pinField = driver.findElement(By.id("pin"));
        WebElement confirmPinField = driver.findElement(By.id("confirm_pin"));
        WebElement completeButton = driver.findElement(By.id("complete_button"));
        //email.clear();
        pinField.sendKeys("12");
        //passwordField.clear();
        confirmPinField.sendKeys("12");
        //driver.hideKeyboard();
        //driver.swipe(32, 357, 32, 48, 500);
        completeButton.click();
        Thread.sleep(3000);
        //(new WebDriverWait(driver, 30))
                //.until(ExpectedConditions.textToBePresentInElementLocated(By.id("message"),"PIN length must be 4"));
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.LinearLayout[1]/android.widget.TextView[1]"),"PIN length must be 4"));
        WebElement okButton = driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.Button[1]"));
        okButton.click();
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("otp_message")));
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("pin")));
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("confirm_pin")));
        //WebElement okButton = driver.findElement(By.name("Ok"));



    }

    @Test
    public void testGPinMismatchLogin() throws InterruptedException {

        WebElement pinField = driver.findElement(By.id("pin"));
        WebElement confirmPinField = driver.findElement(By.id("confirm_pin"));
        WebElement completeButton = driver.findElement(By.id("complete_button"));
        pinField.clear();
        pinField.sendKeys("1235");
        confirmPinField.clear();
        confirmPinField.sendKeys("1236");
        //driver.hideKeyboard();
        //driver.swipe(32, 357, 32, 48, 500);
        completeButton.click();
        Thread.sleep(3000);
        //(new WebDriverWait(driver, 30))
        //.until(ExpectedConditions.textToBePresentInElementLocated(By.id("message"),"PIN length must be 4"));
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.LinearLayout[1]/android.widget.TextView[1]"),"PINs don't match, please confirm and try again"));
        WebElement okButton = driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.Button[1]"));
        okButton.click();
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("otp_message")));
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("pin")));
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("confirm_pin")));
        //WebElement okButton = driver.findElement(By.name("Ok"));



    }

    @Test
    public void testHPinMismatchIncompleteLogin() throws InterruptedException {

        WebElement pinField = driver.findElement(By.id("pin"));
        WebElement confirmPinField = driver.findElement(By.id("confirm_pin"));
        WebElement completeButton = driver.findElement(By.id("complete_button"));
        pinField.clear();
        pinField.sendKeys("1227");
        confirmPinField.clear();
        confirmPinField.sendKeys("12");
        //driver.hideKeyboard();
        //driver.swipe(32, 357, 32, 48, 500);
        completeButton.click();
        Thread.sleep(3000);
        //(new WebDriverWait(driver, 30))
        //.until(ExpectedConditions.textToBePresentInElementLocated(By.id("message"),"PIN length must be 4"));
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.LinearLayout[1]/android.widget.TextView[1]"),"PINs don't match, please confirm and try again"));
        WebElement okButton = driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.Button[1]"));
        okButton.click();
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("otp_message")));
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("pin")));
        (new WebDriverWait(driver,60)).until(ExpectedConditions.presenceOfElementLocated(By.id("confirm_pin")));
        //WebElement okButton = driver.findElement(By.name("Ok"));



    }

    @Test
    public void testIValidPinLogin() throws InterruptedException {

        WebElement pinField = driver.findElement(By.id("pin"));
        WebElement confirmPinField = driver.findElement(By.id("confirm_pin"));
        WebElement completeButton = driver.findElement(By.id("complete_button"));
        pinField.clear();
        pinField.sendKeys("1234");
        confirmPinField.clear();
        confirmPinField.sendKeys("1234");
        //driver.hideKeyboard();
        //driver.swipe(32, 357, 32, 48, 500);
        completeButton.click();
        Thread.sleep(3000);
        //(new WebDriverWait(driver, 30))
        //.until(ExpectedConditions.textToBePresentInElementLocated(By.id("message"),"PIN length must be 4"));
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.LinearLayout[1]/android.widget.TextView[1]"),"PIN successfully created"));
        WebElement okButton = driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.Button[1]"));
        okButton.click();

        //WebElement okButton = driver.findElement(By.name("Ok"));



    }

    /*
    @Test
    public void testEInvalidPasswordLogin() throws InterruptedException {

        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement signInButton = driver.findElement(By.id("loginButton"));
        emailField.clear();
        emailField.sendKeys("zoro@putsbox.com");
        passwordField.clear();
        passwordField.sendKeys("hffjhfjd");
        driver.hideKeyboard();
        //driver.swipe(32, 357, 32, 48, 500);
        signInButton.click();
        Thread.sleep(7000);
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.LinearLayout[1]/android.widget.TextView[1]"),"The email/password combination provided is invalid"));
        WebElement okButton = driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.Button[1]"));
        okButton.click();

    }

    @Test
    public void testFInvalidCredentialsLogin() throws InterruptedException {

        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement signInButton = driver.findElement(By.id("loginButton"));
        emailField.clear();
        emailField.sendKeys("zohdh@putsbox.com");
        passwordField.clear();
        passwordField.sendKeys("hffjhfjd");
        driver.hideKeyboard();
        //driver.swipe(32, 357, 32, 48, 500);
        signInButton.click();
        Thread.sleep(7000);
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.LinearLayout[1]/android.widget.TextView[1]"),"The email/password combination provided is invalid"));
        WebElement okButton = driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.Button[1]"));
        okButton.click();

    }

    @Test
    public void testGValidCredentialsLogin() throws InterruptedException {

        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement signInButton = driver.findElement(By.id("loginButton"));
        emailField.clear();
        emailField.sendKeys(emailAdd);
        passwordField.clear();
        passwordField.sendKeys(password);
        driver.hideKeyboard();
        //driver.swipe(32, 357, 32, 48, 500);
        signInButton.click();
        Thread.sleep(7000);
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.view.ViewGroup[1]/android.widget.TextView[1]"),"Change Password"));
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.LinearLayout[1]/android.widget.TextView[1]"),"First time users are required to change the provided password"));

    }

    @Test
    public void testHBlankOldPasswordChange() throws InterruptedException {

        WebElement oldPassword = driver.findElement(By.id("old_password"));
        WebElement newPassword = driver.findElement(By.id("new_password"));
        WebElement changeButton = driver.findElement(By.id("change_password_button"));
        oldPassword.clear();
        //emailField.sendKeys(emailAdd);
        newPassword.clear();
        newPassword.sendKeys("123456");
        driver.hideKeyboard();
        //driver.swipe(32, 357, 32, 48, 500);
        changeButton.click();
        Thread.sleep(3000);
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.id("textinput_error"),"This field is required"));

    }

    @Test
    public void testIBlankNewPasswordChange() throws InterruptedException {

        WebElement oldPassword = driver.findElement(By.id("old_password"));
        WebElement newPassword = driver.findElement(By.id("new_password"));
        WebElement changeButton = driver.findElement(By.id("change_password_button"));
        oldPassword.clear();
        oldPassword.sendKeys("123456");
        //emailField.sendKeys(emailAdd);
        newPassword.clear();
        //newPassword.sendKeys("123456");
        driver.hideKeyboard();
        //driver.swipe(32, 357, 32, 48, 500);
        changeButton.click();
        Thread.sleep(3000);
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.id("textinput_error"),"This field is required"));

    }

    @Test
    public void testJLessCharactersChange() throws InterruptedException {

        WebElement oldPassword = driver.findElement(By.id("old_password"));
        WebElement newPassword = driver.findElement(By.id("new_password"));
        WebElement changeButton = driver.findElement(By.id("change_password_button"));
        oldPassword.clear();
        oldPassword.sendKeys("1234");
        //emailField.sendKeys(emailAdd);
        newPassword.clear();
        newPassword.sendKeys("1234");
        driver.hideKeyboard();
        //driver.swipe(32, 357, 32, 48, 500);
        changeButton.click();
        Thread.sleep(3000);
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.id("textinput_error"),"Password must not be less than six characters"));

    }

    @Test
    public void testKPasswordMismatch() throws InterruptedException {

        WebElement oldPassword = driver.findElement(By.id("old_password"));
        WebElement newPassword = driver.findElement(By.id("new_password"));
        WebElement changeButton = driver.findElement(By.id("change_password_button"));
        oldPassword.clear();
        oldPassword.sendKeys("12345678");
        //emailField.sendKeys(emailAdd);
        newPassword.clear();
        newPassword.sendKeys("1234567");
        driver.hideKeyboard();
        //driver.swipe(32, 357, 32, 48, 500);
        changeButton.click();
        Thread.sleep(3000);
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.id("textinput_error"),"Passwords do not match"));

    }

    @Test
    public void testLValidPassword() throws InterruptedException {
        Faker faker = new Faker();
        pass = faker.internet().password(6,10);

        WebElement oldPassword = driver.findElement(By.id("old_password"));
        WebElement newPassword = driver.findElement(By.id("new_password"));
        WebElement changeButton = driver.findElement(By.id("change_password_button"));
        oldPassword.clear();
        oldPassword.sendKeys(pass);
        newPassword.clear();
        newPassword.sendKeys(pass);
        driver.hideKeyboard();
        //driver.swipe(32, 357, 32, 48, 500);
        changeButton.click();
        Thread.sleep(3000);
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.widget.LinearLayout[1]/android.widget.TextView[1]"),"Password Successfully updated. Click 'OK' to login"));
        WebElement okButton = driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.Button[1]"));
        okButton.click();
        Thread.sleep(5000);

    }

    @Test
    public void testMValidCredentialsLogin() throws InterruptedException {

        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement signInButton = driver.findElement(By.id("loginButton"));
        emailField.clear();
        emailField.sendKeys(emailAdd);
        passwordField.clear();
        passwordField.sendKeys(pass);
        driver.hideKeyboard();
        //driver.swipe(32, 357, 32, 48, 500);
        signInButton.click();
        Thread.sleep(7000);
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.view.ViewGroup[1]/android.widget.TextView[1]"),"Home"));
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.id("saving_balance_label"),"Your Savings Balance"));

    }*/

    @AfterSuite
    public void tearDown() throws Exception {
        //driver.closeApp();
        driver.quit();
        //chromedriver.quit();
    }
}
