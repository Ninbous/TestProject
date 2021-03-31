package org.example;

import java.util.List;

public interface SearchPageInterface {

    public void openPage();

    public void switchImagesTab();

    public List<Image> getAllImagesFromImageTab();

    public void search(String string);
}
