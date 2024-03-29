package com.uniovi.sdi2223entrega122.pageobjects;


import com.uniovi.sdi2223entrega122.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_PrivateView extends PO_NavView {

    static public void fillFormAddOffer(WebDriver driver, String title, String detail, long price) {

        SeleniumUtils.waitLoadElementsBy(driver, "id", "title", 5);
        WebElement titleElement = driver.findElement(By.name("title"));
        titleElement.click();
        titleElement.clear();
        titleElement.sendKeys(title);

        WebElement descriptionElement = driver.findElement(By.name("detail"));
        descriptionElement.click();
        descriptionElement.clear();
        descriptionElement.sendKeys(detail);

        WebElement priceElement = driver.findElement(By.name("price"));
        priceElement.click();
        priceElement.clear();
        priceElement.sendKeys(String.valueOf(price));

        //Pulsar el boton de Alta.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    static public void fillHighlightedOffer(WebDriver driver, String title, String detail, long price) {
        WebElement checkbox = driver.findElement(By.id("checkbox"));
        checkbox.click();

        fillFormAddOffer(driver, title, detail, price);
    }

    static public void sendMessage(WebDriver driver, String message) {

        //SeleniumUtils.waitLoadElementsBy(driver, "id", "message", 10);
        WebElement titleElement = driver.findElement(By.name("message"));
        titleElement.click();
        titleElement.clear();
        titleElement.sendKeys(message);

        //Pulsar el boton de Alta.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }
}