import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class GoogleFormPage {

    //Get list of elements based on the list title
    private ElementsCollection getList(String header) {
        return $$(By.xpath("//div[text()='" + header + "']/ancestor::div[@class='freebirdFormviewerViewNumberedItemContainer']/descendant::*"));
    }

    public  ElementsCollection getListOfMandatoryFields(){
        return $$(By.xpath("//div[@data-required='true']"));
    }

    //Set email to the form
    public void setEmail(String email) {
        $(By.xpath("//input[@type='email']"))
                .sendKeys(email);
    }

    //Set name to the form
    public void setName(String name) {
        $(By.xpath("//input[@type='text']"))
                .sendKeys(name);
    }

    //Expand drop-down list
    private void openDropDown(String header) {
        getList(header)
                .findBy(Condition.attribute("role","listbox"))
                .click();
            }

    //Select gender
    public void setGender(String header){
        openDropDown(header);
        getList(header)
                .filter(Condition.attribute("role", "option"))
                        .get(1)
                .click();
    }

    //Get an amount of the selected emotions in the form
    public int getListOfSelectedEmotions(String header){
        int amount = getList(header)
                .filter(Condition.attribute("aria-checked","true"))
                .size();
        return amount;
    }

    //Select an emotion
    public void setEmotion(String header, int index) {

        getList(header)
                .filter(Condition.attribute("data-value"))
                .get(index)
                .click();
    }

    //Select odd emotion
    public void setEmotion(String header, String emo) {

        getList(header)
                .filter(Condition.attribute("data-value"))
                .last()
                .click();
        getList(header)
                .find(Condition.attribute("type","text"))
                .sendKeys(emo);
    }

    //Submit form
    public void submit() {
        $(By.className("freebirdFormviewerViewNavigationSubmitButton"))
                .click();
    }

    //Check confirmation
    public boolean confirmSubmission() {
       return $(By.className("freebirdFormviewerViewResponseConfirmationMessage"))
               .exists();
    }
}
