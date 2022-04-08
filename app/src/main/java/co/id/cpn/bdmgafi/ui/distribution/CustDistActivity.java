/*
package co.id.cpn.bdmgafi.ui.distribution;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import co.id.cpn.bdmgafi.ui.adapter.CustomViewPager;
import co.id.cpn.bdmgafi.ui.adapter.ViewPagerCustAdapter;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.koin.core.qualifier.Qualifier;
import co.id.cpn.bdmgafi.ui.base.BaseActivity;
import co.id.cpn.bdmgafi.databinding.ActivityCustDistBinding;
import co.id.cpn.bdmgafi.util.AppUtils;
import co.id.cpn.bdmgafi.widget.DistributionDropdown;
import co.id.cpn.bdmgafi.widget.RegionDropdown;
import co.id.cpn.domain.distribution.DistUseCase;
import co.id.cpn.entity.Asset;
import co.id.cpn.entity.CustomerItem;
import co.id.cpn.entity.Distribution;
import co.id.cpn.entity.util.Constants;
*/
/*
import co.id.cpn.bdmgafi.ui.adapter.InspectionCriteriaDropdownAdapter;
import co.id.cpn.bdmgafi.ui.adapter.VisitCriteriaDropdownAdapter;
import co.id.cpn.bdmgafi.ui.base.ViewPagerCustAdapter;
import co.id.cpn.bdmgafi.ui.distribution.fragment.AssetDistListAssetFragment;
import co.id.cpn.bdmgafi.ui.distribution.fragment.AssetDistListFragment;
import co.id.cpn.bdmgafi.ui.distribution.fragment.AssetDistMapFragment;
import co.id.cpn.bdmgafi.ui.distribution.fragment.CustDistListFragment;
import co.id.cpn.bdmgafi.ui.distribution.fragment.CustDistMapAssetFragment;
import co.id.cpn.bdmgafi.ui.distribution.fragment.CustDistMapFragment;
import co.id.cpn.bdmgafi.widget.CustomViewPager;
import co.id.cpn.bdmgafi.widget.CustomerTypeDropdown;
import co.id.cpn.bdmgafi.widget.InspectionCriteriaDropdown;
import co.id.cpn.bdmgafi.widget.ItemDropdown;
import co.id.cpn.bdmgafi.widget.VisitCriteriaDropdown;
import co.id.cpn.entity.FilterAsset;
import co.id.cpn.entity.FilterCustomer;*//*


public final class CustDistActivity extends BaseActivity {
    private ActivityCustDistBinding binding;
    private final Lazy viewModel$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, new CustDistActivity$special$$inlined$viewModel$default$1(this, (Qualifier) null, (Function0) null));
    private ViewPagerCustAdapter adapter;
    public boolean dialogEmpty;
    public String inspectionCriteriaFilter = "";
    public String inspectionCriteriaFilterName = "";
    private int tabPos;
    private int totalPage = 2;
    */
/* access modifiers changed from: private *//*

    public String visitCriteriaFilter = "";
    */
/* access modifiers changed from: private *//*

    public String visitCriteriaFilterName = "";

    public final int getTabPos() {
        return this.tabPos;
    }

    public final void setTabPos(int i) {
        this.tabPos = i;
    }

    public final int getTotalPage() {
        return this.totalPage;
    }

    public final void setTotalPage(int i) {
        this.totalPage = i;
    }

    */
/* access modifiers changed from: private *//*

    public final DistributionViewModel getViewModel() {
        return (DistributionViewModel) this.viewModel$delegate.getValue();
    }

    public void observeViewModel() {}

    */
/* access modifiers changed from: protected *//*

    public void initViewBinding() {}

    */
/* access modifiers changed from: protected *//*

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCustDistBinding inflate = ActivityCustDistBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(layoutInflater)");
        this.binding = inflate;
        if (inflate != null) {
            setContentView((View) inflate.getRoot());
            ActivityCustDistBinding activityCustDistBinding = this.binding;
            if (activityCustDistBinding != null) {
                setSupportActionBar(activityCustDistBinding.toolbar);
                ActionBar supportActionBar = getSupportActionBar();
                if (supportActionBar != null) {
                    supportActionBar.setDisplayHomeAsUpEnabled(true);
                }
                Bundle extras = getIntent().getExtras();
                Intrinsics.checkNotNull(extras);
                if (extras.getString("dist_type") != null) {
                    Bundle extras2 = getIntent().getExtras();
                    Intrinsics.checkNotNull(extras2);
                    if (StringsKt.equals$default(extras2.getString("dist_type"), "cust", false, 2, (Object) null)) {
                        setTitle("Customer Distribution");
                        Bundle extras3 = getIntent().getExtras();
                        Intrinsics.checkNotNull(extras3);
                        if (extras3.getString(Constants.DISTRIBUTION) != null) {
                            DistUseCase distUseCase = getViewModel().getDistUseCase();
                            Bundle extras4 = getIntent().getExtras();
                            Intrinsics.checkNotNull(extras4);
                            String string = extras4.getString(Constants.DISTRIBUTION);
                            Intrinsics.checkNotNull(string);
                            distUseCase.getDistributionBy(string).observe(this, new Observer() {
                                public final void onChanged(Object obj) {
                                    CustDistActivity.m556onCreate$lambda1(CustDistActivity.this, (Distribution) obj);
                                }
                            });
                        }
                        getViewModel().getDistribution().observe(this, new Observer() {
                            public final void onChanged(Object obj) {
                                CustDistActivity.m565onCreate$lambda4(CustDistActivity.this, (List) obj);
                            }
                        });
                        ActivityCustDistBinding activityCustDistBinding2 = this.binding;
                        if (activityCustDistBinding2 != null) {
                            activityCustDistBinding2.filterButton.setOnClickListener(new View.OnClickListener() {
                                public final void onClick(View view) {
                                    CustDistActivity.m568onCreate$lambda5(CustDistActivity.this, view);
                                }
                            });
                        } else {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            throw null;
                        }
                    } else {
                        setTitle("Asset Distribution");
                        Bundle extras5 = getIntent().getExtras();
                        Intrinsics.checkNotNull(extras5);
                        if (extras5.getString("asset_id") != null) {
                            Bundle extras6 = getIntent().getExtras();
                            Intrinsics.checkNotNull(extras6);
                            String assetNo = extras6.getString("asset_id");
                            DistributionViewModel viewModel = getViewModel();
                            Intrinsics.checkNotNull(assetNo);
                            viewModel.assetByAssetNo(assetNo).observe(this, new Observer() {
                                public final void onChanged(Object obj) {
                                    CustDistActivity.m569onCreate$lambda8(CustDistActivity.this, (Asset) obj);
                                }
                            });
                        } else {
                            Bundle extras7 = getIntent().getExtras();
                            Intrinsics.checkNotNull(extras7);
                            if (extras7.getString(Constants.DISTRIBUTION) != null) {
                                DistUseCase distUseCase2 = getViewModel().getDistUseCase();
                                Bundle extras8 = getIntent().getExtras();
                                Intrinsics.checkNotNull(extras8);
                                String string2 = extras8.getString(Constants.DISTRIBUTION);
                                Intrinsics.checkNotNull(string2);
                                distUseCase2.getDistributionBy(string2).observe(this, new Observer() {
                                    public final void onChanged(Object obj) {
                                        CustDistActivity.m558onCreate$lambda10(CustDistActivity.this, (Distribution) obj);
                                    }
                                });
                            }
                            getViewModel().getDistribution().observe(this, new Observer() {
                                public final void onChanged(Object obj) {
                                    CustDistActivity.m560onCreate$lambda13(CustDistActivity.this, (List) obj);
                                }
                            });
                        }
                        ActivityCustDistBinding activityCustDistBinding3 = this.binding;
                        if (activityCustDistBinding3 != null) {
                            activityCustDistBinding3.filterButton.setOnClickListener(new View.OnClickListener() {
                                public final void onClick(View view) {
                                    CustDistActivity.m563onCreate$lambda14(CustDistActivity.this, view);
                                }
                            });
                        } else {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            throw null;
                        }
                    }
                }
                ActivityCustDistBinding activityCustDistBinding4 = this.binding;
                if (activityCustDistBinding4 != null) {
                    activityCustDistBinding4.searchEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                            return CustDistActivity.m564onCreate$lambda15(CustDistActivity.this, textView, i, keyEvent);
                        }
                    });
                    AppUtils appUtils = AppUtils.INSTANCE;
                    ActivityCustDistBinding activityCustDistBinding5 = this.binding;
                    if (activityCustDistBinding5 != null) {
                        ImageView imageView = activityCustDistBinding5.searchButton;
                        Intrinsics.checkNotNullExpressionValue(imageView, "binding.searchButton");
                        View view = imageView;
                        ActivityCustDistBinding activityCustDistBinding6 = this.binding;
                        if (activityCustDistBinding6 != null) {
                            LinearLayout linearLayout = activityCustDistBinding6.searchLayout;
                            Intrinsics.checkNotNullExpressionValue(linearLayout, "binding.searchLayout");
                            ActivityCustDistBinding activityCustDistBinding7 = this.binding;
                            if (activityCustDistBinding7 != null) {
                                ImageView imageView2 = activityCustDistBinding7.searchButton;
                                Intrinsics.checkNotNullExpressionValue(imageView2, "binding.searchButton");
                                appUtils.setupExpanded(view, linearLayout, imageView2, C0550R.C0552drawable.ic_search_off, C0550R.C0552drawable.ic_search, "close");
                                return;
                            }
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            throw null;
                        }
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        throw null;
                    }
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    throw null;
                }
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("binding");
        throw null;
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-1  reason: not valid java name *//*

    public static final void m556onCreate$lambda1(CustDistActivity this$0, Distribution it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dialogEmpty = false;
        this$0.showProgress();
        new Handler().postDelayed(new Runnable(it) {
            public final */
