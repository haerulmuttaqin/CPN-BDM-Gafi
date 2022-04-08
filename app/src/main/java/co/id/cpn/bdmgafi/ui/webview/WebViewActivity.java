package co.id.cpn.bdmgafi.ui.webview;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebViewClient;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import co.id.cpn.bdmgafi.databinding.ActivityWebviewBinding;
import co.id.cpn.entity.util.Constants;
import kotlin.jvm.internal.Intrinsics;

public final class WebViewActivity extends AppCompatActivity {
    private ActivityWebviewBinding binding;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWebviewBinding inflate = ActivityWebviewBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        String str = null;
        if (inflate != null) {
            setContentView((View) inflate.getRoot());
            ActivityWebviewBinding activityWebviewBinding = this.binding;
            if (activityWebviewBinding != null) {
                setSupportActionBar(activityWebviewBinding.toolbar);
                ActionBar supportActionBar = getSupportActionBar();
                if (supportActionBar != null) {
                    supportActionBar.setDisplayHomeAsUpEnabled(true);
                }
                Bundle extras = getIntent().getExtras();
                String titleExtra = extras == null ? null : extras.getString(Constants.TITLE);
                Bundle extras2 = getIntent().getExtras();
                if (extras2 != null) {
                    str = extras2.getString(Constants.URL);
                }
                String urlExtra = str;
                if (getTitle() != null) {
                    setTitle(titleExtra);
                    if (urlExtra == null) {
                        return;
                    }
                    if (Intrinsics.areEqual((Object) urlExtra, (Object) "cust_distibution")) {
                        initWebView("https://google.com");
                    } else {
                        initWebView(urlExtra);
                    }
                }
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            }
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }
    }

    private final void initWebView(String url) {
        ActivityWebviewBinding activityWebviewBinding = this.binding;
        if (activityWebviewBinding != null) {
            activityWebviewBinding.webView.getSettings().setLoadsImagesAutomatically(true);
            ActivityWebviewBinding activityWebviewBinding2 = this.binding;
            if (activityWebviewBinding2 != null) {
                activityWebviewBinding2.webView.getSettings().setJavaScriptEnabled(true);
                ActivityWebviewBinding activityWebviewBinding3 = this.binding;
                if (activityWebviewBinding3 != null) {
                    activityWebviewBinding3.webView.getSettings().setDomStorageEnabled(true);
                    ActivityWebviewBinding activityWebviewBinding4 = this.binding;
                    if (activityWebviewBinding4 != null) {
                        activityWebviewBinding4.webView.getSettings().setSupportZoom(true);
                        ActivityWebviewBinding activityWebviewBinding5 = this.binding;
                        if (activityWebviewBinding5 != null) {
                            activityWebviewBinding5.webView.getSettings().setBuiltInZoomControls(true);
                            ActivityWebviewBinding activityWebviewBinding6 = this.binding;
                            if (activityWebviewBinding6 != null) {
                                activityWebviewBinding6.webView.getSettings().setDisplayZoomControls(false);
                                ActivityWebviewBinding activityWebviewBinding7 = this.binding;
                                if (activityWebviewBinding7 != null) {
                                    activityWebviewBinding7.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                                    ActivityWebviewBinding activityWebviewBinding8 = this.binding;
                                    if (activityWebviewBinding8 != null) {
                                        activityWebviewBinding8.webView.setWebViewClient(new WebViewClient());
                                        ActivityWebviewBinding activityWebviewBinding9 = this.binding;
                                        if (activityWebviewBinding9 != null) {
                                            activityWebviewBinding9.webView.loadUrl(url);
                                        } else {
                                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                                            throw null;
                                        }
                                    } else {
                                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                                        throw null;
                                    }
                                } else {
                                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                                    throw null;
                                }
                            } else {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                throw null;
                            }
                        } else {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            throw null;
                        }
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        throw null;
                    }
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    throw null;
                }
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }

    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}