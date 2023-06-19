package anu.edu.cecs.holistay.ui.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.google.android.gms.maps.MapView;

/**
 * Custom MapView to enable in map scroll events
 *
 * @Author: Srinivasa Sai Damarla (u7370240)
 */
public class MyMapViewHelper extends MapView {
    public MyMapViewHelper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP: {
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                this.getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
