package com.autonomy.abc.selenium.find;

import com.hp.autonomy.frontend.selenium.util.AppElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class Service extends AppElement {
    private WebElement left;
    private WebElement center;
    private WebElement right;

    public Service(WebDriver driver) {
        super(driver.findElement(By.className("service-view-container")), driver);
    }

    public WebElement getRelatedConcepts() {
        return findElement(By.className("related-concepts-list"));
    }

    public void filterByIndex(String domain, String index) {
        System.out.println(domain);
        System.out.println(index.replace(" ", "%20"));

        findElement(By.cssSelector("[data-id='" + domain + ":" + index.replace(" ", "%20") + "']")).click();
    }

    public boolean cBoxFirstDocument() {
        String[] current = getDriver().findElement(By.id("cboxCurrent")).getText().split(" ");

        if (Integer.parseInt(current[0]) == 1){
            return true;
        }

        return false;
    }

    public WebElement viewBoxNextButton() {
        return getDriver().findElement(By.className("nextBtn"));
    }

    public List<WebElement> getPromotions() {
        return getPromotionsDiv().findElements(By.className("promoted-document"));
    }

    public List<String> getPromotionsTitles(){
        List<String> titles = new ArrayList<>();
        for(WebElement promotion : getPromotions()){
            titles.add(promotion.findElement(By.tagName("h4")).getText());
        }
        return titles;
    }

    private WebElement getPromotionsDiv(){
        return findElement(By.className("promotions"));
    }

    public void filterByDate(DateEnum date) {
        findElement(By.cssSelector("td[data-id='" + date.toString().toLowerCase() + "']")).click();
    }

    public void filterByDate(String start, String end){
        filterByDate(DateEnum.CUSTOM);

        inputDate("results-filter-min-date", start);
        inputDate("results-filter-max-date", end);
    }

    private void inputDate(String inputElementCSS,String inputString){
        WebElement inputBox = findElement(By.cssSelector("."+inputElementCSS+" input"));
        inputBox.sendKeys(inputString);
    }

    public List<String> getResultTitles() {
        List<String> titles = new ArrayList<>();
        for(WebElement result : getResults()){
            titles.add(result.findElement(By.tagName("h4")).getText());
        }
        return titles;
    }

    public WebElement getCBoxLoadedContent() {
        return getDriver().findElement(By.id("cboxLoadedContent"));
    }

    public WebElement getStartDateFilter() {
        return findElement(By.cssSelector(".results-filter-min-date input"));
    }

    public WebElement getEndDateFilter() {
        return findElement(By.cssSelector(".results-filter-max-date input"));
    }

    public enum DateEnum {
        WEEK,
        MONTH,
        YEAR,
        CUSTOM
    }

    private enum ParametricContainers {
        AUTHOR("author"),
        CONTENTTYPE("content_type");

        private final String dataField;

        ParametricContainers(String dataField){
            this.dataField = dataField;
        }

        public String getDataField(){
            return dataField;
        }
    }

    private WebElement getParametricContainer(ParametricContainers param) {
        return getParametricContainer(param.dataField);
    }

    public WebElement getParametricContainer(String param){
        return findElement(By.className("parametric-container")).findElement(By.cssSelector("[data-field='" + param + "']"));
    }

    private WebElement getContentTypeContainer(){
        return getParametricContainer(ParametricContainers.CONTENTTYPE);
    }

    public void refreshContainers(){
        left = getDriver().findElement(By.className("left-side-container"));
        center = getDriver().findElement(By.className("middle-container"));
        right = getDriver().findElement(By.className("right-side-container"));
    }


    public void selectContentType(String contentType){
        waitForSearchLoadIndicatorToDisappear(Service.Container.LEFT);
        getContentTypeContainer().findElement(By.cssSelector("[data-value='" + contentType.toUpperCase() + "']")).click();
        waitForSearchLoadIndicatorToDisappear(Container.MIDDLE);
    }

    public WebElement getResultsDiv(){
        return getDriver().findElement(By.className("results"));
    }

    public List<WebElement> getResults(){
        return getResultsDiv().findElements(By.cssSelector("[data-rel='results']"));
    }

    public List<String> getDisplayedDocumentsDocumentTypes(){
        List<String> documentTypes = new ArrayList<String>();
        for(WebElement result : getResults()){
            documentTypes.add(result.findElement(By.cssSelector(".content-type i")).getAttribute("class"));
        }
        return documentTypes;
    }

    public enum Container{
        LEFT("left-side"),
        MIDDLE("middle"),
        RIGHT("right-side");

        private final String container;

        Container(String container) {
            this.container = container;
        }

        public String getContainer() {
            return container;
        }
    }

    public void waitForSearchLoadIndicatorToDisappear(final Container container){
        try {
            new WebDriverWait(getDriver(), 5).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("." + container.getContainer() + "-container .loading-spinner")));
        } catch (Exception e) {
            //Noop
        }

        new WebDriverWait(getDriver(),60).until(new ExpectedCondition<Boolean>(){

            @Override
            public Boolean apply(WebDriver driver) {
                List<WebElement> indicators = findElement(By.className(container.getContainer()+"-container")).findElements(By.className("loading-spinner"));

                if(indicators.size() == 0) {
                    return true;
                }

                for(WebElement indicator : indicators){
                    if(container == Container.RIGHT) {
                        if(!indicator.findElement(By.xpath("..")).getAttribute("class").contains("hide")) {
                            return false;
                        }
                    } else if (!indicator.getAttribute("class").contains("hide")){
                        return false;
                    }
                }

                return true;
            }
        });
    }

    public WebElement getSearchResult(int searchResultNumber) {
        return findElement(By.cssSelector(".results div:nth-child(" + searchResultNumber + ")"));
    }

    public WebElement getSearchResultTitle(int searchResultNumber) {
        return getSearchResult(searchResultNumber).findElement(By.tagName("h4"));
    }

    public WebElement getViewMetadata() {
        return new WebDriverWait(getDriver(),10).until(ExpectedConditions.visibilityOfElementLocated(By.className("view-server-document-info")));
    }

    public void closeViewBox() {
        getDriver().findElement(By.id("cboxClose")).click();
    }
}
