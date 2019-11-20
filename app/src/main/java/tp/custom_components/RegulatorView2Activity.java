package tp.custom_components;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import tp.custom_components.project.widget.RegulatorView2;

public class RegulatorView2Activity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private RegulatorView2 mRVtemperature;
    private RegulatorView2 mRVtime;
    private RegulatorView2 mRVrpm;
    private int mTemperature = 10;
    private TextView tvTemperature;
    private TextView tvTime;
    private TextView tvRpm;

    private Map<Integer, Boolean> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulatorview2);
    }

    private void initView() {
        mRVtime = findViewById(R.id.regulator_view_time);
        mRVrpm = findViewById(R.id.regulator_view_rpm);
        mRVtemperature = findViewById(R.id.regulator_view_temperature);

        tvTemperature = findViewById(R.id.tv_temperature);
        tvTime = findViewById(R.id.tv_time);
        tvRpm = findViewById(R.id.tv_rpm);

        mRVtemperature.setIsOpenBacklightAnim(true);
        mRVtemperature.setCurAngle(0.8f, true);
        mRVtemperature.setProgressChangeListener(new RegulatorView2.OnProgressChangeListener() {
            @Override
            public void onProgress(float progress) {
                int value = (int) (100 + 182 * progress);
                if (mTemperature != value) {
                    mTemperature = value;
                    mRVtemperature.setCenterTitle(mTemperature + "");
                }
            }
        });

        mRVtemperature.setOnClickListener(this);
        mRVtime.setOnClickListener(this);
        mRVrpm.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        tvTemperature.setOnClickListener(this);
        tvRpm.setOnClickListener(this);
        mRVtemperature.setOnTouchListener(this);
        mRVtime.setOnTouchListener(this);
        mRVrpm.setOnTouchListener(this);

        map = new HashMap<>(3);
        map.put(R.id.regulator_view_temperature, false);  //默认选中，不进行放大
        map.put(R.id.regulator_view_time, true);
        map.put(R.id.regulator_view_rpm, true);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.regulator_view_temperature:
            case R.id.tv_temperature:
                setRVtemperatureSelected(true);
                setRVtimeSelected(false);
                setRVrpmSelected(false);
                break;
            case R.id.regulator_view_time:
            case R.id.tv_time:
                setRVtemperatureSelected(false);
                setRVtimeSelected(true);
                setRVrpmSelected(false);
                break;
            case R.id.regulator_view_rpm:
            case R.id.tv_rpm:
                setRVtemperatureSelected(false);
                setRVtimeSelected(false);
                setRVrpmSelected(true);
                break;
            default:
                break;
        }
    }

    private void setRVtemperatureSelected(boolean isSelected) {
        map.put(R.id.regulator_view_temperature, !isSelected);
        tvTemperature.setTextColor(isSelected ? 0xff00ffc0 : 0xffffffff);
        mRVtemperature.setCenterTitleColor(isSelected ? 0xff00ffc0 : 0xffffffff);
        mRVtemperature.setBottomTitleColor(isSelected ? 0xff00ffc0 : 0xffffffff);
        mRVtemperature.setIsForbidSlide(!isSelected);
        mRVtemperature.setIsOpenBacklightAnim(isSelected);
        mRVtemperature.setSecondCircleShadowColor(isSelected ? 0xff00ffc0 : 0x00000000);
    }

    private void setRVtimeSelected(boolean isSelected) {
        map.put(R.id.regulator_view_time, !isSelected);
        tvTime.setTextColor(isSelected ? 0xff00ffc0 : 0xffffffff);
        mRVtime.setCenterTitleColor(isSelected ? 0xff00ffc0 : 0xffffffff);
        mRVtime.setBottomTitleColor(isSelected ? 0xff00ffc0 : 0xffffffff);
        mRVtime.setIsForbidSlide(!isSelected);
        mRVtime.setIsOpenBacklightAnim(isSelected);
        mRVtime.setSecondCircleShadowColor(isSelected ? 0xff00ffc0 : 0x00000000);
    }

    private void setRVrpmSelected(boolean isSelected) {
        map.put(R.id.regulator_view_rpm, !isSelected);
        tvRpm.setTextColor(isSelected ? 0xff00ffc0 : 0xffffffff);
        mRVrpm.setCenterTitleColor(isSelected ? 0xff00ffc0 : 0xffffffff);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(isSelected ? R.drawable.home_biground_rotating : R.drawable.home_smallround_rotating);
        if (bitmapDrawable != null) {
            mRVrpm.setBottomBitmapImage(bitmapDrawable.getBitmap());
        }
        mRVrpm.setIsForbidSlide(!isSelected);
        mRVrpm.setIsOpenBacklightAnim(isSelected);
        mRVrpm.setSecondCircleShadowColor(isSelected ? 0xff00ffc0 : 0x00000000);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int id = view.getId();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                boolean tmp = map.get(id);
                if (map.get(id)) {
                    Animation animation = AnimationUtils.loadAnimation(RegulatorView2Activity.this, R.anim.normal_to_large);
                    view.startAnimation(animation);
                    map.put(id, false);
                    break;
                }
                break;
        }
        return false;
    }

}