/* synthetic *//*
 Distribution f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                CustDistActivity.m557onCreate$lambda1$lambda0(CustDistActivity.this, this.f$1);
            }
        }, 100);
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-1$lambda-0  reason: not valid java name *//*

    public static final void m557onCreate$lambda1$lambda0(CustDistActivity this$0, Distribution $it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCustDistBinding activityCustDistBinding = this$0.binding;
        if (activityCustDistBinding != null) {
            activityCustDistBinding.tabLayout.setVisibility(0);
            ActivityCustDistBinding activityCustDistBinding2 = this$0.binding;
            if (activityCustDistBinding2 != null) {
                activityCustDistBinding2.selectView.setVisibility(4);
                this$0.getViewModel().getDistSelected().postValue($it);
                ActivityCustDistBinding activityCustDistBinding3 = this$0.binding;
                if (activityCustDistBinding3 != null) {
                    activityCustDistBinding3.distributionDropdown.setText($it.getDistributionName(), false);
                    ActivityCustDistBinding activityCustDistBinding4 = this$0.binding;
                    if (activityCustDistBinding4 != null) {
                        CustomViewPager customViewPager = activityCustDistBinding4.viewPager;
                        Intrinsics.checkNotNullExpressionValue(customViewPager, "binding.viewPager");
                        this$0.setupViewPager(customViewPager, "cust");
                        return;
                    }
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    throw null;
                }
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("binding");
        throw null;
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-4  reason: not valid java name *//*

    public static final void m565onCreate$lambda4(CustDistActivity this$0, List dist) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCustDistBinding activityCustDistBinding = this$0.binding;
        if (activityCustDistBinding != null) {
            DistributionDropdown distributionDropdown = activityCustDistBinding.distributionDropdown;
            Intrinsics.checkNotNullExpressionValue(dist, "dist");
            distributionDropdown.setupData(dist);
            ActivityCustDistBinding activityCustDistBinding2 = this$0.binding;
            if (activityCustDistBinding2 != null) {
                activityCustDistBinding2.distributionDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener(dist) {
                    public final */
/* synthetic *//*
 List f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                        CustDistActivity.m566onCreate$lambda4$lambda3(CustDistActivity.this, this.f$1, adapterView, view, i, j);
                    }
                });
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-4$lambda-3  reason: not valid java name *//*

    public static final void m566onCreate$lambda4$lambda3(CustDistActivity this$0, List $dist, AdapterView $noName_0, View $noName_1, int position, long $noName_3) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dialogEmpty = false;
        this$0.showProgress();
        new Handler().postDelayed(new Runnable($dist, position) {
            public final */
/* synthetic *//*
 List f$1;
            public final */
/* synthetic *//*
 int f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                CustDistActivity.m567onCreate$lambda4$lambda3$lambda2(CustDistActivity.this, this.f$1, this.f$2);
            }
        }, 100);
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-4$lambda-3$lambda-2  reason: not valid java name *//*

    public static final void m567onCreate$lambda4$lambda3$lambda2(CustDistActivity this$0, List $dist, int $position) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCustDistBinding activityCustDistBinding = this$0.binding;
        if (activityCustDistBinding != null) {
            activityCustDistBinding.tabLayout.setVisibility(0);
            ActivityCustDistBinding activityCustDistBinding2 = this$0.binding;
            if (activityCustDistBinding2 != null) {
                activityCustDistBinding2.selectView.setVisibility(4);
                this$0.getViewModel().getDistSelected().postValue($dist.get($position));
                ActivityCustDistBinding activityCustDistBinding3 = this$0.binding;
                if (activityCustDistBinding3 != null) {
                    CustomViewPager customViewPager = activityCustDistBinding3.viewPager;
                    Intrinsics.checkNotNullExpressionValue(customViewPager, "binding.viewPager");
                    this$0.setupViewPager(customViewPager, "cust");
                    return;
                }
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("binding");
        throw null;
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-5  reason: not valid java name *//*

    public static final void m568onCreate$lambda5(CustDistActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showDialogFilter();
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-8  reason: not valid java name *//*

    public static final void m569onCreate$lambda8(CustDistActivity this$0, Asset asset) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getViewModel().getAssetSelected().postValue(asset);
        if (asset != null) {
            this$0.getViewModel().customerBy(asset.getCustomerSID()).observe(this$0, new Observer() {
                public final void onChanged(Object obj) {
                    CustDistActivity.m570onCreate$lambda8$lambda7(CustDistActivity.this, (CustomerItem) obj);
                }
            });
        } else {
            AppUtils.INSTANCE.confirmDialog(this$0, "Asset Not Found", "please make sure the QR Code is valid", C0550R.C0552drawable.ic_undraw_empty, "Cancel", "Yes", new CustDistActivity$onCreate$4$2(this$0));
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-8$lambda-7  reason: not valid java name *//*

    public static final void m570onCreate$lambda8$lambda7(CustDistActivity this$0, CustomerItem cust) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCustDistBinding activityCustDistBinding = this$0.binding;
        if (activityCustDistBinding != null) {
            activityCustDistBinding.tabLayout.setVisibility(0);
            ActivityCustDistBinding activityCustDistBinding2 = this$0.binding;
            if (activityCustDistBinding2 != null) {
                activityCustDistBinding2.selectView.setVisibility(4);
                this$0.getViewModel().getCustomerSelected().postValue(cust);
                this$0.getViewModel().getDistUseCase().getDistributionBy(cust.getDistributionSID()).observe(this$0, new Observer() {
                    public final void onChanged(Object obj) {
                        CustDistActivity.m571onCreate$lambda8$lambda7$lambda6(CustDistActivity.this, (Distribution) obj);
                    }
                });
                return;
            }
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("binding");
        throw null;
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-8$lambda-7$lambda-6  reason: not valid java name *//*

    public static final void m571onCreate$lambda8$lambda7$lambda6(CustDistActivity this$0, Distribution it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getViewModel().getDistSelected().postValue(it);
        ActivityCustDistBinding activityCustDistBinding = this$0.binding;
        if (activityCustDistBinding != null) {
            CustomViewPager customViewPager = activityCustDistBinding.viewPager;
            Intrinsics.checkNotNullExpressionValue(customViewPager, "binding.viewPager");
            this$0.setupViewPagerAsset(customViewPager, Constants.ASSET);
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("binding");
        throw null;
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-10  reason: not valid java name *//*

    public static final void m558onCreate$lambda10(CustDistActivity this$0, Distribution it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dialogEmpty = false;
        this$0.showProgress();
        new Handler().postDelayed(new Runnable(it) {
            public final */
/* synthetic *//*
 Distribution f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                CustDistActivity.m559onCreate$lambda10$lambda9(CustDistActivity.this, this.f$1);
            }
        }, 100);
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-10$lambda-9  reason: not valid java name *//*

    public static final void m559onCreate$lambda10$lambda9(CustDistActivity this$0, Distribution $it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCustDistBinding activityCustDistBinding = this$0.binding;
        if (activityCustDistBinding != null) {
            activityCustDistBinding.tabLayout.setVisibility(0);
            ActivityCustDistBinding activityCustDistBinding2 = this$0.binding;
            if (activityCustDistBinding2 != null) {
                activityCustDistBinding2.selectView.setVisibility(4);
                this$0.getViewModel().getDistSelected().postValue($it);
                ActivityCustDistBinding activityCustDistBinding3 = this$0.binding;
                if (activityCustDistBinding3 != null) {
                    activityCustDistBinding3.distributionDropdown.setText($it.getDistributionName(), false);
                    ActivityCustDistBinding activityCustDistBinding4 = this$0.binding;
                    if (activityCustDistBinding4 != null) {
                        CustomViewPager customViewPager = activityCustDistBinding4.viewPager;
                        Intrinsics.checkNotNullExpressionValue(customViewPager, "binding.viewPager");
                        this$0.setupViewPager(customViewPager, Constants.ASSET);
                        return;
                    }
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    throw null;
                }
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("binding");
        throw null;
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-13  reason: not valid java name *//*

    public static final void m560onCreate$lambda13(CustDistActivity this$0, List dist) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCustDistBinding activityCustDistBinding = this$0.binding;
        if (activityCustDistBinding != null) {
            DistributionDropdown distributionDropdown = activityCustDistBinding.distributionDropdown;
            Intrinsics.checkNotNullExpressionValue(dist, "dist");
            distributionDropdown.setupData(dist);
            ActivityCustDistBinding activityCustDistBinding2 = this$0.binding;
            if (activityCustDistBinding2 != null) {
                activityCustDistBinding2.distributionDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener(dist) {
                    public final */
/* synthetic *//*
 List f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                        CustDistActivity.m561onCreate$lambda13$lambda12(CustDistActivity.this, this.f$1, adapterView, view, i, j);
                    }
                });
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-13$lambda-12  reason: not valid java name *//*

    public static final void m561onCreate$lambda13$lambda12(CustDistActivity this$0, List $dist, AdapterView $noName_0, View $noName_1, int position, long $noName_3) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showProgress();
        this$0.dialogEmpty = false;
        new Handler().postDelayed(new Runnable($dist, position) {
            public final */
/* synthetic *//*
 List f$1;
            public final */
/* synthetic *//*
 int f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                CustDistActivity.m562onCreate$lambda13$lambda12$lambda11(CustDistActivity.this, this.f$1, this.f$2);
            }
        }, 100);
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-13$lambda-12$lambda-11  reason: not valid java name *//*

    public static final void m562onCreate$lambda13$lambda12$lambda11(CustDistActivity this$0, List $dist, int $position) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCustDistBinding activityCustDistBinding = this$0.binding;
        if (activityCustDistBinding != null) {
            activityCustDistBinding.tabLayout.setVisibility(0);
            ActivityCustDistBinding activityCustDistBinding2 = this$0.binding;
            if (activityCustDistBinding2 != null) {
                activityCustDistBinding2.selectView.setVisibility(4);
                this$0.getViewModel().getDistSelected().postValue($dist.get($position));
                ActivityCustDistBinding activityCustDistBinding3 = this$0.binding;
                if (activityCustDistBinding3 != null) {
                    CustomViewPager customViewPager = activityCustDistBinding3.viewPager;
                    Intrinsics.checkNotNullExpressionValue(customViewPager, "binding.viewPager");
                    this$0.setupViewPager(customViewPager, Constants.ASSET);
                    return;
                }
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("binding");
        throw null;
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-14  reason: not valid java name *//*

    public static final void m563onCreate$lambda14(CustDistActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showDialogFilterAsset();
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: onCreate$lambda-15  reason: not valid java name *//*

    public static final boolean m564onCreate$lambda15(CustDistActivity this$0, TextView v, int actionId, KeyEvent event) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (actionId != 3) {
            return false;
        }
        DistributionViewModel viewModel = this$0.getViewModel();
        ActivityCustDistBinding activityCustDistBinding = this$0.binding;
        if (activityCustDistBinding != null) {
            viewModel.submitFilter(String.valueOf(activityCustDistBinding.searchEdittext.getText()));
            this$0.hideKeyboard();
            return true;
        }
        Intrinsics.throwUninitializedPropertyAccessException("binding");
        throw null;
    }

    public final void setupViewPager(ViewPager viewPager, String type) {
        Intrinsics.checkNotNullParameter(viewPager, "viewPager");
        Intrinsics.checkNotNullParameter(type, "type");
        new Handler().postDelayed(new Runnable(type, viewPager) {
            public final */
/* synthetic *//*
 String f$1;
            public final */
