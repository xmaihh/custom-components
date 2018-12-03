package tp.custom_components.project.util;

import android.content.Context;

public class DensityUtil {
    public static float dptopx(Context context, float dpValue) {
        return context.getResources().getDisplayMetrics().density * dpValue + 0.5f;
    }

    public static float pxtodp(Context context, float pxValue) {
        return pxValue / context.getResources().getDisplayMetrics().density + 0.5f;
    }

    public static float sptopx(Context context, float spValue) {
        return context.getResources().getDisplayMetrics().scaledDensity * spValue + 0.5f;
    }

    public static float pxtosp(Context context, float pxValue) {
        return pxValue / context.getResources().getDisplayMetrics().scaledDensity + 0.5f;
    }

}
