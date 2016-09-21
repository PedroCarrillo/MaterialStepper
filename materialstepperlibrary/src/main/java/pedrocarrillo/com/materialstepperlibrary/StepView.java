package pedrocarrillo.com.materialstepperlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import pedrocarrillo.com.materialstepperlibrary.interfaces.StepLayoutInterface;

/**
 * Created by pedrocarrillo on 9/14/16.
 */

public abstract class StepView extends LinearLayout implements View.OnClickListener {

    private TextView tvTitle, tvSelection, tvPosition;
    private LinearLayout llStepContentContainer, separator;
    private Button btnAccept, btnCancel;
    private ViewStub vsContent;
    private StepLayoutInterface stepLayoutInterface;

    protected int stepNumber;
    public STATE currentState = STATE.WAIT;

    enum STATE {
        WAIT,
        SELECTED,
        RESOLVED
    }

    public StepView(Context context) {
        super(context);
        initView();
    }

    public StepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public abstract @LayoutRes int customStepResource();
    public abstract boolean onStepSuccess();
    public abstract String showTitle();
    public abstract void onStepCancel();
    public abstract String showSelection();

    public void setStepLayoutInterface(StepLayoutInterface stepLayoutInterface) {
        this.stepLayoutInterface = stepLayoutInterface;
    }

    public void setWaitState() {
        currentState = STATE.WAIT;
        tvPosition.setActivated(false);
        tvPosition.setSelected(false);
        tvSelection.setVisibility(View.GONE);

        llStepContentContainer.setVisibility(View.GONE);

    }

    public void setResolvedState() {
        currentState = STATE.RESOLVED;
        tvSelection.setVisibility(View.VISIBLE);
        tvPosition.setSelected(true);
        llStepContentContainer.setVisibility(View.GONE);

        tvPosition.setText("");
        tvSelection.setText(showSelection());

    }

    public void setSelectedState() {
        currentState = STATE.SELECTED;
        tvSelection.setVisibility(View.GONE);
        tvPosition.setActivated(true);
        tvPosition.setSelected(false);

        llStepContentContainer.setVisibility(View.VISIBLE);
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
        tvPosition.setText(String.valueOf(stepNumber));
        tvTitle.setText(showTitle());
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.step_view_layout, this, true);
        vsContent = (ViewStub) findViewById(R.id.vs_content);
        tvPosition = (TextView) findViewById(R.id.tvPosition);
        tvSelection = (TextView) findViewById(R.id.tv_selection);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        separator = (LinearLayout) findViewById(R.id.separator);
        llStepContentContainer = (LinearLayout) findViewById(R.id.step_content_container);
        btnAccept = (Button) findViewById(R.id.btn_accept);
        btnCancel = (Button) findViewById(R.id.btn_cancel);

        btnAccept.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        vsContent.setLayoutResource(customStepResource());
        vsContent.inflate();

    }

    public void showSeparator(boolean show) {
        separator.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_accept) {
           if (onStepSuccess()) {
               stepLayoutInterface.onStepSuccess();
           }
        } else if (v.getId() == R.id.btn_cancel) {
            onStepCancel();
            stepLayoutInterface.onStepCancel();
        }
    }
}
