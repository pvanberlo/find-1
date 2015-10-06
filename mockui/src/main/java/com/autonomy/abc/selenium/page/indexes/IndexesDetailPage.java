package com.autonomy.abc.selenium.page.indexes;

import com.autonomy.abc.selenium.page.SAASPageBase;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.hp.autonomy.frontend.selenium.util.AppElement;
import com.hp.autonomy.frontend.selenium.util.AppPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IndexesDetailPage extends SAASPageBase {
    public IndexesDetailPage(WebDriver driver) {
        super(driver);
    }

    public static IndexesDetailPage make(WebDriver driver) {
        new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()[contains(.,'Index fields')]]")));
        return new IndexesDetailPage(driver);
    }

    public String getIndexHeader(){
        return getDriver().findElement(By.cssSelector(".affix-element h1 strong")).getText();   //TODO actually in the top nav bar
    }

    public String getIndexTitle() {
        return findElement(By.className("index-md-name")).getText();
    }

    public String getCreatedDate() {
        return findElement(By.className("index-md-create-date")).getText();
    }

    private WebElement button(String buttonText){
        return findElement(By.xpath("//div[contains(@class,'affix-element')]//a[text()[contains(.,'" + buttonText + "')]]"));
    }

    public WebElement deleteButton(){
        return button("Delete");
    }

    public WebElement confirmDeleteButton() {
        return getDriver().findElement(By.cssSelector("#confirmDeleteBtns [type='submit']")); //Outside of page
    }

    private WebElement addURLInput(){
        return findElement(By.name("urlInput"));
    }

    public void addSiteToIndex(String url){
        WebElement inputBox = addURLInput();
        inputBox.sendKeys(url);
        inputBox.findElement(By.xpath(".//..//i")).click();
    }
}
