package com.autonomy.abc.selenium.search;

import com.autonomy.abc.selenium.util.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParametricFilter implements SearchFilter {
    private List<String> categories;
    private List<String> fields;

    public ParametricFilter(String category, String field){
        categories = new ArrayList<>();
        fields = new ArrayList<>();

        categories.add(category);
        fields.add(field);
    }

    public ParametricFilter(Collection<String> categories, Collection<String> fields){
        if(categories.size() != fields.size()){
            throw new IllegalArgumentException("Categories and Fields have different sizes");
        }

        this.categories = new ArrayList<>(categories);
        this.fields = new ArrayList<>(fields);
    }

    @Override
    public void apply(SearchFilter.Filterable page) {
        if(page instanceof Filterable){
            Filterable filterable = (Filterable) page;
            WebElement parametricContainer = filterable.parametricContainer();
            uncheckAll(parametricContainer, filterable);

            for(int i = 0; i < categories.size(); i++) {
                WebElement filterContainer = filterContainer(parametricContainer, categories.get(i));
                fieldCheckbox(filterContainer, fields.get(i)).click();
                filterable.waitForParametricValuesToLoad();
            }
        }
    }

    private WebElement filterContainer(WebElement parametricContainer, String category){
        return parametricContainer.findElement(By.cssSelector("[data-field='" + category.toLowerCase().replace(" ","_") + "']"));
    }

    private WebElement fieldCheckbox(WebElement filterContainer, String field){
        return filterContainer.findElement(By.cssSelector("[data-value='" + field.toUpperCase() + "']"));
    }

    private boolean isUnchecked(WebElement checkBox){
        return ElementUtil.hasClass("hide", checkBox) && !ElementUtil.hasClass("checked", checkBox);
    }

    private void uncheckAll(WebElement parametricContainer, Filterable filterable){
        List<WebElement> checkboxes;

        checkboxes = parametricContainer.findElements(By.className("icheckbox_square-green"));
        if(checkboxes.isEmpty()){
            parametricContainer.findElements(By.className("fa-check"));
        }

        for(WebElement element : checkboxes){
            if(isUnchecked(element)){
                element.click();
                filterable.waitForParametricValuesToLoad();
            }
        }
    }

    public interface Filterable extends SearchFilter.Filterable{
        WebElement parametricContainer();
        void waitForParametricValuesToLoad();
    }
}