/* synthetic *//*
 ViewPager f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                CustDistActivity.m572setupViewPager$lambda19(CustDistActivity.this, this.f$1, this.f$2);
            }
        }, 500);
        ActivityCustDistBinding activityCustDistBinding = this.binding;
        if (activityCustDistBinding != null) {
            activityCustDistBinding.tabLayout.setOnTabSelectedListener((TabLayout.OnTabSelectedListener) new CustDistActivity$setupViewPager$2(this));
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: setupViewPager$lambda-19  reason: not valid java name *//*

    public static final void m572setupViewPager$lambda19(CustDistActivity this$0, String $type, ViewPager $viewPager) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter($type, "$type");
        Intrinsics.checkNotNullParameter($viewPager, "$viewPager");
        this$0.adapter = new ViewPagerCustAdapter(this$0.getSupportFragmentManager());
        if (Intrinsics.areEqual((Object) $type, (Object) "cust")) {
            ActivityCustDistBinding activityCustDistBinding = this$0.binding;
            if (activityCustDistBinding != null) {
                activityCustDistBinding.searchEdittext.setHint("Search Customer");
                ViewPagerCustAdapter viewPagerCustAdapter = this$0.adapter;
                if (viewPagerCustAdapter != null) {
                    CustDistMapFragment.Companion companion = CustDistMapFragment.Companion;
                    ActivityCustDistBinding activityCustDistBinding2 = this$0.binding;
                    if (activityCustDistBinding2 != null) {
                        viewPagerCustAdapter.addFragment(companion.newInstance(activityCustDistBinding2.distributionDropdown.getSelectedID(), ""), "MAP");
                        ViewPagerCustAdapter viewPagerCustAdapter2 = this$0.adapter;
                        if (viewPagerCustAdapter2 != null) {
                            CustDistListFragment.Companion companion2 = CustDistListFragment.Companion;
                            ActivityCustDistBinding activityCustDistBinding3 = this$0.binding;
                            if (activityCustDistBinding3 != null) {
                                viewPagerCustAdapter2.addFragment(companion2.newInstance(activityCustDistBinding3.distributionDropdown.getSelectedID(), ""), "CUSTOMER");
                            } else {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                throw null;
                            }
                        } else {
                            Intrinsics.throwUninitializedPropertyAccessException("adapter");
                            throw null;
                        }
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        throw null;
                    }
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    throw null;
                }
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
        } else {
            ActivityCustDistBinding activityCustDistBinding4 = this$0.binding;
            if (activityCustDistBinding4 != null) {
                activityCustDistBinding4.searchEdittext.setHint("Search Asset");
                ViewPagerCustAdapter viewPagerCustAdapter3 = this$0.adapter;
                if (viewPagerCustAdapter3 != null) {
                    AssetDistMapFragment.Companion companion3 = AssetDistMapFragment.Companion;
                    ActivityCustDistBinding activityCustDistBinding5 = this$0.binding;
                    if (activityCustDistBinding5 != null) {
                        viewPagerCustAdapter3.addFragment(companion3.newInstance(activityCustDistBinding5.distributionDropdown.getSelectedID(), ""), "MAP");
                        ViewPagerCustAdapter viewPagerCustAdapter4 = this$0.adapter;
                        if (viewPagerCustAdapter4 != null) {
                            AssetDistListFragment.Companion companion4 = AssetDistListFragment.Companion;
                            ActivityCustDistBinding activityCustDistBinding6 = this$0.binding;
                            if (activityCustDistBinding6 != null) {
                                viewPagerCustAdapter4.addFragment(companion4.newInstance(activityCustDistBinding6.distributionDropdown.getSelectedID(), ""), "ASSET");
                            } else {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                throw null;
                            }
                        } else {
                            Intrinsics.throwUninitializedPropertyAccessException("adapter");
                            throw null;
                        }
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        throw null;
                    }
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    throw null;
                }
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
        }
        this$0.setTotalPage(2);
        ViewPagerCustAdapter viewPagerCustAdapter5 = this$0.adapter;
        if (viewPagerCustAdapter5 != null) {
            $viewPager.setAdapter(viewPagerCustAdapter5);
            ActivityCustDistBinding activityCustDistBinding7 = this$0.binding;
            if (activityCustDistBinding7 != null) {
                CustomViewPager customViewPager = activityCustDistBinding7.viewPager;
                ActivityCustDistBinding activityCustDistBinding8 = this$0.binding;
                if (activityCustDistBinding8 != null) {
                    customViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(activityCustDistBinding8.tabLayout));
                    ActivityCustDistBinding activityCustDistBinding9 = this$0.binding;
                    if (activityCustDistBinding9 != null) {
                        activityCustDistBinding9.viewPager.setCurrentItem(0);
                        ActivityCustDistBinding activityCustDistBinding10 = this$0.binding;
                        if (activityCustDistBinding10 != null) {
                            activityCustDistBinding10.viewPager.setPageMargin(20);
                            ActivityCustDistBinding activityCustDistBinding11 = this$0.binding;
                            if (activityCustDistBinding11 != null) {
                                activityCustDistBinding11.viewPager.setOffscreenPageLimit(this$0.getTotalPage());
                                ActivityCustDistBinding activityCustDistBinding12 = this$0.binding;
                                if (activityCustDistBinding12 != null) {
                                    TabLayout tabLayout = activityCustDistBinding12.tabLayout;
                                    ActivityCustDistBinding activityCustDistBinding13 = this$0.binding;
                                    if (activityCustDistBinding13 != null) {
                                        tabLayout.setupWithViewPager(activityCustDistBinding13.viewPager);
                                        ActivityCustDistBinding activityCustDistBinding14 = this$0.binding;
                                        if (activityCustDistBinding14 != null) {
                                            activityCustDistBinding14.tabLayout.setTabTextColors(Color.parseColor("#0971ba"), Color.parseColor("#ffffff"));
                                            ActivityCustDistBinding activityCustDistBinding15 = this$0.binding;
                                            if (activityCustDistBinding15 != null) {
                                                activityCustDistBinding15.tabLayout.setSelectedTabIndicatorHeight(0);
                                                ActivityCustDistBinding activityCustDistBinding16 = this$0.binding;
                                                if (activityCustDistBinding16 != null) {
                                                    activityCustDistBinding16.tabLayout.setTabRippleColor((ColorStateList) null);
                                                    ActivityCustDistBinding activityCustDistBinding17 = this$0.binding;
                                                    if (activityCustDistBinding17 != null) {
                                                        activityCustDistBinding17.viewPager.setPagingEnabled(false);
                                                        ViewPagerCustAdapter viewPagerCustAdapter6 = this$0.adapter;
                                                        if (viewPagerCustAdapter6 != null) {
                                                            viewPagerCustAdapter6.notifyDataSetChanged();
                                                            this$0.getDataList();
                                                            this$0.getViewModel().getFilter().observe(this$0, new Observer() {
                                                                public final void onChanged(Object obj) {
                                                                    CustDistActivity.m573setupViewPager$lambda19$lambda16(CustDistActivity.this, (String) obj);
                                                                }
                                                            });
                                                            this$0.getViewModel().getFilterCustomer().observe(this$0, new Observer() {
                                                                public final void onChanged(Object obj) {
                                                                    CustDistActivity.m574setupViewPager$lambda19$lambda17(CustDistActivity.this, (FilterCustomer) obj);
                                                                }
                                                            });
                                                            this$0.getViewModel().getFilterAsset().observe(this$0, new Observer() {
                                                                public final void onChanged(Object obj) {
                                                                    CustDistActivity.m575setupViewPager$lambda19$lambda18(CustDistActivity.this, (FilterAsset) obj);
                                                                }
                                                            });
                                                            this$0.hideProgress();
                                                            return;
                                                        }
                                                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                                                        throw null;
                                                    }
                                                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                                                    throw null;
                                                }
                                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                                throw null;
                                            }
                                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                                            throw null;
                                        }
                                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                                        throw null;
                                    }
                                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                                    throw null;
                                }
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                throw null;
                            }
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            throw null;
                        }
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        throw null;
                    }
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    throw null;
                }
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("adapter");
        throw null;
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: setupViewPager$lambda-19$lambda-16  reason: not valid java name *//*

    public static final void m573setupViewPager$lambda19$lambda16(CustDistActivity this$0, String it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullExpressionValue(it, "it");
        if (it.length() > 0) {
            ActivityCustDistBinding activityCustDistBinding = this$0.binding;
            if (activityCustDistBinding != null) {
                activityCustDistBinding.searchButton.setColorFilter(Color.parseColor("#2194E6"));
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
        } else {
            ActivityCustDistBinding activityCustDistBinding2 = this$0.binding;
            if (activityCustDistBinding2 != null) {
                activityCustDistBinding2.searchButton.setColorFilter(Color.parseColor("#707070"));
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
        }
        this$0.getDataList();
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: setupViewPager$lambda-19$lambda-17  reason: not valid java name *//*

    public static final void m574setupViewPager$lambda19$lambda17(CustDistActivity this$0, FilterCustomer it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.w("FILTER", Intrinsics.stringPlus("setupViewPager: ", it));
        CharSequence visitCriteria = it.getVisitCriteria();
        boolean z = true;
        if (!(visitCriteria == null || visitCriteria.length() == 0)) {
            ActivityCustDistBinding activityCustDistBinding = this$0.binding;
            if (activityCustDistBinding != null) {
                activityCustDistBinding.filterButton.setColorFilter(Color.parseColor("#2194E6"));
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
        } else {
            CharSequence region = it.getRegion();
            if ((region == null || region.length() == 0) || StringsKt.equals$default(it.getRegion(), "ALL", false, 2, (Object) null)) {
                CharSequence customerType = it.getCustomerType();
                if (!(customerType == null || customerType.length() == 0)) {
                    z = false;
                }
                if (z || StringsKt.equals$default(it.getCustomerType(), "ALL", false, 2, (Object) null)) {
                    ActivityCustDistBinding activityCustDistBinding2 = this$0.binding;
                    if (activityCustDistBinding2 != null) {
                        activityCustDistBinding2.filterButton.setColorFilter(Color.parseColor("#707070"));
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        throw null;
                    }
                } else {
                    ActivityCustDistBinding activityCustDistBinding3 = this$0.binding;
                    if (activityCustDistBinding3 != null) {
                        activityCustDistBinding3.filterButton.setColorFilter(Color.parseColor("#2194E6"));
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        throw null;
                    }
                }
            } else {
                ActivityCustDistBinding activityCustDistBinding4 = this$0.binding;
                if (activityCustDistBinding4 != null) {
                    activityCustDistBinding4.filterButton.setColorFilter(Color.parseColor("#2194E6"));
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    throw null;
                }
            }
        }
        this$0.getDataList();
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: setupViewPager$lambda-19$lambda-18  reason: not valid java name *//*

    public static final void m575setupViewPager$lambda19$lambda18(CustDistActivity this$0, FilterAsset it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.w("FILTER", Intrinsics.stringPlus("setupViewPager: ", it));
        CharSequence inspection = it.getInspection();
        boolean z = true;
        if (!(inspection == null || inspection.length() == 0)) {
            ActivityCustDistBinding activityCustDistBinding = this$0.binding;
            if (activityCustDistBinding != null) {
                activityCustDistBinding.filterButton.setColorFilter(Color.parseColor("#2194E6"));
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
        } else {
            CharSequence region = it.getRegion();
            if ((region == null || region.length() == 0) || StringsKt.equals$default(it.getRegion(), "ALL", false, 2, (Object) null)) {
                CharSequence customerType = it.getCustomerType();
                if ((customerType == null || customerType.length() == 0) || StringsKt.equals$default(it.getCustomerType(), "ALL", false, 2, (Object) null)) {
                    CharSequence merk = it.getMerk();
                    if ((merk == null || merk.length() == 0) || StringsKt.equals$default(it.getMerk(), "ALL", false, 2, (Object) null)) {
                        CharSequence assetType = it.getAssetType();
                        if ((assetType == null || assetType.length() == 0) || StringsKt.equals$default(it.getAssetType(), "ALL", false, 2, (Object) null)) {
                            CharSequence ownership = it.getOwnership();
                            if (!(ownership == null || ownership.length() == 0)) {
                                z = false;
                            }
                            if (z || StringsKt.equals$default(it.getOwnership(), "ALL", false, 2, (Object) null)) {
                                ActivityCustDistBinding activityCustDistBinding2 = this$0.binding;
                                if (activityCustDistBinding2 != null) {
                                    activityCustDistBinding2.filterButton.setColorFilter(Color.parseColor("#707070"));
                                } else {
                                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                                    throw null;
                                }
                            } else {
                                ActivityCustDistBinding activityCustDistBinding3 = this$0.binding;
                                if (activityCustDistBinding3 != null) {
                                    activityCustDistBinding3.filterButton.setColorFilter(Color.parseColor("#2194E6"));
                                } else {
                                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                                    throw null;
                                }
                            }
                        } else {
                            ActivityCustDistBinding activityCustDistBinding4 = this$0.binding;
                            if (activityCustDistBinding4 != null) {
                                activityCustDistBinding4.filterButton.setColorFilter(Color.parseColor("#2194E6"));
                            } else {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                throw null;
                            }
                        }
                    } else {
                        ActivityCustDistBinding activityCustDistBinding5 = this$0.binding;
                        if (activityCustDistBinding5 != null) {
                            activityCustDistBinding5.filterButton.setColorFilter(Color.parseColor("#2194E6"));
                        } else {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            throw null;
                        }
                    }
                } else {
                    ActivityCustDistBinding activityCustDistBinding6 = this$0.binding;
                    if (activityCustDistBinding6 != null) {
                        activityCustDistBinding6.filterButton.setColorFilter(Color.parseColor("#2194E6"));
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        throw null;
                    }
                }
            } else {
                ActivityCustDistBinding activityCustDistBinding7 = this$0.binding;
                if (activityCustDistBinding7 != null) {
                    activityCustDistBinding7.filterButton.setColorFilter(Color.parseColor("#2194E6"));
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    throw null;
                }
            }
        }
        this$0.getDataList();
    }

    public final void getDataList() {
        getViewModel().getDistSelected().observe(this, new Observer() {
            public final void onChanged(Object obj) {
                CustDistActivity.m535getDataList$lambda22(CustDistActivity.this, (Distribution) obj);
            }
        });
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: getDataList$lambda-22  reason: not valid java name *//*

    public static final void m535getDataList$lambda22(CustDistActivity this$0, Distribution dist) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Bundle extras = this$0.getIntent().getExtras();
        Intrinsics.checkNotNull(extras);
        if (StringsKt.equals$default(extras.getString("dist_type"), "cust", false, 2, (Object) null)) {
            this$0.getViewModel().customerSize(dist.getDistributionSID()).observe(this$0, new Observer() {
                public final void onChanged(Object obj) {
                    CustDistActivity.m536getDataList$lambda22$lambda20(CustDistActivity.this, (List) obj);
                }
            });
        } else {
            this$0.getViewModel().assetViaQueryBy(dist.getDistributionSID()).observe(this$0, new Observer() {
                public final void onChanged(Object obj) {
                    CustDistActivity.m537getDataList$lambda22$lambda21(CustDistActivity.this, (List) obj);
                }
            });
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: getDataList$lambda-22$lambda-20  reason: not valid java name *//*

    public static final void m536getDataList$lambda22$lambda20(CustDistActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCustDistBinding activityCustDistBinding = this$0.binding;
        if (activityCustDistBinding != null) {
            TabLayout.Tab tabAt = activityCustDistBinding.tabLayout.getTabAt(1);
            if (tabAt != null) {
                tabAt.setText((CharSequence) "CUSTOMER (" + it.size() + ')');
            }
            if (it.isEmpty()) {
                this$0.showDialogEmptyData();
                return;
            }
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("binding");
        throw null;
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: getDataList$lambda-22$lambda-21  reason: not valid java name *//*

    public static final void m537getDataList$lambda22$lambda21(CustDistActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCustDistBinding activityCustDistBinding = this$0.binding;
        if (activityCustDistBinding != null) {
            TabLayout.Tab tabAt = activityCustDistBinding.tabLayout.getTabAt(1);
            if (tabAt != null) {
                tabAt.setText((CharSequence) "ASSET (" + it.size() + ')');
            }
            if (it.isEmpty()) {
                this$0.showDialogEmptyDataAsset();
                return;
            }
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("binding");
        throw null;
    }

    public final void setupViewPagerAsset(ViewPager viewPager, String type) {
        Intrinsics.checkNotNullParameter(viewPager, "viewPager");
        Intrinsics.checkNotNullParameter(type, "type");
        new Handler().postDelayed(new Runnable(viewPager) {
            public final */
