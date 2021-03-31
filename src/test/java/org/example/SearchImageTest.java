package org.example;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchImageTest {

    public static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfigProperties.getProperty("chromedriver"));
        System.setProperty("webdriver.chrome.whitelistedIps", ""); //--allowed-ips
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void searchImageTest() {
        SearchPageInterface page = new YandexPage(driver);
        page.openPage();

        Date date = new Date();
        String dateFormatted = String.format("%1$td %1$tB", date);

        page.search(dateFormatted);
        page.switchImagesTab();

        WebDriverWait wait = new WebDriverWait(driver, 3);

        List<Image> images = page.getAllImagesFromImageTab();

        Assert.assertNotNull(images);
        Assert.assertFalse(images.isEmpty());


        Image imageObj = images.get(2);
        String imgSrc = imageObj.getSource();

        System.out.println(imgSrc);

        if (Base64Helper.isBase64(imgSrc)) {
            try {
                String imageString = imgSrc.split(",")[1];
                byte[] imageByte = Base64.getDecoder().decode(imageString);
                ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                BufferedImage image = ImageIO.read(bis);
                bis.close();
                ImageIO.write(image, "png", new File("_data/image.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else { //Тут можно сделать регулярку на проверку урл ли эта строка?
            try {
                URL imageURL = new URL(imgSrc);
                BufferedImage image = ImageIO.read(imageURL);
                ImageIO.write(image, "png", new File("_data/image.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

}
