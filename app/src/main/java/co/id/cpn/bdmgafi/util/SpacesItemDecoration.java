package co.id.cpn.bdmgafi.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private boolean includeEdge;
    private int spacing;
    private int spanCount;

    public SpacesItemDecoration(int spanCount2, int spacing2, boolean includeEdge2) {
        this.spanCount = spanCount2;
        this.spacing = spacing2;
        this.includeEdge = includeEdge2;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int i = this.spanCount;
        int column = position % i;
        if (this.includeEdge) {
            int i2 = this.spacing;
            outRect.left = i2 - ((column * i2) / i);
            outRect.right = ((column + 1) * this.spacing) / this.spanCount;
            if (position < this.spanCount) {
                outRect.top = this.spacing;
            }
            outRect.bottom = this.spacing;
            return;
        }
        outRect.left = (this.spacing * column) / i;
        int i3 = this.spacing;
        outRect.right = i3 - (((column + 1) * i3) / this.spanCount);
        if (position >= this.spanCount) {
            outRect.top = this.spacing;
        }
    }
}