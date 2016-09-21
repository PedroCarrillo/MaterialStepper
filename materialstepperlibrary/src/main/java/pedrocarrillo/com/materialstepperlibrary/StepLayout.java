package pedrocarrillo.com.materialstepperlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import pedrocarrillo.com.materialstepperlibrary.interfaces.StepLayoutInterface;
import pedrocarrillo.com.materialstepperlibrary.interfaces.StepLayoutResult;

/**
 * Created by pedrocarrillo on 9/14/16.
 */

public class StepLayout extends LinearLayout implements StepLayoutInterface {

    private List<StepView> stepViews = new ArrayList<>();
    private StepperType stepperType = StepperType.VERTICAL;
    private StepLayoutResult stepLayoutResult;
    private int currentStep = 0;

    enum StepperType {
        VERTICAL
    }

    public void setStepperType(StepperType stepperType) {
        this.stepperType = stepperType;
    }

    public void setStepLayoutResult(StepLayoutResult stepLayoutResult) {
        this.stepLayoutResult = stepLayoutResult;
    }

    public void addStepView(StepView stepView) {
        stepView.setStepLayoutInterface(this);
        stepViews.add(stepView);
        stepView.setStepNumber(stepViews.size());
    }

    public StepLayout(Context context) {
        super(context);
    }

    public StepLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StepLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void load() {
        setOrientation(StepView.VERTICAL);
        for (int i = 0; i < stepViews.size(); i++) {
            StepView stepView = stepViews.get(i);
            addView(stepView);
            if (i == 0) {
                stepView.setSelectedState();
            } else {
                if (i == stepViews.size() - 1) {
                    stepView.showSeparator(false);
                }
                stepView.setWaitState();
            }
        }
    }

    @Override
    public void onStepSuccess() {
        if (currentStep + 1 < stepViews.size()) {
            stepViews.get(currentStep).setResolvedState();
            currentStep++;
            stepViews.get(currentStep).setSelectedState();
        } else {
            if (stepLayoutResult != null) {
                stepViews.get(currentStep).setResolvedState();
                stepLayoutResult.onFinish();
            }
        }
    }

    @Override
    public void onStepCancel() {
        if (currentStep - 1 >= 0) {
            stepViews.get(currentStep).setWaitState();
            currentStep--;
            stepViews.get(currentStep).setStepNumber(currentStep+1);
            stepViews.get(currentStep).setSelectedState();
        } else {
            if (stepLayoutResult != null) {
                stepLayoutResult.onCancel();
            }
        }
    }
}