/* synthetic *//*
 ViewPager f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                CustDistActivity.m576setupViewPagerAsset$lambda23(CustDistActivity.this, this.f$1);
            }
        }, 500);
        ActivityCustDistBinding activityCustDistBinding = this.binding;
        if (activityCustDistBinding != null) {
            activityCustDistBinding.tabLayout.setOnTabSelectedListener((TabLayout.OnTabSelectedListener) new CustDistActivity$setupViewPagerAsset$2(this));
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: setupViewPagerAsset$lambda-23  reason: not valid java name *//*

    public static final void m576setupViewPagerAsset$lambda23(CustDistActivity this$0, ViewPager $viewPager) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter($viewPager, "$viewPager");
        ViewPagerCustAdapter viewPagerCustAdapter = new ViewPagerCustAdapter(this$0.getSupportFragmentManager());
        this$0.adapter = viewPagerCustAdapter;
        if (viewPagerCustAdapter != null) {
            CustDistMapAssetFragment.Companion companion = CustDistMapAssetFragment.Companion;
            ActivityCustDistBinding activityCustDistBinding = this$0.binding;
            if (activityCustDistBinding != null) {
                viewPagerCustAdapter.addFragment(companion.newInstance(activityCustDistBinding.distributionDropdown.getSelectedID(), ""), "MAP");
                ViewPagerCustAdapter viewPagerCustAdapter2 = this$0.adapter;
                if (viewPagerCustAdapter2 != null) {
                    AssetDistListAssetFragment.Companion companion2 = AssetDistListAssetFragment.Companion;
                    ActivityCustDistBinding activityCustDistBinding2 = this$0.binding;
                    if (activityCustDistBinding2 != null) {
                        viewPagerCustAdapter2.addFragment(companion2.newInstance(activityCustDistBinding2.distributionDropdown.getSelectedID(), ""), "ASSET");
                        this$0.setTotalPage(2);
                        ViewPagerCustAdapter viewPagerCustAdapter3 = this$0.adapter;
                        if (viewPagerCustAdapter3 != null) {
                            $viewPager.setAdapter(viewPagerCustAdapter3);
                            ActivityCustDistBinding activityCustDistBinding3 = this$0.binding;
                            if (activityCustDistBinding3 != null) {
                                CustomViewPager customViewPager = activityCustDistBinding3.viewPager;
                                ActivityCustDistBinding activityCustDistBinding4 = this$0.binding;
                                if (activityCustDistBinding4 != null) {
                                    customViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(activityCustDistBinding4.tabLayout));
                                    ActivityCustDistBinding activityCustDistBinding5 = this$0.binding;
                                    if (activityCustDistBinding5 != null) {
                                        activityCustDistBinding5.viewPager.setCurrentItem(0);
                                        ActivityCustDistBinding activityCustDistBinding6 = this$0.binding;
                                        if (activityCustDistBinding6 != null) {
                                            activityCustDistBinding6.viewPager.setPageMargin(20);
                                            ActivityCustDistBinding activityCustDistBinding7 = this$0.binding;
                                            if (activityCustDistBinding7 != null) {
                                                activityCustDistBinding7.viewPager.setOffscreenPageLimit(this$0.getTotalPage());
                                                ActivityCustDistBinding activityCustDistBinding8 = this$0.binding;
                                                if (activityCustDistBinding8 != null) {
                                                    TabLayout tabLayout = activityCustDistBinding8.tabLayout;
                                                    ActivityCustDistBinding activityCustDistBinding9 = this$0.binding;
                                                    if (activityCustDistBinding9 != null) {
                                                        tabLayout.setupWithViewPager(activityCustDistBinding9.viewPager);
                                                        ActivityCustDistBinding activityCustDistBinding10 = this$0.binding;
                                                        if (activityCustDistBinding10 != null) {
                                                            activityCustDistBinding10.tabLayout.setTabTextColors(Color.parseColor("#0971ba"), Color.parseColor("#ffffff"));
                                                            ActivityCustDistBinding activityCustDistBinding11 = this$0.binding;
                                                            if (activityCustDistBinding11 != null) {
                                                                activityCustDistBinding11.tabLayout.setSelectedTabIndicatorHeight(0);
                                                                ActivityCustDistBinding activityCustDistBinding12 = this$0.binding;
                                                                if (activityCustDistBinding12 != null) {
                                                                    activityCustDistBinding12.tabLayout.setTabRippleColor((ColorStateList) null);
                                                                    ActivityCustDistBinding activityCustDistBinding13 = this$0.binding;
                                                                    if (activityCustDistBinding13 != null) {
                                                                        activityCustDistBinding13.viewPager.setPagingEnabled(false);
                                                                        ViewPagerCustAdapter viewPagerCustAdapter4 = this$0.adapter;
                                                                        if (viewPagerCustAdapter4 != null) {
                                                                            viewPagerCustAdapter4.notifyDataSetChanged();
                                                                            this$0.hideProgress();
                                                                            return;
                                                                        }
                                                                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                                                                        throw null;
                                                                    }
                                                                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                                                                    throw null;
                                                                }
                                                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                                                throw null;
                                                            }
                                                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                                                            throw null;
                                                        }
                                                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                                                        throw null;
                                                    }
                                                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                                                    throw null;
                                                }
                                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                                throw null;
                                            }
                                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                                            throw null;
                                        }
                                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                                        throw null;
                                    }
                                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                                    throw null;
                                }
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                throw null;
                            }
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            throw null;
                        }
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                        throw null;
                    }
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    throw null;
                }
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                throw null;
            }
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("adapter");
        throw null;
    }

    */
