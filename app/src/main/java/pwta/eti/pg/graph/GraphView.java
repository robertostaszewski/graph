package pwta.eti.pg.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class GraphView extends View {

    float a = 0.05f, b = 3, c = 4;
    int y_const = 1900;
    float y_min = fun(-b/(2*a));
    float y_max = y_min + y_const;
    Paint funPaint;
    float scale = y_const/a;

    public GraphView(Context context) {
        super(context);
        funPaint = new Paint();
        funPaint.setColor(Color.BLACK);
        funPaint.setStrokeWidth(7f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth(), height = getHeight();
        y_max = fun(width);
        float x = 0;
        for (int w = 0; w < width; w++) {
            canvas.drawPoint(w, fun(w)*y_const/y_max, funPaint);
            x += scale/width;
        }
    }

    public float fun(float x) {
        float result = a * x * x + b * x + c;
        Log.d("fun", "wartosc = " + result);
        return result;
    }

}
