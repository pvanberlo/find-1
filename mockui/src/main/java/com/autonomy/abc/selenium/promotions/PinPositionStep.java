package com.autonomy.abc.selenium.promotions;

import com.autonomy.abc.selenium.actions.wizard.WizardStep;
import com.autonomy.abc.selenium.page.promotions.CreateNewPromotionsPage;

public class PinPositionStep implements WizardStep {
    private CreateNewPromotionsPage page;
    private int position;

    public PinPositionStep(CreateNewPromotionsPage page, int position) {
        this.page = page;
        this.position = position;
    }

    @Override
    public String getTitle() {
        return "Promotion details";
    }

    @Override
    public void apply() {
        for (int i=1; i<position; i++) {
            page.selectPositionPlusButton().click();
        }
    }
}
