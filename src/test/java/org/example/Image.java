package org.example;

import org.openqa.selenium.WebElement;

public class Image {
    private String src;
    private WebElement img;

    public Image(WebElement element) throws Exception {
        if(!element.getTagName().equals("img")){
            throw new Exception("Not a img");
        }
        img = element;
        src = img.getAttribute("src");
    }

    public String getSource(){
        return src;
    }
}
