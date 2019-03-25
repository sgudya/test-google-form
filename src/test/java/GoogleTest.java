import com.codeborne.selenide.Configuration;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;
import static com.codeborne.selenide.Selenide.open;


public class GoogleTest {

    private String emotionListHeader = "Как ваше настроение ? :) ";
    private String genderListHeader = "Пол ";
    private String baseURI = "https://goo.gl/forms/t16Uov7ZHXCrB2ZE2";
    private String email = "sergey.gudyma@gmail.com";
    private String name = "Serhii Hudima";
    private String emotion = "Fantastic";

    private GoogleFormPage form;

    @BeforeMethod(description = "Prepare Environment for the test run")
    public void init() {
        Configuration.browserCapabilities = new DesiredCapabilities();
        Configuration.browserCapabilities
                .setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        form = new GoogleFormPage();
        open(baseURI);
    }

    @DataProvider // List of invalid email addresses
    public Object[][] invalidEmails() {
        return new Object[][]{
                {" "},
                {"áêóípsilonã_1A@gmail.com"},
                {"sergey.gudyma.gmail.com"},
                {"sergey.gudyma@"},
                {"sergey.gudyma@com"}
        };
    }

    @DataProvider // List of invalid text data
    public Object[][] invalidText() {
        return new Object[][]{
                {" "},
                {"%@!><?"}
        };
    }

    @Test(description = "1. Submit form with all fields completed")
    public void submitFormWithAllFields() {
        form.setEmail(email);
        form.setName(name);
        form.setGender(genderListHeader);
        form.setEmotion(emotionListHeader, 0);
        form.setEmotion(emotionListHeader, 1);
        form.setEmotion(emotionListHeader, 2);
        form.setEmotion(emotionListHeader, emotion);
        form.submit();
        Assert.assertTrue(form.confirmSubmission());

    }

    @Test(description = "2.Submit form with only mandatory fields completed")
    public void submitFormWithMandatoryFieldsOnly() {
        form = new GoogleFormPage();
        open(baseURI);
        form.setEmail(email);
        form.setName(name);
        form.setGender(genderListHeader);
        form.submit();
        Assert.assertTrue(form.confirmSubmission());
    }

    @Test(dataProvider = "invalidEmails", description = "6.Submit form with incorrect email")
    public void submitFormWithInvalidEmail(String email) {
        form.setEmail(email);
        form.setName(name);
        form.setGender(genderListHeader);
        form.setEmotion(emotionListHeader, 0);
        form.setEmotion(emotionListHeader, 1);
        form.setEmotion(emotionListHeader, 2);
        form.setEmotion(emotionListHeader, emotion);
        form.submit();
        Assert.assertFalse(form.confirmSubmission());
    }

    @Test(description = "9.Check if only single emotion can be selected")
    public void singleEmotionSelection() {
        form.setEmotion(emotionListHeader, 0);
        form.setEmotion(emotionListHeader, 1);
        form.setEmotion(emotionListHeader, 2);
        form.setEmotion(emotionListHeader, "Fantastic");
        Assert.assertEquals(form.getListOfSelectedEmotions(emotionListHeader), 1);
    }

    @Test(dataProvider = "invalidText", description = "7.Submit form with incorrect name")
    public void submitFormWithIncorrectName(String name) {

        form.setEmail(email);
        form.setName(name);
        form.setGender(genderListHeader);
        form.setEmotion(emotionListHeader, emotion);
        form.submit();
        Assert.assertFalse(form.confirmSubmission());
    }

    @Test(dataProvider = "invalidText", description = "8.Submit form with incorrect emotion")
    public void submitFormWithIncorrectEmotion(String emotion) {

        form.setEmail(email);
        form.setName(name);
        form.setGender(genderListHeader);
        form.setEmotion(emotionListHeader, emotion);
        form.submit();
        Assert.assertFalse(form.confirmSubmission());
    }

    @Test(description = "3.Submit form without email field")
    public void submitFormWithOutEmail() {

        form.setName(name);
        form.setGender(genderListHeader);
        form.setEmotion(emotionListHeader, emotion);
        form.submit();
        Assert.assertFalse(form.confirmSubmission());
    }

    @Test(description = "4.Submit form without name field")
    public void submitFormWithOutName() {

        form.setEmail(email);
        form.setGender(genderListHeader);
        form.setEmotion(emotionListHeader, emotion);
        form.submit();
        Assert.assertFalse(form.confirmSubmission());
    }
    @Test(description = "5.Submit form without gender field")
    public void submitFormWithOutGenderSelection() {

        form.setEmail(email);
        form.setName(name);
        form.setEmotion(emotionListHeader, emotion);
        form.submit();
        Assert.assertFalse(form.confirmSubmission());
    }

}