/* access modifiers changed from: private *//*

    public final void hiddenKeyboard(View v) {
        Object systemService = getSystemService("input_method");
        if (systemService != null) {
            ((InputMethodManager) systemService).hideSoftInputFromWindow(v.getWindowToken(), 0);
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }

    public final void showDialogFilter() {
        hideKeyboard();
        Object systemService = getSystemService("layout_inflater");
        if (systemService != null) {
            LayoutInflater li = (LayoutInflater) systemService;
            View inflate = li.inflate(C0550R.layout.dialog_filter_cutomer, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(inflate, "li.inflate(co.id.cpn.bdmgafi.R.layout.dialog_filter_cutomer, null)");
            View view = inflate;
            RegionDropdown region = (RegionDropdown) view.findViewById(C0550R.C0553id.region_dropdown);
            Ref.ObjectRef visitCriteria = new Ref.ObjectRef();
            visitCriteria.element = view.findViewById(C0550R.C0553id.visit_criteria);
            BottomSheetDialog dialog = new BottomSheetDialog(this, C0550R.C0557style.AppBottomSheetDialogTheme);
            dialog.setOnShowListener($$Lambda$CustDistActivity$GUX39pxSbcby5QkXAFWeRomlXkI.INSTANCE);
            $$Lambda$CustDistActivity$lA8UbWeRqZ2oZ3aK60y9vuouVA r7 = r0;
            Button button = (Button) view.findViewById(C0550R.C0553id.apply_filter);
            LayoutInflater layoutInflater = li;
            RegionDropdown regionDropdown = region;
            RegionDropdown regionDropdown2 = region;
            MutableLiveData<Distribution> distSelected = getViewModel().getDistSelected();
            $$Lambda$CustDistActivity$lA8UbWeRqZ2oZ3aK60y9vuouVA r0 = new Observer(visitCriteria, button, regionDropdown, (CustomerTypeDropdown) view.findViewById(C0550R.C0553id.customer_type), dialog) {
                public final */
/* synthetic *//*
 Ref.ObjectRef f$1;
                public final */
/* synthetic *//*
 Button f$2;
                public final */
/* synthetic *//*
 RegionDropdown f$3;
                public final */
/* synthetic *//*
 CustomerTypeDropdown f$4;
                public final */
/* synthetic *//*
 BottomSheetDialog f$5;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                    this.f$4 = r5;
                    this.f$5 = r6;
                }

                public final void onChanged(Object obj) {
                    CustDistActivity.m581showDialogFilter$lambda29(CustDistActivity.this, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, (Distribution) obj);
                }
            };
            distSelected.observe(this, r7);
            ((ImageView) view.findViewById(C0550R.C0553id.close_button)).setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    CustDistActivity.m586showDialogFilter$lambda30(BottomSheetDialog.this, view);
                }
            });
            ((Button) view.findViewById(C0550R.C0553id.cancel_button)).setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    CustDistActivity.m587showDialogFilter$lambda31(BottomSheetDialog.this, view);
                }
            });
            dialog.setCancelable(false);
            dialog.setContentView(view);
            dialog.show();
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.LayoutInflater");
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilter$lambda-24  reason: not valid java name *//*

    public static final void m580showDialogFilter$lambda24(DialogInterface dialog) {
        if (dialog != null) {
            View findViewById = ((BottomSheetDialog) dialog).findViewById(C0550R.C0553id.design_bottom_sheet);
            if (findViewById != null) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((FrameLayout) findViewById);
                Intrinsics.checkNotNullExpressionValue(bottomSheetBehavior, "from(bottomSheet)");
                bottomSheetBehavior.setState(3);
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout");
        }
        throw new NullPointerException("null cannot be cast to non-null type com.google.android.material.bottomsheet.BottomSheetDialog");
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilter$lambda-29  reason: not valid java name *//*

    public static final void m581showDialogFilter$lambda29(CustDistActivity this$0, Ref.ObjectRef $visitCriteria, Button $applyFilter, RegionDropdown $region, CustomerTypeDropdown $customerType, BottomSheetDialog $dialog, Distribution dist) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter($visitCriteria, "$visitCriteria");
        Intrinsics.checkNotNullParameter($dialog, "$dialog");
        if (dist != null) {
            DistributionViewModel viewModel = this$0.getViewModel();
            Distribution value = this$0.getViewModel().getDistSelected().getValue();
            Intrinsics.checkNotNull(value);
            viewModel.regions(value.getDistributionSID()).observe(this$0, new Observer(this$0) {
                public final */
/* synthetic *//*
 CustDistActivity f$1;

                {
                    this.f$1 = r2;
                }

                public final void onChanged(Object obj) {
                    CustDistActivity.m582showDialogFilter$lambda29$lambda25(RegionDropdown.this, this.f$1, (List) obj);
                }
            });
            this$0.getViewModel().customerTypes().observe(this$0, new Observer(this$0) {
                public final */
/* synthetic *//*
 CustDistActivity f$1;

                {
                    this.f$1 = r2;
                }

                public final void onChanged(Object obj) {
                    CustDistActivity.m583showDialogFilter$lambda29$lambda26(CustomerTypeDropdown.this, this.f$1, (List) obj);
                }
            });
            ((VisitCriteriaDropdown) $visitCriteria.element).setAdapter(new VisitCriteriaDropdownAdapter(this$0, C0550R.layout.dropdown_visit_criteria, (ArrayList) this$0.getViewModel().visitCriteria(), new CustDistActivity$showDialogFilter$2$3($visitCriteria, this$0)));
            VisitCriteriaDropdown visitCriteriaDropdown = (VisitCriteriaDropdown) $visitCriteria.element;
            FilterCustomer value2 = this$0.getViewModel().getFilterCustomer().getValue();
            visitCriteriaDropdown.setText(value2 == null ? null : value2.getVisitCriteriaName(), false);
            $applyFilter.setOnClickListener(new View.OnClickListener($region, $customerType, $dialog) {
                public final */
/* synthetic *//*
 RegionDropdown f$1;
                public final */
/* synthetic *//*
 CustomerTypeDropdown f$2;
                public final */
/* synthetic *//*
 BottomSheetDialog f$3;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                }

                public final void onClick(View view) {
                    CustDistActivity.m584showDialogFilter$lambda29$lambda27(CustDistActivity.this, this.f$1, this.f$2, this.f$3, view);
                }
            });
            return;
        }
        $applyFilter.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                CustDistActivity.m585showDialogFilter$lambda29$lambda28(CustDistActivity.this, view);
            }
        });
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilter$lambda-29$lambda-25  reason: not valid java name *//*

    public static final void m582showDialogFilter$lambda29$lambda25(RegionDropdown $region, CustDistActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (it != null) {
            $region.setupData(it);
            FilterCustomer value = this$0.getViewModel().getFilterCustomer().getValue();
            $region.setText(value == null ? null : value.getRegionName(), false);
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilter$lambda-29$lambda-26  reason: not valid java name *//*

    public static final void m583showDialogFilter$lambda29$lambda26(CustomerTypeDropdown $customerType, CustDistActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (it != null) {
            $customerType.setupData(it);
            FilterCustomer value = this$0.getViewModel().getFilterCustomer().getValue();
            $customerType.setText(value == null ? null : value.getCustomerTypeName(), false);
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:10:0x0059  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:12:0x005f  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:18:0x0080  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:20:0x0086  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:26:0x00a7  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:28:0x00ad  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:34:0x00f3  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:41:0x010c  *//*

    */
/* renamed from: showDialogFilter$lambda-29$lambda-27  reason: not valid java name *//*

    */
/* Code decompiled incorrectly, please refer to instructions dump. *//*

    public static final void m584showDialogFilter$lambda29$lambda27(p000co.p001id.cpn.bdmgafi.p003ui.customer_dist.CustDistActivity r14, p000co.p001id.cpn.bdmgafi.widget.RegionDropdown r15, p000co.p001id.cpn.bdmgafi.widget.CustomerTypeDropdown r16, com.google.android.material.bottomsheet.BottomSheetDialog r17, android.view.View r18) {
        */
