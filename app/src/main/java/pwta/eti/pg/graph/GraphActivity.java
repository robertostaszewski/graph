package pwta.eti.pg.graph;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class GraphActivity extends AppCompatActivity {

    private GraphView graphView;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        float aValue = intent.getFloatExtra(ParamActivity.A_VALUE, 1f);
        float bValue = intent.getFloatExtra(ParamActivity.B_VALUE, 1f);
        float cValue = intent.getFloatExtra(ParamActivity.C_VALUE, 1f);
        graphView = new GraphView(this, aValue, bValue, cValue);
        setContentView(graphView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent.putExtra(ParamActivity.A_VALUE, graphView.getA());
        intent.putExtra(ParamActivity.B_VALUE, graphView.getB());
        intent.putExtra(ParamActivity.C_VALUE, graphView.getC());
        setResult(RESULT_OK, intent);
        finish();
    }
}
