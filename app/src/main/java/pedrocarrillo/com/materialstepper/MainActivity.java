package pedrocarrillo.com.materialstepper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import pedrocarrillo.com.materialstepperlibrary.StepLayout;
import pedrocarrillo.com.materialstepperlibrary.StepView;
import pedrocarrillo.com.materialstepperlibrary.interfaces.StepLayoutResult;

public class MainActivity extends AppCompatActivity {

    private StepLayout stepLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepLayout = (StepLayout) findViewById(R.id.stepLayout);
        Step1Test step1 = new Step1Test(this);

        Step1Test step2 = new Step1Test(this);
        Step1Test step3 = new Step1Test(this);
        Step1Test step4 = new Step1Test(this);

        stepLayout.addStepView(step1);
        stepLayout.addStepView(step2);
        stepLayout.addStepView(step3);
        stepLayout.addStepView(step4);

        stepLayout.setStepLayoutResult(new StepLayoutResult() {
            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "YOU FINISHED", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

                Toast.makeText(MainActivity.this, "you want to go back?", Toast.LENGTH_SHORT).show();
            }
        });

        stepLayout.load();


    }
}