/*
            r0 = r14
            java.lang.String r1 = "this$0"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r14, r1)
            java.lang.String r1 = "$dialog"
            r2 = r17
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r1)
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r1 = r14.getViewModel()
            co.id.cpn.entity.FilterCustomer r11 = new co.id.cpn.entity.FilterCustomer
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r14.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilter()
            java.lang.Object r3 = r3.getValue()
            r4 = r3
            java.lang.String r4 = (java.lang.String) r4
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            java.lang.String r3 = r15.getSelectedID()
            java.lang.String r5 = ""
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            r12 = 0
            if (r3 != 0) goto L_0x0038
            java.lang.String r3 = r15.getSelectedID()
        L_0x0036:
            r6 = r3
            goto L_0x004f
        L_0x0038:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r14.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterCustomer()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterCustomer r3 = (p000co.p001id.cpn.entity.FilterCustomer) r3
            if (r3 != 0) goto L_0x004a
            r6 = r12
            goto L_0x004f
        L_0x004a:
            java.lang.String r3 = r3.getRegion()
            goto L_0x0036
        L_0x004f:
            java.lang.String r3 = r15.getSelectedName()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            if (r3 != 0) goto L_0x005f
            java.lang.String r3 = r15.getSelectedName()
        L_0x005d:
            r7 = r3
            goto L_0x0076
        L_0x005f:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r14.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterCustomer()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterCustomer r3 = (p000co.p001id.cpn.entity.FilterCustomer) r3
            if (r3 != 0) goto L_0x0071
            r7 = r12
            goto L_0x0076
        L_0x0071:
            java.lang.String r3 = r3.getRegionName()
            goto L_0x005d
        L_0x0076:
            java.lang.String r3 = r16.getSelectedID()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            if (r3 != 0) goto L_0x0086
            java.lang.String r3 = r16.getSelectedID()
        L_0x0084:
            r8 = r3
            goto L_0x009d
        L_0x0086:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r14.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterCustomer()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterCustomer r3 = (p000co.p001id.cpn.entity.FilterCustomer) r3
            if (r3 != 0) goto L_0x0098
            r8 = r12
            goto L_0x009d
        L_0x0098:
            java.lang.String r3 = r3.getCustomerType()
            goto L_0x0084
        L_0x009d:
            java.lang.String r3 = r16.getSelectedName()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            if (r3 != 0) goto L_0x00ad
            java.lang.String r3 = r16.getSelectedName()
        L_0x00ab:
            r9 = r3
            goto L_0x00c4
        L_0x00ad:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r14.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterCustomer()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterCustomer r3 = (p000co.p001id.cpn.entity.FilterCustomer) r3
            if (r3 != 0) goto L_0x00bf
            r9 = r12
            goto L_0x00c4
        L_0x00bf:
            java.lang.String r3 = r3.getCustomerTypeName()
            goto L_0x00ab
        L_0x00c4:
            java.lang.String r10 = r0.visitCriteriaFilter
            java.lang.String r13 = r0.visitCriteriaFilterName
            r3 = r11
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r13
            r3.<init>(r4, r5, r6, r7, r8, r9, r10)
            r1.submitFilterCustomer(r11)
            android.content.Intent r1 = r14.getIntent()
            android.os.Bundle r1 = r1.getExtras()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            java.lang.String r3 = "dist_type"
            java.lang.String r1 = r1.getString(r3)
            r3 = 0
            r4 = 2
            java.lang.String r5 = "cust"
            boolean r1 = kotlin.text.StringsKt.equals$default(r1, r5, r3, r4, r12)
            java.lang.String r3 = "binding"
            r4 = 1
            if (r1 == 0) goto L_0x010c
            co.id.cpn.bdmgafi.databinding.ActivityCustDistBinding r1 = r0.binding
            if (r1 == 0) goto L_0x0108
            com.google.android.material.tabs.TabLayout r1 = r1.tabLayout
            com.google.android.material.tabs.TabLayout$Tab r1 = r1.getTabAt(r4)
            if (r1 != 0) goto L_0x0100
            goto L_0x0120
        L_0x0100:
            java.lang.String r3 = "CUSTOMER"
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3
            r1.setText((java.lang.CharSequence) r3)
            goto L_0x0120
        L_0x0108:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r3)
            throw r12
        L_0x010c:
            co.id.cpn.bdmgafi.databinding.ActivityCustDistBinding r1 = r0.binding
            if (r1 == 0) goto L_0x0124
            com.google.android.material.tabs.TabLayout r1 = r1.tabLayout
            com.google.android.material.tabs.TabLayout$Tab r1 = r1.getTabAt(r4)
            if (r1 != 0) goto L_0x0119
            goto L_0x0120
        L_0x0119:
            java.lang.String r3 = "ASSET"
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3
            r1.setText((java.lang.CharSequence) r3)
        L_0x0120:
            r17.dismiss()
            return
        L_0x0124:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r3)
            throw r12
        *//*

        throw new UnsupportedOperationException("Method not decompiled: p000co.p001id.cpn.bdmgafi.p003ui.customer_dist.CustDistActivity.m584showDialogFilter$lambda29$lambda27(co.id.cpn.bdmgafi.ui.customer_dist.CustDistActivity, co.id.cpn.bdmgafi.widget.RegionDropdown, co.id.cpn.bdmgafi.widget.CustomerTypeDropdown, com.google.android.material.bottomsheet.BottomSheetDialog, android.view.View):void");
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilter$lambda-29$lambda-28  reason: not valid java name *//*

    public static final void m585showDialogFilter$lambda29$lambda28(CustDistActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCustDistBinding activityCustDistBinding = this$0.binding;
        if (activityCustDistBinding != null) {
            Snackbar.make((View) activityCustDistBinding.getRoot(), (CharSequence) "Please select a region", -1).show();
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilter$lambda-30  reason: not valid java name *//*

    public static final void m586showDialogFilter$lambda30(BottomSheetDialog $dialog, View it) {
        Intrinsics.checkNotNullParameter($dialog, "$dialog");
        $dialog.dismiss();
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilter$lambda-31  reason: not valid java name *//*

    public static final void m587showDialogFilter$lambda31(BottomSheetDialog $dialog, View it) {
        Intrinsics.checkNotNullParameter($dialog, "$dialog");
        $dialog.dismiss();
    }

    public final void showDialogFilterAsset() {
        Object systemService = getSystemService("layout_inflater");
        if (systemService != null) {
            LayoutInflater li = (LayoutInflater) systemService;
            View inflate = li.inflate(C0550R.layout.dialog_filter_asset, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(inflate, "li.inflate(co.id.cpn.bdmgafi.R.layout.dialog_filter_asset, null)");
            View view = inflate;
            BottomSheetDialog dialog = new BottomSheetDialog(this, C0550R.C0557style.AppBottomSheetDialogTheme);
            dialog.setOnShowListener($$Lambda$CustDistActivity$C4QqLDjgqcEu60Qxxj7YQGPzgFM.INSTANCE);
            $$Lambda$CustDistActivity$yYF1ksrqkjaEXXps0VT2g3T0Fg r10 = r0;
            LayoutInflater layoutInflater = li;
            Button button = (Button) view.findViewById(C0550R.C0553id.apply_filter);
            View view2 = view;
            MutableLiveData<Distribution> distSelected = getViewModel().getDistSelected();
            $$Lambda$CustDistActivity$yYF1ksrqkjaEXXps0VT2g3T0Fg r0 = new Observer(button, (RegionDropdown) view.findViewById(C0550R.C0553id.region_dropdown), (CustomerTypeDropdown) view.findViewById(C0550R.C0553id.customer_type), (ItemDropdown) view.findViewById(C0550R.C0553id.merk), (ItemDropdown) view.findViewById(C0550R.C0553id.assetType), (ItemDropdown) view.findViewById(C0550R.C0553id.ownership), (InspectionCriteriaDropdown) view.findViewById(C0550R.C0553id.inspection_criteria), dialog) {
                public final */
/* synthetic *//*
 Button f$1;
                public final */
/* synthetic *//*
 RegionDropdown f$2;
                public final */
/* synthetic *//*
 CustomerTypeDropdown f$3;
                public final */
/* synthetic *//*
 ItemDropdown f$4;
                public final */
/* synthetic *//*
 ItemDropdown f$5;
                public final */
/* synthetic *//*
 ItemDropdown f$6;
                public final */
/* synthetic *//*
 InspectionCriteriaDropdown f$7;
                public final */
/* synthetic *//*
 BottomSheetDialog f$8;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                    this.f$4 = r5;
                    this.f$5 = r6;
                    this.f$6 = r7;
                    this.f$7 = r8;
                    this.f$8 = r9;
                }

                public final void onChanged(Object obj) {
                    CustDistActivity.m589showDialogFilterAsset$lambda40(CustDistActivity.this, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, this.f$8, (Distribution) obj);
                }
            };
            distSelected.observe(this, r10);
            BottomSheetDialog dialog2 = dialog;
            ((ImageView) view.findViewById(C0550R.C0553id.close_button)).setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    CustDistActivity.m597showDialogFilterAsset$lambda41(BottomSheetDialog.this, view);
                }
            });
            ((Button) view.findViewById(C0550R.C0553id.cancel_button)).setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    CustDistActivity.m598showDialogFilterAsset$lambda42(BottomSheetDialog.this, view);
                }
            });
            dialog2.setCancelable(false);
            dialog2.setContentView(view2);
            dialog2.show();
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.LayoutInflater");
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilterAsset$lambda-32  reason: not valid java name *//*

    public static final void m588showDialogFilterAsset$lambda32(DialogInterface dialog) {
        if (dialog != null) {
            View findViewById = ((BottomSheetDialog) dialog).findViewById(C0550R.C0553id.design_bottom_sheet);
            if (findViewById != null) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((FrameLayout) findViewById);
                Intrinsics.checkNotNullExpressionValue(bottomSheetBehavior, "from(bottomSheet)");
                bottomSheetBehavior.setState(3);
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout");
        }
        throw new NullPointerException("null cannot be cast to non-null type com.google.android.material.bottomsheet.BottomSheetDialog");
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilterAsset$lambda-40  reason: not valid java name *//*

    public static final void m589showDialogFilterAsset$lambda40(CustDistActivity this$0, Button $applyFilter, RegionDropdown $region, CustomerTypeDropdown $customerType, ItemDropdown $merk, ItemDropdown $assetType, ItemDropdown $ownership, InspectionCriteriaDropdown $inspectionCriteria, BottomSheetDialog $dialog, Distribution dist) {
        CustDistActivity custDistActivity = this$0;
        Intrinsics.checkNotNullParameter(custDistActivity, "this$0");
        Intrinsics.checkNotNullParameter($dialog, "$dialog");
        if (dist != null) {
            DistributionViewModel viewModel = this$0.getViewModel();
            Distribution value = this$0.getViewModel().getDistSelected().getValue();
            Intrinsics.checkNotNull(value);
            viewModel.regions(value.getDistributionSID()).observe(custDistActivity, new Observer(custDistActivity) {
                public final */
/* synthetic *//*
 CustDistActivity f$1;

                {
                    this.f$1 = r2;
                }

                public final void onChanged(Object obj) {
                    CustDistActivity.m590showDialogFilterAsset$lambda40$lambda33(RegionDropdown.this, this.f$1, (List) obj);
                }
            });
            this$0.getViewModel().customerTypes().observe(custDistActivity, new Observer(custDistActivity) {
                public final */
/* synthetic *//*
 CustDistActivity f$1;

                {
                    this.f$1 = r2;
                }

                public final void onChanged(Object obj) {
                    CustDistActivity.m591showDialogFilterAsset$lambda40$lambda34(CustomerTypeDropdown.this, this.f$1, (List) obj);
                }
            });
            this$0.getViewModel().assetMerk().observe(custDistActivity, new Observer(custDistActivity) {
                public final */
/* synthetic *//*
 CustDistActivity f$1;

                {
                    this.f$1 = r2;
                }

                public final void onChanged(Object obj) {
                    CustDistActivity.m592showDialogFilterAsset$lambda40$lambda35(ItemDropdown.this, this.f$1, (List) obj);
                }
            });
            this$0.getViewModel().assetType().observe(custDistActivity, new Observer(custDistActivity) {
                public final */
/* synthetic *//*
 CustDistActivity f$1;

                {
                    this.f$1 = r2;
                }

                public final void onChanged(Object obj) {
                    CustDistActivity.m593showDialogFilterAsset$lambda40$lambda36(ItemDropdown.this, this.f$1, (List) obj);
                }
            });
            this$0.getViewModel().assetOwnership().observe(custDistActivity, new Observer(custDistActivity) {
                public final */
