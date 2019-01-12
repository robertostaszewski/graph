package pwta.eti.pg.graph;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class ParamActivity extends AppCompatActivity {

    public static final String A_VALUE = "aValue";
    public static final String B_VALUE = "bValue";
    public static final String C_VALUE = "cValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        final EditText aText = findViewById(R.id.A);
        final EditText bText = findViewById(R.id.B);
        final EditText cText = findViewById(R.id.C);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            float aValue = getFloatValue(aText);
            float bValue = getFloatValue(bText);
            float cValue = getFloatValue(cText);
            Intent intent = new Intent(this, GraphActivity.class);
            intent.putExtra(A_VALUE, aValue);
            intent.putExtra(B_VALUE, bValue);
            intent.putExtra(C_VALUE, cValue);
            startActivityForResult(intent, 1);
        });
    }

    private float getFloatValue(EditText aText) {
        String s = aText.getText().toString();
        return s.isEmpty() ? 0f : Float.parseFloat(s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1 && resultCode == RESULT_OK && intent != null) {
            float aValue = intent.getFloatExtra(A_VALUE, 1f);
            float bValue = intent.getFloatExtra(B_VALUE, 1f);
            float cValue = intent.getFloatExtra(C_VALUE, 1f);

            EditText aText = findViewById(R.id.A);
            EditText bText = findViewById(R.id.B);
            EditText cText = findViewById(R.id.C);

            aText.setText(String.valueOf(aValue));
            bText.setText(String.valueOf(bValue));
            cText.setText(String.valueOf(cValue));
        }
    }
}
