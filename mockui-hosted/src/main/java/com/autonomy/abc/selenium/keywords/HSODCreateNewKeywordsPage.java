package com.autonomy.abc.selenium.keywords;

import com.autonomy.abc.selenium.language.LanguageDropdown;
import com.autonomy.abc.selenium.language.WarningLanguageDropdown;
import com.hp.autonomy.frontend.selenium.util.ParametrizedFactory;
import org.openqa.selenium.WebDriver;

public class HSODCreateNewKeywordsPage extends CreateNewKeywordsPage {

    private HSODCreateNewKeywordsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected LanguageDropdown languageDropdown() {
        return new WarningLanguageDropdown();
    }
    
    public static class Factory implements ParametrizedFactory<WebDriver, HSODCreateNewKeywordsPage> {
        @Override
        public HSODCreateNewKeywordsPage create(WebDriver context) {
            CreateNewKeywordsPage.waitForLoad(context);
            return new HSODCreateNewKeywordsPage(context);
        }
    }
}
