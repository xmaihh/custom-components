package tp.custom_components.project.widget;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Date;

public class SuperCircleView extends View {

    int mInrArcRadio;   //内圆弧半径
    float mRingWidth;   //圆环的宽度
    int mSegment;        //分成几段
    int mSegmentAngle;   //每个圆弧之间的间隔
    int mMaxCircleColor;    //最外面圆的颜色
    int mRingNormalColor;    //默认圆环的颜色
    int mColorSelect;        //选中状态的颜色
    float mRingSweepAngle;  //每一段圆弧划过的角度
    int mInitAngle;     //起始角度
    int subtextSize;    //圆弧上的文字大小
    int maintextSize;   //中心圆上的文字大小
    int mMinCircleRadio;//中心圆半径
    int mMaxCircleRadio;//外圈圆半径

    int mViewCenterX;   //view宽的中心点
    int mViewCenterY;   //view高的中心点
    Paint mPaint;
    RectF mRectF;     //用来绘制圆环的矩形区域
    long touchTime;
    int mClickState = -10086;

    public SuperCircleView(Context context) {
        this(context, null);
    }

    public SuperCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInrArcRadio = 300;   //内圆弧半径
        mRingWidth = 160;   //圆环的宽度
        mSegment = 6;        //分成几段
        mSegmentAngle = 5;   //每个圆弧之间的间隔角度
        mMaxCircleColor = Color.parseColor("#e9e9e9");    //最外面圆的颜色
        mRingNormalColor = Color.parseColor("#d9d9d9");    //圆环和中心圆的颜色
        mColorSelect = Color.parseColor("#bfbfbf");          //选中状态的颜色

        mInitAngle = 240;     //起始角度  /**设置为240度即可显示竖直*/
        maintextSize = 64;   //中心圆上的text大小
        subtextSize = 48;    //圆环上的text大小
        mMinCircleRadio = 240;//中心圆半径
        mMaxCircleRadio = 520;//外圈圆半径

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        this.setWillNotDraw(false);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mViewCenterX = getMeasuredWidth() / 2;
        mViewCenterY = getMeasuredHeight() / 2;
        mRectF = new RectF(mViewCenterX - mInrArcRadio - mRingWidth / 2, mViewCenterY - mInrArcRadio - mRingWidth / 2, mViewCenterX + mInrArcRadio + mRingWidth / 2, mViewCenterY + mInrArcRadio + mRingWidth / 2);
        mRingSweepAngle = (360 - mSegment * mSegmentAngle) / mSegment;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**draw a big circle*/
        mPaint.setColor(mMaxCircleColor);
        canvas.drawCircle(mViewCenterX, mViewCenterY, mMaxCircleRadio, mPaint);

        /**add a litter circle*/

        if (mClickState == -1) {
            mPaint.setColor(mColorSelect);
        } else {
            mPaint.setColor(mRingNormalColor);
        }
        canvas.drawCircle(mViewCenterX, mViewCenterY, mMinCircleRadio, mPaint);

