package pedrocarrillo.com.materialstepper;

import android.content.Context;

import pedrocarrillo.com.materialstepperlibrary.StepView;

/**
 * Created by pedrocarrillo on 9/20/16.
 */

public class Step1Test extends StepView {

    public Step1Test(Context context) {
        super(context);
    }

    @Override
    public int customStepResource() {
        return R.layout.test_step_1_expanded;
    }

    @Override
    public boolean onStepSuccess() {
        return true;
    }

    @Override
    public String showTitle() {
        return "Step "+stepNumber;
    }

    @Override
    public void onStepCancel() {

    }

    @Override
    public String showSelection() {
        return "selected item 1";
    }

}
