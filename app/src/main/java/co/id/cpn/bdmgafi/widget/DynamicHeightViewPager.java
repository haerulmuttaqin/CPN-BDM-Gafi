package co.id.cpn.bdmgafi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.viewpager.widget.ViewPager;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class DynamicHeightViewPager extends ViewPager {
    public DynamicHeightViewPager(Context context) {
        this(context, (AttributeSet) null, 2, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DynamicHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DynamicHeightViewPager(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec2 = heightMeasureSpec;
        int height = 0;
        int childCount = getChildCount();
        if (childCount > 0) {
            int i = 0;
            do {
                int i2 = i;
                i++;
                View child = getChildAt(i2);
                child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > height) {
                    height = h;
                    continue;
                }
            } while (i < childCount);
        }
        if (height != 0) {
            heightMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        try {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
