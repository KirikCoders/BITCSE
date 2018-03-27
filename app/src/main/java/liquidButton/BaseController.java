package liquidButton;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Canvas;
import android.support.annotation.Keep;


import java.lang.ref.WeakReference;



/**
 * Created by ricogao on 12/05/2016.
 */
@Keep
public abstract class BaseController {
    //set WeakReference to avoid memory leak
    private WeakReference<LiquidButton> checkView;
    private Animator animator;

    int centerX;
    int centerY;
    int radius;

    public abstract void draw(Canvas canvas);

    abstract Animator buildAnimator();

    public abstract void reset();

    protected void setRender(float interpolatedTime) {
        getCheckView().invalidate();
    }

    BaseController() {
        animator = buildAnimator();
    }

    public void setCheckView(LiquidButton checkView) {
        this.checkView = new WeakReference<>(checkView);
    }

    LiquidButton getCheckView() {
        return checkView != null ? checkView.get() : null;
    }

    public Animator getAnimator() {
        return animator;
    }

    public boolean isRunning() {
        return getAnimator().isRunning();
    }

    public void getMeasure(int width, int height) {
        centerX = width / 2;
        centerY = height / 2;
        radius = (width*2) / 5;
    }


    Animator getBaseAnimator(long duration, TimeInterpolator interpolator) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "render", 0.0f, 1.0f);
        animator.setDuration(duration);
        animator.setInterpolator(interpolator);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                reset();
            }
        });

        return animator;
    }
}