/* synthetic *//*
 CustDistActivity f$1;

                {
                    this.f$1 = r2;
                }

                public final void onChanged(Object obj) {
                    CustDistActivity.m594showDialogFilterAsset$lambda40$lambda37(ItemDropdown.this, this.f$1, (List) obj);
                }
            });
            this$0.getViewModel().inspectionCriterias().observe(custDistActivity, new Observer(custDistActivity) {
                public final */
/* synthetic *//*
 CustDistActivity f$1;

                {
                    this.f$1 = r2;
                }

                public final void onChanged(Object obj) {
                    CustDistActivity.m595showDialogFilterAsset$lambda40$lambda38(InspectionCriteriaDropdown.this, this.f$1, (List) obj);
                }
            });
            $$Lambda$CustDistActivity$D7GBcGH0NwFFrt2xZEVjy6hJ7A r9 = r0;
            $$Lambda$CustDistActivity$D7GBcGH0NwFFrt2xZEVjy6hJ7A r0 = new View.OnClickListener($region, $customerType, $merk, $assetType, $ownership, $dialog) {
                public final */
/* synthetic *//*
 RegionDropdown f$1;
                public final */
/* synthetic *//*
 CustomerTypeDropdown f$2;
                public final */
/* synthetic *//*
 ItemDropdown f$3;
                public final */
/* synthetic *//*
 ItemDropdown f$4;
                public final */
/* synthetic *//*
 ItemDropdown f$5;
                public final */
/* synthetic *//*
 BottomSheetDialog f$6;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                    this.f$4 = r5;
                    this.f$5 = r6;
                    this.f$6 = r7;
                }

                public final void onClick(View view) {
                    CustDistActivity.m596showDialogFilterAsset$lambda40$lambda39(CustDistActivity.this, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, view);
                }
            };
            $applyFilter.setOnClickListener(r9);
            return;
        }
        Button button = $applyFilter;
        RegionDropdown regionDropdown = $region;
        CustomerTypeDropdown customerTypeDropdown = $customerType;
        ItemDropdown itemDropdown = $merk;
        ItemDropdown itemDropdown2 = $assetType;
        ItemDropdown itemDropdown3 = $ownership;
        InspectionCriteriaDropdown inspectionCriteriaDropdown = $inspectionCriteria;
        ActivityCustDistBinding activityCustDistBinding = custDistActivity.binding;
        if (activityCustDistBinding != null) {
            Snackbar.make((View) activityCustDistBinding.getRoot(), (CharSequence) "Please select a distribution", -1).show();
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilterAsset$lambda-40$lambda-33  reason: not valid java name *//*

    public static final void m590showDialogFilterAsset$lambda40$lambda33(RegionDropdown $region, CustDistActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (it != null) {
            $region.setupData(it);
            FilterAsset value = this$0.getViewModel().getFilterAsset().getValue();
            $region.setText(value == null ? null : value.getRegionName(), false);
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilterAsset$lambda-40$lambda-34  reason: not valid java name *//*

    public static final void m591showDialogFilterAsset$lambda40$lambda34(CustomerTypeDropdown $customerType, CustDistActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (it != null) {
            $customerType.setupData(it);
            FilterAsset value = this$0.getViewModel().getFilterAsset().getValue();
            $customerType.setText(value == null ? null : value.getCustomerTypeName(), false);
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilterAsset$lambda-40$lambda-35  reason: not valid java name *//*

    public static final void m592showDialogFilterAsset$lambda40$lambda35(ItemDropdown $merk, CustDistActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (it != null) {
            $merk.setupData(it);
            FilterAsset value = this$0.getViewModel().getFilterAsset().getValue();
            $merk.setText(value == null ? null : value.getMerkName(), false);
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilterAsset$lambda-40$lambda-36  reason: not valid java name *//*

    public static final void m593showDialogFilterAsset$lambda40$lambda36(ItemDropdown $assetType, CustDistActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (it != null) {
            $assetType.setupData(it);
            FilterAsset value = this$0.getViewModel().getFilterAsset().getValue();
            $assetType.setText(value == null ? null : value.getAssetTypeName(), false);
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilterAsset$lambda-40$lambda-37  reason: not valid java name *//*

    public static final void m594showDialogFilterAsset$lambda40$lambda37(ItemDropdown $ownership, CustDistActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (it != null) {
            $ownership.setupData(it);
            FilterAsset value = this$0.getViewModel().getFilterAsset().getValue();
            $ownership.setText(value == null ? null : value.getOwnershipName(), false);
        }
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilterAsset$lambda-40$lambda-38  reason: not valid java name *//*

    public static final void m595showDialogFilterAsset$lambda40$lambda38(InspectionCriteriaDropdown $inspectionCriteria, CustDistActivity this$0, List inspectionCriteriaData) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        $inspectionCriteria.setAdapter(new InspectionCriteriaDropdownAdapter(this$0, C0550R.layout.dropdown_visit_criteria, inspectionCriteriaData, new CustDistActivity$showDialogFilterAsset$2$6$1($inspectionCriteria, this$0)));
        FilterAsset value = this$0.getViewModel().getFilterAsset().getValue();
        $inspectionCriteria.setText(value == null ? null : value.getInspectionName(), false);
    }

    */
/* access modifiers changed from: private *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:10:0x005a  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:12:0x0060  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:18:0x0081  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:20:0x0087  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:26:0x00a8  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:28:0x00ae  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:34:0x00cf  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:36:0x00d5  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:42:0x00f6  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:44:0x00fc  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:50:0x011d  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:52:0x0123  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:58:0x0144  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:60:0x014a  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:66:0x016b  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:68:0x0172  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:74:0x0194  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:76:0x019b  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:82:0x01f3  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:89:0x020d  *//*

    */
/* renamed from: showDialogFilterAsset$lambda-40$lambda-39  reason: not valid java name *//*

    */
/* Code decompiled incorrectly, please refer to instructions dump. *//*

    public static final void m596showDialogFilterAsset$lambda40$lambda39(p000co.p001id.cpn.bdmgafi.p003ui.customer_dist.CustDistActivity r20, p000co.p001id.cpn.bdmgafi.widget.RegionDropdown r21, p000co.p001id.cpn.bdmgafi.widget.CustomerTypeDropdown r22, p000co.p001id.cpn.bdmgafi.widget.ItemDropdown r23, p000co.p001id.cpn.bdmgafi.widget.ItemDropdown r24, p000co.p001id.cpn.bdmgafi.widget.ItemDropdown r25, com.google.android.material.bottomsheet.BottomSheetDialog r26, android.view.View r27) {
        */