        /**draw text*/
        mPaint.setTextSize(maintextSize);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextAlign(Paint.Align.CENTER);
        //计算baseline
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = mViewCenterY + distance;
        canvas.drawText("OK", mViewCenterX, baseline, mPaint);   //绘制中心文字
        //画默认圆环
        drawNormalRing(canvas);
    }


    /**
     * 画默认圆环
     *
     * @param canvas
     */
    private void drawNormalRing(Canvas canvas) {
        Paint ringNormalPaint = new Paint(mPaint);
        ringNormalPaint.setStyle(Paint.Style.STROKE);
        ringNormalPaint.setStrokeWidth(mRingWidth);
        ringNormalPaint.setColor(mRingNormalColor);

        for (int i = 0; i < mSegment; i++) {
            if (mClickState == i) {
                ringNormalPaint.setColor(mColorSelect);
            } else {
                ringNormalPaint.setColor(mRingNormalColor);
            }
            canvas.drawArc(mRectF, mInitAngle + (i * mRingSweepAngle + i * mSegmentAngle), mRingSweepAngle, false, ringNormalPaint);

            float startAngle = mInitAngle + i * (mRingSweepAngle + mSegmentAngle);
            float endAngle = startAngle + mRingSweepAngle;
            float middleAndle = (startAngle + endAngle) / 2;

            float scion = 0;
            float r = mInrArcRadio + (mRingWidth / 2);
            double x1 = 0;
            double y1 = 0;
            float zz = Math.abs(middleAndle % 360);  // 求圆弧中心所在象限
            if (middleAndle > 360) {
                if (zz > 0 && zz <= 90) { // 第一
                    scion = Math.abs(zz);
                    x1 = mViewCenterX + r * Math.cos(scion * Math.PI / 180);
                    y1 = mViewCenterY + r * Math.sin(scion * Math.PI / 180);
                } else if (zz > 90 && zz <= 180) { //第二
                    scion = Math.abs(180 - zz);
                    x1 = mViewCenterX - r * Math.cos(scion * Math.PI / 180);
                    y1 = mViewCenterY + r * Math.sin(scion * Math.PI / 180);
                } else if (zz > 180 && zz <= 270) { //第三
                    scion = Math.abs(zz - 180);
                    x1 = mViewCenterX - r * Math.cos(scion * Math.PI / 180);
                    y1 = mViewCenterY - r * Math.sin(scion * Math.PI / 180);
                } else if (zz > 270 && zz < 360) { //第四
                    scion = Math.abs(360 - zz);
                    x1 = mViewCenterX + r * Math.cos(scion * Math.PI / 180);
                    y1 = mViewCenterY - r * Math.sin(scion * Math.PI / 180);
                }
            } else {
                if (zz > 0 && zz <= 90) { // 第一
                    scion = Math.abs(zz);
                    x1 = mViewCenterX + r * Math.cos(scion * Math.PI / 180);
                    y1 = mViewCenterY + r * Math.sin(scion * Math.PI / 180);
                } else if (zz > 90 && zz <= 180) { //第二
                    scion = Math.abs(180 - zz);
                    x1 = mViewCenterX - r * Math.cos(scion * Math.PI / 180);
                    y1 = mViewCenterY + r * Math.sin(scion * Math.PI / 180);
                } else if (zz > 180 && zz <= 270) { //第三
                    scion = Math.abs(zz - 180);
                    x1 = mViewCenterX - r * Math.cos(scion * Math.PI / 180);
                    y1 = mViewCenterY - r * Math.sin(scion * Math.PI / 180);
                } else if (zz > 270 && zz < 360) { //第四
                    scion = Math.abs(360 - zz);
                    x1 = mViewCenterX + r * Math.cos(scion * Math.PI / 180);
                    y1 = mViewCenterY - r * Math.sin(scion * Math.PI / 180);
                }
            }

            /***draw text**/
            mPaint.setTextSize(subtextSize);
            mPaint.setColor(Color.BLACK);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setTextAlign(Paint.Align.CENTER);
            //计算baseline
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            float baseline = (float) (y1 + distance);
            canvas.drawText("" + i, (float) x1, baseline, mPaint);   //绘制中心文字
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchTime = new Date().getTime();  //按下时间,超过300ms计算一次点击
                float evX = event.getX();
                float evY = event.getY();
                int distance = (int) xx(mViewCenterX, mViewCenterY, evX, evY); //计算点击位置距圆心的距离
                if (distance <= mInrArcRadio) {
                    mClickState = -1;  //点击坐标距离圆心的距离小于中心圆的半径，点击的是中心圆
                } else if (distance <= mInrArcRadio + mRingWidth + 40) {  //点击坐标距离圆心的距离小于大圆的半径，点击的就是圆弧
                    mClickState = getClickState(mViewCenterX, mViewCenterY, evX, evY, mInitAngle, mSegment, mRingSweepAngle);
                } else {
                    // 点击范围不计算在View内
                    mClickState = -10086;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if ((new Date().getTime() - touchTime) < 300) {
                    //点击小于300ms视为一次点击事件
                    //doOnClickListener(mClickState)

                }
                mClickState = -10086;
                invalidate();
                break;

        }
        return true;
    }

    static double xx(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.abs((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
    }

    /**
     * Math.max(min,current) == Math.min(current,max)
     *
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @return
     */
    static int getClickState(float x0, float y0, float x1, float y1, int mInitAngle, int count, float mRingSweepAngle) {
        int state = -10086;
        if (x1 > x0 && y1 <= y0) {         //数学第一象限
            if (mInitAngle > 270) {
                state = getSection(mInitAngle + 90 - getAngle(x0, y0, x1, y1), count, mInitAngle, mRingSweepAngle);
            } else {
                state = getSection(Math.abs(270 - mInitAngle) + mInitAngle + (90 - getAngle(x0, y0, x1, y1)), count, mInitAngle, mRingSweepAngle);
            }
        } else if (x1 <= x0 && y1 < y0) {  //数学第二象限
            if (mInitAngle > 270) {
                state = getSection(mInitAngle + 270 + getAngle(x0, y0, x1, y1), count, mInitAngle, mRingSweepAngle);
            } else {
                state = getSection(Math.abs(270 - mInitAngle) + mInitAngle + 270 + getAngle(x0, y0, x1, y1), count, mInitAngle, mRingSweepAngle);
            }
        } else if (x1 < x0 && y1 >= y0) {   //数学第三象限
            if (mInitAngle > 270) {
                state = getSection(mInitAngle + 270 - getAngle(x0, y0, x1, y1), count, mInitAngle, mRingSweepAngle);
            } else {
                state = getSection(Math.abs(270 - mInitAngle) + mInitAngle + 270 - getAngle(x0, y0, x1, y1), count, mInitAngle, mRingSweepAngle);
            }
        } else if (x1 >= x0 && y1 > y0) {   //数学第四象限
            if (mInitAngle > 270) {
                state = getSection(mInitAngle + 90 + getAngle(x0, y0, x1, y1), count, mInitAngle, mRingSweepAngle);
            } else {
                state = getSection(Math.abs(270 - mInitAngle) + mInitAngle + 90 + getAngle(x0, y0, x1, y1), count, mInitAngle, mRingSweepAngle);
            }
        }
        return state;
    }

    /**
     * 获得圆上一点与圆心夹角
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    static int getAngle(float x1, float y1, float x2, float y2) {
        float x = Math.abs(x1 - x2);
        float y = Math.abs(y1 - y2);
//        double z = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double tan = y / x;
        double radian = Math.atan(tan);
        double angle = 180 / (Math.PI / radian);
        return (int) angle;
    }

    /**
     * Math.max(min,current) == Math.min(current,max)
     * 获取一个数在区间内
     *
     * @param current
     * @param count
     * @return
     */
    static int getSection(int current, int count, int mInitAngle, float mRingSweepAngle) {
        if (current > mInitAngle + 360) {
            return 0;
        }
        for (int i = 0; i < count; i++) {
            int min = (int) (mInitAngle + (360 / count) * i);
            int max = (int) (min + mRingSweepAngle);

            int a = Math.max(min, current);
            int b = Math.min(current, max);
            if (a == b) {
                return i;
            }
        }
        return -10086;
    }
}
