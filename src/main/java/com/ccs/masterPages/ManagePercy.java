package com.ccs.masterPages;

import com.ccs.masterConfig.DriverManager;
import com.ccs.utility.ElementOperations;
import io.percy.selenium.Percy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ManagePercy extends ElementOperations {

    private WebDriver driver;
    private Percy percy;

    public ManagePercy(){
        percy = DriverManager.getInstance().getPercy();
        driver = DriverManager.getInstance().getDriver();
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
    }
    public void takePercySnapshot(String pagename) {
        percy.snapshot(pagename);
    }

}