/*
            r0 = r20
            java.lang.String r1 = "this$0"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r1)
            java.lang.String r1 = "$dialog"
            r2 = r26
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r1)
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r1 = r20.getViewModel()
            co.id.cpn.entity.FilterAsset r15 = new co.id.cpn.entity.FilterAsset
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r20.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilter()
            java.lang.Object r3 = r3.getValue()
            r4 = r3
            java.lang.String r4 = (java.lang.String) r4
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            java.lang.String r3 = r21.getSelectedID()
            java.lang.String r5 = ""
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            r14 = 0
            if (r3 != 0) goto L_0x0039
            java.lang.String r3 = r21.getSelectedID()
        L_0x0037:
            r6 = r3
            goto L_0x0050
        L_0x0039:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r20.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterAsset()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterAsset r3 = (p000co.p001id.cpn.entity.FilterAsset) r3
            if (r3 != 0) goto L_0x004b
            r6 = r14
            goto L_0x0050
        L_0x004b:
            java.lang.String r3 = r3.getRegion()
            goto L_0x0037
        L_0x0050:
            java.lang.String r3 = r21.getSelectedName()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            if (r3 != 0) goto L_0x0060
            java.lang.String r3 = r21.getSelectedName()
        L_0x005e:
            r7 = r3
            goto L_0x0077
        L_0x0060:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r20.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterAsset()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterAsset r3 = (p000co.p001id.cpn.entity.FilterAsset) r3
            if (r3 != 0) goto L_0x0072
            r7 = r14
            goto L_0x0077
        L_0x0072:
            java.lang.String r3 = r3.getRegionName()
            goto L_0x005e
        L_0x0077:
            java.lang.String r3 = r22.getSelectedID()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            if (r3 != 0) goto L_0x0087
            java.lang.String r3 = r22.getSelectedID()
        L_0x0085:
            r8 = r3
            goto L_0x009e
        L_0x0087:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r20.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterAsset()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterAsset r3 = (p000co.p001id.cpn.entity.FilterAsset) r3
            if (r3 != 0) goto L_0x0099
            r8 = r14
            goto L_0x009e
        L_0x0099:
            java.lang.String r3 = r3.getCustomerType()
            goto L_0x0085
        L_0x009e:
            java.lang.String r3 = r22.getSelectedName()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            if (r3 != 0) goto L_0x00ae
            java.lang.String r3 = r22.getSelectedName()
        L_0x00ac:
            r9 = r3
            goto L_0x00c5
        L_0x00ae:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r20.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterAsset()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterAsset r3 = (p000co.p001id.cpn.entity.FilterAsset) r3
            if (r3 != 0) goto L_0x00c0
            r9 = r14
            goto L_0x00c5
        L_0x00c0:
            java.lang.String r3 = r3.getCustomerTypeName()
            goto L_0x00ac
        L_0x00c5:
            java.lang.String r3 = r23.getSelectedID()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            if (r3 != 0) goto L_0x00d5
            java.lang.String r3 = r23.getSelectedID()
        L_0x00d3:
            r10 = r3
            goto L_0x00ec
        L_0x00d5:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r20.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterAsset()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterAsset r3 = (p000co.p001id.cpn.entity.FilterAsset) r3
            if (r3 != 0) goto L_0x00e7
            r10 = r14
            goto L_0x00ec
        L_0x00e7:
            java.lang.String r3 = r3.getMerk()
            goto L_0x00d3
        L_0x00ec:
            java.lang.String r3 = r23.getSelectedName()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            if (r3 != 0) goto L_0x00fc
            java.lang.String r3 = r23.getSelectedName()
        L_0x00fa:
            r11 = r3
            goto L_0x0113
        L_0x00fc:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r20.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterAsset()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterAsset r3 = (p000co.p001id.cpn.entity.FilterAsset) r3
            if (r3 != 0) goto L_0x010e
            r11 = r14
            goto L_0x0113
        L_0x010e:
            java.lang.String r3 = r3.getMerkName()
            goto L_0x00fa
        L_0x0113:
            java.lang.String r3 = r24.getSelectedName()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            if (r3 != 0) goto L_0x0123
            java.lang.String r3 = r24.getSelectedName()
        L_0x0121:
            r12 = r3
            goto L_0x013a
        L_0x0123:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r20.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterAsset()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterAsset r3 = (p000co.p001id.cpn.entity.FilterAsset) r3
            if (r3 != 0) goto L_0x0135
            r12 = r14
            goto L_0x013a
        L_0x0135:
            java.lang.String r3 = r3.getAssetType()
            goto L_0x0121
        L_0x013a:
            java.lang.String r3 = r24.getSelectedName()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            if (r3 != 0) goto L_0x014a
            java.lang.String r3 = r24.getSelectedName()
        L_0x0148:
            r13 = r3
            goto L_0x0161
        L_0x014a:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r20.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterAsset()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterAsset r3 = (p000co.p001id.cpn.entity.FilterAsset) r3
            if (r3 != 0) goto L_0x015c
            r13 = r14
            goto L_0x0161
        L_0x015c:
            java.lang.String r3 = r3.getAssetTypeName()
            goto L_0x0148
        L_0x0161:
            java.lang.String r3 = r25.getSelectedName()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            if (r3 != 0) goto L_0x0172
            java.lang.String r3 = r25.getSelectedName()
        L_0x016f:
            r16 = r3
            goto L_0x018a
        L_0x0172:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r20.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterAsset()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterAsset r3 = (p000co.p001id.cpn.entity.FilterAsset) r3
            if (r3 != 0) goto L_0x0185
            r16 = r14
            goto L_0x018a
        L_0x0185:
            java.lang.String r3 = r3.getOwnership()
            goto L_0x016f
        L_0x018a:
            java.lang.String r3 = r25.getSelectedName()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
            if (r3 != 0) goto L_0x019b
            java.lang.String r3 = r25.getSelectedName()
        L_0x0198:
            r17 = r3
            goto L_0x01b3
        L_0x019b:
            co.id.cpn.bdmgafi.ui.customer_dist.CustDistViewModel r3 = r20.getViewModel()
            androidx.lifecycle.LiveData r3 = r3.getFilterAsset()
            java.lang.Object r3 = r3.getValue()
            co.id.cpn.entity.FilterAsset r3 = (p000co.p001id.cpn.entity.FilterAsset) r3
            if (r3 != 0) goto L_0x01ae
            r17 = r14
            goto L_0x01b3
        L_0x01ae:
            java.lang.String r3 = r3.getOwnershipName()
            goto L_0x0198
        L_0x01b3:
            java.lang.String r5 = r0.inspectionCriteriaFilter
            java.lang.String r3 = r0.inspectionCriteriaFilterName
            r18 = r3
            r3 = r15
            r19 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r16
            r2 = r14
            r14 = r17
            r2 = r15
            r15 = r19
            r16 = r18
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)
            r1.submitFilterAsset(r2)
            android.content.Intent r1 = r20.getIntent()
            android.os.Bundle r1 = r1.getExtras()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            java.lang.String r2 = "dist_type"
            java.lang.String r1 = r1.getString(r2)
            r2 = 0
            r3 = 2
            java.lang.String r4 = "cust"
            r5 = 0
            boolean r1 = kotlin.text.StringsKt.equals$default(r1, r4, r2, r3, r5)
            java.lang.String r2 = "binding"
            r3 = 1
            if (r1 == 0) goto L_0x020d
            co.id.cpn.bdmgafi.databinding.ActivityCustDistBinding r1 = r0.binding
            if (r1 == 0) goto L_0x0208
            com.google.android.material.tabs.TabLayout r1 = r1.tabLayout
            com.google.android.material.tabs.TabLayout$Tab r1 = r1.getTabAt(r3)
            if (r1 != 0) goto L_0x0200
            goto L_0x0221
        L_0x0200:
            java.lang.String r2 = "CUSTOMER"
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            r1.setText((java.lang.CharSequence) r2)
            goto L_0x0221
        L_0x0208:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            r1 = 0
            throw r1
        L_0x020d:
            co.id.cpn.bdmgafi.databinding.ActivityCustDistBinding r1 = r0.binding
            if (r1 == 0) goto L_0x0225
            com.google.android.material.tabs.TabLayout r1 = r1.tabLayout
            com.google.android.material.tabs.TabLayout$Tab r1 = r1.getTabAt(r3)
            if (r1 != 0) goto L_0x021a
            goto L_0x0221
        L_0x021a:
            java.lang.String r2 = "ASSET"
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            r1.setText((java.lang.CharSequence) r2)
        L_0x0221:
            r26.dismiss()
            return
        L_0x0225:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            r1 = 0
            throw r1
        *//*

        throw new UnsupportedOperationException("Method not decompiled: p000co.p001id.cpn.bdmgafi.p003ui.customer_dist.CustDistActivity.m596showDialogFilterAsset$lambda40$lambda39(co.id.cpn.bdmgafi.ui.customer_dist.CustDistActivity, co.id.cpn.bdmgafi.widget.RegionDropdown, co.id.cpn.bdmgafi.widget.CustomerTypeDropdown, co.id.cpn.bdmgafi.widget.ItemDropdown, co.id.cpn.bdmgafi.widget.ItemDropdown, co.id.cpn.bdmgafi.widget.ItemDropdown, com.google.android.material.bottomsheet.BottomSheetDialog, android.view.View):void");
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilterAsset$lambda-41  reason: not valid java name *//*

    public static final void m597showDialogFilterAsset$lambda41(BottomSheetDialog $dialog, View it) {
        Intrinsics.checkNotNullParameter($dialog, "$dialog");
        $dialog.dismiss();
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogFilterAsset$lambda-42  reason: not valid java name *//*

    public static final void m598showDialogFilterAsset$lambda42(BottomSheetDialog $dialog, View it) {
        Intrinsics.checkNotNullParameter($dialog, "$dialog");
        $dialog.dismiss();
    }

    public final void showDialogEmptyData() {
        String tag = getTAG();
        Dialog dialog = getDialog();
        String str = null;
        Log.w(tag, Intrinsics.stringPlus("showDialogEmptyData: ", dialog == null ? null : Boolean.valueOf(dialog.isShowing())));
        if (!this.dialogEmpty) {
            this.dialogEmpty = true;
            AppUtils appUtils = AppUtils.INSTANCE;
            Context context = this;
            Distribution value = getViewModel().getDistSelected().getValue();
            String stringPlus = Intrinsics.stringPlus("No data found ", value == null ? null : value.getDistributionName());
            StringBuilder sb = new StringBuilder();
            sb.append("Please select other distribution, or download ");
            Distribution value2 = getViewModel().getDistSelected().getValue();
            if (value2 != null) {
                str = value2.getDistributionName();
            }
            sb.append(str);
            sb.append(" data");
            appUtils.confirmDialog(context, stringPlus, sb.toString(), C0550R.C0552drawable.ic_undraw_empty, "Close", "Download", new CustDistActivity$showDialogEmptyData$1(this));
        }
    }

    public final void showDialogEmptyDataAsset() {
        String tag = getTAG();
        Dialog dialog = getDialog();
        String str = null;
        Log.w(tag, Intrinsics.stringPlus("showDialogEmptyData: ", dialog == null ? null : Boolean.valueOf(dialog.isShowing())));
        if (!this.dialogEmpty) {
            this.dialogEmpty = true;
            AppUtils appUtils = AppUtils.INSTANCE;
            Context context = this;
            Distribution value = getViewModel().getDistSelected().getValue();
            String stringPlus = Intrinsics.stringPlus("No data found ", value == null ? null : value.getDistributionName());
            StringBuilder sb = new StringBuilder();
            sb.append("Please select other distribution, or download ");
            Distribution value2 = getViewModel().getDistSelected().getValue();
            if (value2 != null) {
                str = value2.getDistributionName();
            }
            sb.append(str);
            sb.append(" data");
            appUtils.confirmDialog(context, stringPlus, sb.toString(), C0550R.C0552drawable.ic_undraw_empty, "Close", "Download", new CustDistActivity$showDialogEmptyDataAsset$1(this));
        }
    }

    public final void showDialogCustomerPhoto() {
        Object systemService = getSystemService("layout_inflater");
        if (systemService != null) {
            String str = null;
            View view = ((LayoutInflater) systemService).inflate(C0550R.layout.dialog_customer_photo, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(view, "li.inflate(co.id.cpn.bdmgafi.R.layout.dialog_customer_photo, null)");
            ImageView close = (ImageView) view.findViewById(C0550R.C0553id.close_button);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(C0550R.C0553id.recyclerView);
            DistributionViewModel viewModel = getViewModel();
            Distribution value = getViewModel().getDistSelected().getValue();
            if (value != null) {
                str = value.getDistributionSID();
            }
            Intrinsics.checkNotNull(str);
            viewModel.getPhotoType(str).observe(this, $$Lambda$CustDistActivity$jQEZkCU3wDsCJxonl8f5lAiYAY.INSTANCE);
            BottomSheetDialog dialog = new BottomSheetDialog(this, C0550R.C0557style.AppBottomSheetDialogTheme);
            dialog.setOnShowListener($$Lambda$CustDistActivity$Aw_BpnSw_an5f6Jx_ZSQuzk_QFo.INSTANCE);
            close.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    CustDistActivity.m579showDialogCustomerPhoto$lambda45(BottomSheetDialog.this, view);
                }
            });
            dialog.setCancelable(false);
            dialog.setContentView(view);
            dialog.show();
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.LayoutInflater");
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogCustomerPhoto$lambda-43  reason: not valid java name *//*

    public static final void m577showDialogCustomerPhoto$lambda43(List dist) {
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogCustomerPhoto$lambda-44  reason: not valid java name *//*

    public static final void m578showDialogCustomerPhoto$lambda44(DialogInterface dialog) {
        if (dialog != null) {
            View findViewById = ((BottomSheetDialog) dialog).findViewById(C0550R.C0553id.design_bottom_sheet);
            if (findViewById != null) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((FrameLayout) findViewById);
                Intrinsics.checkNotNullExpressionValue(bottomSheetBehavior, "from(bottomSheet)");
                bottomSheetBehavior.setState(3);
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout");
        }
        throw new NullPointerException("null cannot be cast to non-null type com.google.android.material.bottomsheet.BottomSheetDialog");
    }

    */
/* access modifiers changed from: private *//*

    */
/* renamed from: showDialogCustomerPhoto$lambda-45  reason: not valid java name *//*

    public static final void m579showDialogCustomerPhoto$lambda45(BottomSheetDialog $dialog, View it) {
        Intrinsics.checkNotNullParameter($dialog, "$dialog");
        $dialog.dismiss();
    }
}

*/
