package com.uniovi.sdi2223entrega122.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import com.uniovi.sdi2223entrega122.util.SeleniumUtils;

import java.util.List;

public class PO_SearchView extends PO_View {

    public static void searchForSale(WebDriver driver, String title, String xpath) {
        WebElement inputSearch = driver.findElement(By.name("searchText"));
        inputSearch.click();
        inputSearch.clear();
        inputSearch.sendKeys(title);
        List<WebElement> btnSearch = SeleniumUtils.waitLoadElementsByXpath(driver,
                xpath, getTimeout());
        btnSearch.get(0).click();
    }

}
