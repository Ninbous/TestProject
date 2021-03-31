package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class GooglePage implements SearchPageInterface {

    private String url = "https://www.google.com/";

    public WebDriver driver;

    public GooglePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void openPage() {
        driver.get(url);
    }

    @FindBy(xpath = "//form[@role='search']//input[@name='q']")
    private WebElement searchField;

    @FindBy(xpath = "//form[@role='search']//*[@type='submit']")
    private WebElement searchButton;

    public void search(String string) {
        searchField.sendKeys(string);
        searchButton.click();
    }

    @FindBy(xpath = "//*[@role='navigation']//*[contains(text(),'Картинки')]")
    private WebElement imageTabButton;

    public void switchImagesTab() {
        imageTabButton.click();
    }

    private String imgElementsXpath = "//div[@id='islrg']//*[@data-ri]//img";

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
