package co.id.cpn.bdmgafi.ui.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;
import kotlin.jvm.internal.Intrinsics;

public final class CustomViewPager extends ViewPager {
    private boolean isPagingEnabled = true;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
    }

    public final boolean isPagingEnabled() {
        return this.isPagingEnabled;
    }

    public final void setPagingEnabled(boolean z) {
        this.isPagingEnabled = z;
    }

    public boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, NotificationCompat.CATEGORY_EVENT);
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, NotificationCompat.CATEGORY_EVENT);
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }
}
