package co.id.cpn.bdmgafi.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import java.util.ArrayList;
import java.util.List;

import co.id.cpn.bdmgafi.R;
import co.id.cpn.entity.Region;
import kotlin.jvm.internal.Intrinsics;

public final class RegionDropdown extends AppCompatAutoCompleteTextView {
    private AttributeSet attributeSet;
    private String dataSelectedId = "";
    private String dataSelectedName = "";
    private RegionDropdown instance;

    public RegionDropdown(Context context, AttributeSet attributeSet2) {
        super(context, attributeSet2);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final RegionDropdown getInstance() {
        if (this.instance == null) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            this.instance = new RegionDropdown(context, this.attributeSet);
        }
        return this.instance;
    }

    public void setError(CharSequence error) {
        Drawable icon = getResources().getDrawable(R.drawable.ic_important);
        if (icon != null) {
            icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        }
        super.setError(error, icon);
    }

    public final void setupData(List<Region> regions) {
        Intrinsics.checkNotNullParameter(regions, "regions");
        List label = new ArrayList();
        label.add("All Region");
        for (Region data : regions) {
            label.add(data.getRegionName());
        }
        setAdapter(new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, label));
        setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                RegionDropdown.m530setupData$lambda0(RegionDropdown.this, view);
            }
        });
// setOnItemClickListener(new AdapterView.OnItemClickListener(regions) {
//     public final /* synthetic * / List f$1;
//
//     {
//         this.f$1 = r2;
//     }
//
//     public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
//         RegionDropdown.m531setupData$lambda1(RegionDropdown.this, this.f$1, adapterView, view, i, j);
//     }
// });
    }

    /* access modifiers changed from: private */
    /* renamed from: setupData$lambda-0  reason: not valid java name */
    public static final void m530setupData$lambda0(RegionDropdown this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showDropDown();
    }

    /* access modifiers changed from: private */
    /* renamed from: setupData$lambda-1  reason: not valid java name */
    public static final void m531setupData$lambda1(RegionDropdown this$0, List $regions, AdapterView $noName_0, View $noName_1, int position, long $noName_3) {
        if (position != 0) {
            this$0.dataSelectedName = ((Region) $regions.get(position - 1)).getRegionName();
            this$0.dataSelectedId = ((Region) $regions.get(position - 1)).getRegionSID();
            return;
        }
        this$0.dataSelectedName = "ALL";
        this$0.dataSelectedId = "ALL";
    }

    public final String getSelectedID() {
        return String.valueOf(this.dataSelectedId);
    }

    public final String getSelectedName() {
        return String.valueOf(this.dataSelectedName);
    }
}
