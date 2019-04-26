package tp.custom_components.project.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import tp.custom_components.R;

/**
 * 矩形Text
 * +-------------------------------- This is Title ---------------------------+
 * |                                                                          |
 * |                                                                          |
 * |                                                                          |
 * |    +------+    +------+    +------+    +------+   +------+     +------+  |
 * |    |      |    |      |    |      |    |      |   |      |     |      |  |
 * |    |      |    |      |    |      |    |      |   |      |     |      |  |
 * |    |      |    |      |    |      |    |      |   |      |     |      |  |
 * |    +------+    +------+    +------+    +------+   +------+     +------+  |
 * |                                                                          |
 * |                                                                          |
 * +--------------------------------------------------------------------------+
 */


public class RectangleTextView extends View {
    private int mViewCenterX;   //view宽的中心点
    private int mViewCenterY;   //view高的中心点
    private int textSize = 36;
    private int textColor = Color.parseColor("#FFFFFFFF");
    private String text = "基础功能";
    private int lineWidth = 1;
    private int lineTextPadding = 20;
    private int lineColor = Color.parseColor("#FFFFFFFF");
    private Paint mPaint;

    public RectangleTextView(Context context) {
        this(context, null);
    }

    public RectangleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectangleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    void init(Context ctx, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        //获取自定义属性。
        TypedArray ta = ctx.obtainStyledAttributes(attrs, R.styleable.RectangleTextView);
        text = ta.getString(R.styleable.RectangleTextView_strText);
        textSize = (int) ta.getDimension(R.styleable.RectangleTextView_strTextSize, 36);
        textColor = ta.getColor(R.styleable.RectangleTextView_strTextColor, Color.parseColor("#FFFFFFFF"));
        lineColor = ta.getColor(R.styleable.RectangleTextView_lineColor, Color.parseColor("#FFFFFFFF"));
        lineWidth = (int) ta.getDimension(R.styleable.RectangleTextView_lineWidth, 2);
        lineTextPadding = (int) ta.getDimension(R.styleable.RectangleTextView_lineTextPadding, 20);
        ta.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mViewCenterX = getMeasuredWidth() / 2;
        mViewCenterY = getMeasuredHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //写文字
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize); //以px为单位
        mPaint.setTextAlign(Paint.Align.CENTER);

//        //获取文字宽度
//        int width = (int) mPaint.measureText(text);
//
//        //获取文字高度
//        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
//        int top = fm.top;
//        int bottom = fm.bottom;
//        int height = bottom - top;

        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);
        int w = Math.abs(rect.right - rect.left);
        int h = Math.abs(rect.top - rect.bottom);

        canvas.drawText(text, mViewCenterX, h, mPaint);

        //画基线
        mPaint.setColor(lineColor);
        mPaint.setStrokeWidth(lineWidth);
        canvas.drawLine(0 + lineWidth, h / 2, mViewCenterX - lineTextPadding - w / 2, h / 2, mPaint);  // 左--
        canvas.drawLine(mViewCenterX + lineTextPadding + w / 2, h / 2, mViewCenterX * 2 - lineWidth, h / 2, mPaint);  // 右--
        canvas.drawLine(0 + lineWidth, h / 2, 0 + lineWidth, h / 2 + mViewCenterY * 2, mPaint); // 左|
        canvas.drawLine(mViewCenterX * 2 - lineWidth, h / 2, mViewCenterX * 2 - lineWidth, h / 2 + mViewCenterY * 2, mPaint);// 右 |
        canvas.drawLine(0 + lineWidth, mViewCenterY * 2, mViewCenterX * 2 - lineWidth, mViewCenterY * 2, mPaint);// bottom --------
    }
}
