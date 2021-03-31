package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class YandexPage implements SearchPageInterface {

    private String url = "https://yandex.ru/";

    public WebDriver driver;

    public YandexPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void openPage() {
        driver.get(url);
    }

    @FindBy(xpath = "//form[contains(@class, 'search2')]//input[@id='text']")
    private WebElement searchField;

    @FindBy(xpath = "//form[contains(@class, 'search2')]//button[@type='submit']")
    private WebElement searchButton;

    public void search(String string) {
        searchField.sendKeys(string);
        searchButton.click();
    }

    @FindBy(xpath = "//nav[@role='navigation']//*[contains(text(),'Картинки')]")
    private WebElement imageTabButton;

    public void switchImagesTab() {
        imageTabButton.click();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // в яндексе открывается новое окно, перходим в него
        }
    }

    private String imgElementsXpath = "//div[contains(@class, 'serp-list')]//div[contains(@class, 'serp-item')]//img";

    public List<Image> getAllImagesFromImageTab() {

        List<WebElement> elements = driver.findElements(By.xpath(imgElementsXpath));

        List<Image> images = new ArrayList<>();

        //elements.forEach(images.add(e -> images.add(new Image(e)))); // Почему так нельзя, не понял - incompatible types: org.example.GooglePage.Image is not a functional interface
        for (WebElement e : elements) {
            try {
                images.add(new Image(e));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return images;

    }
}
