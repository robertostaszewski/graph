package pwta.eti.pg.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.text.DecimalFormat;

public class GraphView extends View {

    private float a, b, c;
    private float y_max, extremum;
    private float xScale;
    private Paint funPaint;
    private Paint linePaint;
    private Paint zeroLinePaint;
    private Paint textPaint;
    private DecimalFormat decimalFormat;

    public GraphView(Context context) {
        super(context);
    }

    public GraphView(Context context, float a, float b, float c) {
        super(context);
        funPaint = new Paint();
        funPaint.setColor(Color.BLACK);
        funPaint.setStrokeWidth(6f);
        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(3f);
        zeroLinePaint = new Paint();
        zeroLinePaint.setColor(Color.GRAY);
        zeroLinePaint.setStrokeWidth(3f);
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(30f);
        decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        this.a = a;
        this.b = b;
        this.c = c;
        xScale = 10f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth(), height = getHeight();
        extremum = this.a == 0 ? 0 : -this.b / (2 * this.a);
        float xStart = extremum - xScale;
        float step = 2 * xScale / width;
        y_max = fun(xStart);
        float v = Math.abs((y_max - fun(extremum)) / height);

        for (int w = 0; w < width; w++) {
            canvas.drawPoint(w, (float) (height / 1.25) - (fun(xStart += step) - fun(extremum)) / v, funPaint);
        }

        drawXAxis(canvas, width, height);
        drawYAxis(canvas, width, height);
    }

    private void drawXAxis(Canvas canvas, int width, int height) {
        float heightScale = (float) (height / 1.25);
        float scaleStep = width / 10;
        canvas.drawLine(0, heightScale, width, heightScale, linePaint);
        float w = extremum - xScale;
        canvas.drawLine(scaleStep * 5 * (xScale - extremum) / xScale, 0, scaleStep * 5 * (xScale - extremum) / xScale, height, zeroLinePaint);


        for (float i = 0; i <= width; i += scaleStep) {
            canvas.drawLine(i, heightScale + 30, i, heightScale - 30, linePaint);
            canvas.drawText(decimalFormat.format(w), i, heightScale + 60, textPaint);
            w += 2 * scaleStep * (xScale / width);
        }
    }

    private void drawYAxis(Canvas canvas, int width, int height) {
        int widthScale = width / 2;
        canvas.drawLine(widthScale, 0, widthScale, height, linePaint);
        float v = Math.abs((y_max - fun(extremum)) / height);
        int scaleStep = width / 10;
        canvas.drawLine(0, (float) (height / 1.25 * v + fun(extremum)) / v, width, (float) (height / 1.25 * v + fun(extremum)) / v, zeroLinePaint);

        for (float i = scaleStep; i < height; i += scaleStep) {
            canvas.drawLine(widthScale + 30, i, widthScale - 30, i, linePaint);
            double number = height / 1.25 * v - i * v + fun(extremum);
            canvas.drawText(decimalFormat.format(number), widthScale + 40, i, textPaint);
        }
    }

    public float fun(float x) {
        return a * x * x + b * x + c;
    }

    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }

    public float getC() {
        return c;
    }


    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                b = dx / xScale * 2 * a + b;
                c = dy + c;
                break;


        }

        mPreviousX = x;
        mPreviousY = y;

        invalidate();
        return true;
    }
}
