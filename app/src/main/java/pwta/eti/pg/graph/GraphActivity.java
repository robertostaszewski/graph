package pwta.eti.pg.graph;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

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
        RelativeLayout linearLayout = new RelativeLayout(this);
        linearLayout.addView(graphView);

        Button saveButton = new Button(this);
        saveButton.setText("Zapisz wykres");
        RelativeLayout.LayoutParams buttonParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        linearLayout.addView(saveButton, buttonParams);

        setContentView(linearLayout);

        saveButton.setOnClickListener(onClick -> {
            Bitmap screenShot = getScreenShot(graphView);
            store(screenShot, "filename" + System.currentTimeMillis() + ".png");
        });
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

    public Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public void store(Bitmap bm, String fileName) {
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/graph/Screenshots";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dirPath, fileName);
        try (FileOutputStream fOut = new FileOutputStream(file)){
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            Toast.makeText(this, "Saved image to: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
