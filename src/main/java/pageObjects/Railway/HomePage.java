package pageObjects.Railway;

import common.Constant.Constant;

public class HomePage extends GeneralPage{
    //Locators
    //Elements
    //Methods
    public HomePage open(){
        Constant.WEBDRIVER.navigate().to(Constant.RAILWAY_URL);
        return this;
    }
    public void clickBookTicket(){
        getTabBookTicket().click();
    }
}
