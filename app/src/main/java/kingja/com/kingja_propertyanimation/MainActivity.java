package kingja.com.kingja_propertyanimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();
    private View target;
    private View goal;
    private int[] locationTarget;
    private int[] locationGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        target = findViewById(R.id.target);
        goal = findViewById(R.id.goal);
        findViewById(R.id.btn_translate).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_translate:
               target.animate().x(locationGoal[0]).y(locationGoal[1]-getStateHegiht()).setDuration(3000).start();

                break;
            default:
                break;
        }
    }

    private void argbAnimator() {

//        ValueAnimator argbAnimator = ValueAnimator.ofArgb(0xffff0000,0xff00ff00,0xff0000ff);
//        argbAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                target.setBackgroundColor((Integer) animation.getAnimatedValue());
//            }
//        });
//        argbAnimator.setDuration(3000);
//        argbAnimator.start();
    }

    private void doAlpha() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 0.5f)
                .setDuration(300);
        valueAnimator.setRepeatCount(5);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentAlpha = (float) animation.getAnimatedValue();
                target.setAlpha(currentAlpha);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e(TAG, "onAnimationCancel: ");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.e(TAG, "onAnimationRepeat: ");
            }
        });
        valueAnimator.start();

    }

    private void moveAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                int[] startArr = (int[]) startValue;
                int[] endArr = (int[]) endValue;
                int[] currentArr = new int[2];
                currentArr[0] = (int) (fraction * (endArr[0] - startArr[0]));
                currentArr[1] = (int) (fraction * (endArr[1] - startArr[1]));
                return currentArr;
            }
        }, locationTarget, locationGoal);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new OvershootInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int[] currentArr = (int[]) animation.getAnimatedValue();
                target.setX(currentArr[0]);
                target.setY(currentArr[1]);
            }
        });
        valueAnimator.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        locationTarget = new int[2];
        locationGoal = new int[2];
        target.getLocationInWindow(locationTarget);
        goal.getLocationInWindow(locationGoal);
        Log.e(TAG, "getLocationOnScreenX: " + locationTarget[0]);
        Log.e(TAG, "getLocationOnScreenY: " + locationTarget[1]);
        Log.e(TAG, "getLocationInWindowX: " + locationGoal[0]);
        Log.e(TAG, "getLocationInWindowY: " + locationGoal[1]);
        getStateHegiht();
    }

    public int getStateHegiht() {
        Rect rectangle= new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }
}
