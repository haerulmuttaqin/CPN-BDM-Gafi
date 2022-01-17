package co.id.cpn.bdmgafi.ui.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import org.koin.core.qualifier.Qualifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import co.id.cpn.bdmgafi.R;
import co.id.cpn.bdmgafi.databinding.ActivityLoginBinding;
import co.id.cpn.bdmgafi.ui.base.BaseActivity;
import co.id.cpn.bdmgafi.ui.main.MainActivity;
import co.id.cpn.bdmgafi.ui.splash.SplashActivity;
import co.id.cpn.bdmgafi.util.AppUtils;
import co.id.cpn.data.local.spref.SharedPref;
import co.id.cpn.entity.DataBody;
import co.id.cpn.entity.Resource;
import co.id.cpn.entity.login.LoginRequest;
import co.id.cpn.entity.login.LoginResponse;
import co.id.cpn.entity.util.Constants;
import co.id.cpn.entity.util.Utils;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

public final class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private String deviceID = "";
    private String handSetID = "";
    private LoginRequest loginRequest;
    private final Lazy viewModel$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, new LoginActivity$special$$inlined$viewModel$default$1(this, (Qualifier) null, (Function0) null));

    /* access modifiers changed from: private */
    public final LoginViewModel getViewModel() {
        return (LoginViewModel) this.viewModel$delegate.getValue();
    }

    /* access modifiers changed from: protected */
    public void initViewBinding() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private final boolean permissionChecker() {
        if (Build.VERSION.SDK_INT < 30) {
            return (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0) ? false : true;
        } else if (!Environment.isExternalStorageManager()) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (permissionChecker()) {
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }
        Utils.INSTANCE.createDir();
        if (new SharedPref(this).getLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        ActivityLoginBinding activityLoginBinding = this.binding;
        if (activityLoginBinding != null) {
            activityLoginBinding.textVersion.setText(Intrinsics.stringPlus("App Version ", Utils.INSTANCE.appVersion(this)));
            String serverKey = this.handSetID;
            Log.w(getTAG(), Intrinsics.stringPlus("=============== ", serverKey));
            if (!Intrinsics.areEqual((Object) serverKey, (Object) "")) {
                ActivityLoginBinding activityLoginBinding2 = this.binding;
                if (activityLoginBinding2 != null) {
                    activityLoginBinding2.serverKeyLayout.setVisibility(8);
                    ActivityLoginBinding activityLoginBinding3 = this.binding;
                    if (activityLoginBinding3 != null) {
                        activityLoginBinding3.serverKey.setText(serverKey);
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        throw null;
                    }
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    throw null;
                }
            }
            ActivityLoginBinding activityLoginBinding4 = this.binding;
            if (activityLoginBinding4 != null) {
                activityLoginBinding4.rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        LoginActivity.m631onCreate$lambda0(LoginActivity.this, compoundButton, z);
                    }
                });
                ActivityLoginBinding activityLoginBinding5 = this.binding;
                if (activityLoginBinding5 != null) {
                    activityLoginBinding5.loginButton.setOnClickListener(new View.OnClickListener() {
                        public final void onClick(View view) {
                            LoginActivity.m632onCreate$lambda1(LoginActivity.this, view);
                        }
                    });
                    ActivityLoginBinding activityLoginBinding6 = this.binding;
                    if (activityLoginBinding6 != null) {
                        activityLoginBinding6.resetServerKey.setOnClickListener(new View.OnClickListener() {
                            public final void onClick(View view) {
                                LoginActivity.m633onCreate$lambda2(LoginActivity.this, view);
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
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                throw null;
            }
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: onCreate$lambda-2  reason: not valid java name */
    public static final void m633onCreate$lambda2(LoginActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AppUtils.INSTANCE.confirmDialog(this$0, "Reset Server Key", "Are you sure?", R.drawable.ic_undraw_login_re_4vu2, "Cancel", "Yes", new LoginActivity$onCreate$3$1(this$0));
    }

    private final void createFile(String fileName, String content) {
        try {
            FileOutputStream fileOutPutStream = new FileOutputStream(
                    new File(Intrinsics.stringPlus(Environment.getExternalStorageDirectory().toString(), Constants.PATH), fileName));
            Charset charset = Charsets.UTF_8;
            if (content != null) {
                byte[] bytes = content.getBytes(charset);
                Intrinsics.checkNotNullExpressionValue(bytes, "(this as java.lang.String).getBytes(charset)");
                fileOutPutStream.write(bytes);
                fileOutPutStream.close();
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final void readFileDeviceID(String fileName) {
        File myExternalFile = new File(Intrinsics.stringPlus(Environment.getExternalStorageDirectory().toString(), Constants.PATH), fileName);
        if (!myExternalFile.exists()) {
            createFile(fileName, String.valueOf(Utils.INSTANCE.getDeviceID(this)));
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(myExternalFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder stringBuilder = new StringBuilder();
            Ref.ObjectRef text = new Ref.ObjectRef();
            while (new LoginActivity$readFileDeviceID$1(text, bufferedReader).invoke() != null) {
                stringBuilder.append((String) text.element);
            }
            this.deviceID = String.valueOf(stringBuilder);
            ActivityLoginBinding activityLoginBinding = this.binding;
            if (activityLoginBinding != null) {
                activityLoginBinding.textHandsetKey.setText(Intrinsics.stringPlus("Handset Key: ", this.deviceID));
                Log.w(getTAG(), Intrinsics.stringPlus("deviceid: ", this.deviceID));
                fileInputStream.close();
                return;
            }
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public final void removeConfigFile() {
        File file1 = new File(Intrinsics.stringPlus(Environment.getExternalStorageDirectory().toString(), Constants.PATH), ".data.1.0.device.txt");
        File file2 = new File(Intrinsics.stringPlus(Environment.getExternalStorageDirectory().toString(), Constants.PATH), ".data.1.0.skey.txt");
        if (file1.exists()) {
            file1.delete();
        }
        if (file2.exists()) {
            file2.delete();
        }
    }

    private final void readFileHandSet(String fileName) throws IOException {
        File myExternalFile = new File(Intrinsics.stringPlus(Environment.getExternalStorageDirectory().toString(), Constants.PATH), fileName);
        if (myExternalFile.exists() && fileName.toString() != null) {
            String str = fileName.toString();
            if (str == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.CharSequence");
            } else if (!Intrinsics.areEqual((Object) StringsKt.trim((CharSequence) str).toString(), (Object) "")) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(myExternalFile);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    Ref.ObjectRef text = new Ref.ObjectRef();
                    while (bufferedReader.readLine() == text != null) {
                        stringBuilder.append((String) text.element);
                    }
                    this.handSetID = String.valueOf(stringBuilder);
                    Log.w(getTAG(), Intrinsics.stringPlus("handsetid: ", this.handSetID));
                    if (!Intrinsics.areEqual((Object) this.handSetID, (Object) "")) {
                        ActivityLoginBinding activityLoginBinding = this.binding;
                        if (activityLoginBinding != null) {
                            activityLoginBinding.serverKeyLayout.setVisibility(8);
                            ActivityLoginBinding activityLoginBinding2 = this.binding;
                            if (activityLoginBinding2 != null) {
                                activityLoginBinding2.serverKey.setText(this.handSetID);
                            } else {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                throw null;
                            }
                        } else {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            throw null;
                        }
                    }
                    fileInputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void observeViewModel() {
        getViewModel().getLoginLiveData().observe(this, new Observer() {
            public final void onChanged(Object obj) {
                LoginActivity.this.handleLoginResult((Resource) obj);
            }
        });
    }

    private final void doLogin() {
        ActivityLoginBinding activityLoginBinding = this.binding;
        if (activityLoginBinding != null) {
            boolean z = true;
            if (String.valueOf(activityLoginBinding.username.getText()).length() == 0) {
                ActivityLoginBinding activityLoginBinding2 = this.binding;
                if (activityLoginBinding2 != null) {
                    activityLoginBinding2.username.setError("This field can't be empty");
                    ActivityLoginBinding activityLoginBinding3 = this.binding;
                    if (activityLoginBinding3 != null) {
                        activityLoginBinding3.username.requestFocus();
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        throw null;
                    }
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    throw null;
                }
            } else {
                ActivityLoginBinding activityLoginBinding4 = this.binding;
                if (activityLoginBinding4 != null) {
                    if (String.valueOf(activityLoginBinding4.password.getText()).length() == 0) {
                        ActivityLoginBinding activityLoginBinding5 = this.binding;
                        if (activityLoginBinding5 != null) {
                            activityLoginBinding5.password.setError("This field can't be empty");
                            ActivityLoginBinding activityLoginBinding6 = this.binding;
                            if (activityLoginBinding6 != null) {
                                activityLoginBinding6.password.requestFocus();
                            } else {
                                Intrinsics.throwUninitializedPropertyAccessException("binding");
                                throw null;
                            }
                        } else {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            throw null;
                        }
                    } else {
                        ActivityLoginBinding activityLoginBinding7 = this.binding;
                        if (activityLoginBinding7 != null) {
                            if (String.valueOf(activityLoginBinding7.serverKey.getText()).length() != 0) {
                                z = false;
                            }
                            if (z) {
                                ActivityLoginBinding activityLoginBinding8 = this.binding;
                                if (activityLoginBinding8 != null) {
                                    activityLoginBinding8.serverKey.setError("This field can't be empty");
                                    ActivityLoginBinding activityLoginBinding9 = this.binding;
                                    if (activityLoginBinding9 != null) {
                                        activityLoginBinding9.serverKey.requestFocus();
                                    } else {
                                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                                        throw null;
                                    }
                                } else {
                                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                                    throw null;
                                }
                            } else {
                                ActivityLoginBinding activityLoginBinding10 = this.binding;
                                if (activityLoginBinding10 != null) {
                                    String valueOf = String.valueOf(activityLoginBinding10.username.getText());
                                    ActivityLoginBinding activityLoginBinding11 = this.binding;
                                    if (activityLoginBinding11 != null) {
                                        String valueOf2 = String.valueOf(activityLoginBinding11.password.getText());
                                        ActivityLoginBinding activityLoginBinding12 = this.binding;
                                        if (activityLoginBinding12 != null) {
                                            String valueOf3 = String.valueOf(activityLoginBinding12.serverKey.getText());
                                            String str = this.deviceID;
                                            String appVersion = Utils.INSTANCE.appVersion(this);
                                            String oprName = Utils.INSTANCE.getOprName(this);
                                            String currentDateTime = Utils.INSTANCE.currentDateTime();
                                            ActivityLoginBinding activityLoginBinding13 = this.binding;
                                            if (activityLoginBinding13 != null) {
                                                this.loginRequest = new LoginRequest(valueOf, valueOf2, valueOf3, str, appVersion, oprName, 0.0d, 0.0d, currentDateTime, activityLoginBinding13.rememberMe.isChecked());
                                                LoginViewModel viewModel = getViewModel();
                                                LoginRequest loginRequest2 = this.loginRequest;
                                                if (loginRequest2 != null) {
                                                    viewModel.doLogin(loginRequest2);
                                                } else {
                                                    Intrinsics.throwUninitializedPropertyAccessException("loginRequest");
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
                        } else {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                            throw null;
                        }
                    }
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    throw null;
                }
            }
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            throw null;
        }
    }

    /* access modifiers changed from: private */
    public final void handleLoginResult(Resource<DataBody<LoginResponse>> response) {
        if (response instanceof Resource.Loading) {
            disableView();
        } else if (response instanceof Resource.Success) {
            DataBody dataBody = (DataBody) ((Resource.Success) response).getData();
            getViewModel().resetDistribution();
            for (ListDistributionItem dist : ((LoginResponse) ((DataBody) ((Resource.Success) response).getData()).getData()).getListDistribution()) {
                getViewModel().storeDistribution(dist);
            }
            getViewModel().storeInspectionCriteria(new InspectionCriteria("", "All Inspection Criteria", "#FFFFFF"));
            for (InspectionCriteria insp : ((LoginResponse) ((DataBody) ((Resource.Success) response).getData()).getData()).getComboAssetStatus()) {
                getViewModel().storeInspectionCriteria(insp);
            }
            LoginResponse userData = (LoginResponse) ((DataBody) ((Resource.Success) response).getData()).getData();
            Log.w(getTAG(), Intrinsics.stringPlus("handleLoginResult: ", userData));
            SharedPref $this$handleLoginResult_u24lambda_u2d4_u24lambda_u2d3 = new SharedPref(this);
            $this$handleLoginResult_u24lambda_u2d4_u24lambda_u2d3.setUser(userData);
            $this$handleLoginResult_u24lambda_u2d4_u24lambda_u2d3.setUserName(((LoginResponse) ((DataBody) ((Resource.Success) response).getData()).getData()).getUserName());
            $this$handleLoginResult_u24lambda_u2d4_u24lambda_u2d3.setAuthorization(((LoginResponse) ((DataBody) ((Resource.Success) response).getData()).getData()).getAuthorization());
            $this$handleLoginResult_u24lambda_u2d4_u24lambda_u2d3.setLoggedIn(true);
            LoginRequest loginRequest2 = this.loginRequest;
            if (loginRequest2 != null) {
                $this$handleLoginResult_u24lambda_u2d4_u24lambda_u2d3.setUserKey(loginRequest2);
                $this$handleLoginResult_u24lambda_u2d4_u24lambda_u2d3.setUserRole(userData.getUserRole());
                getViewModel().storeUser(userData.getUserSid(), userData.getUserRole(), userData.getUserName());
                if (!new File(Intrinsics.stringPlus(Environment.getExternalStorageDirectory().toString(), Constants.PATH), "data.1.0.skey.txt").exists() || Intrinsics.areEqual((Object) this.handSetID, (Object) "")) {
                    ActivityLoginBinding activityLoginBinding = this.binding;
                    if (activityLoginBinding != null) {
                        createFile(".data.1.0.skey.txt", String.valueOf(activityLoginBinding.serverKey.getText()));
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        throw null;
                    }
                }
                navigateToMainScreen();
                return;
            }
            Intrinsics.throwUninitializedPropertyAccessException("loginRequest");
            throw null;
        } else if (response instanceof Resource.Failed) {
            enableView();
            if (((Resource.Failed) response).getCode() == 401) {
                AppUtils.INSTANCE.errorDialog(this, ((Resource.Failed) response).getMessage());
            } else {
                AppUtils.INSTANCE.errorDialog(this, ((Resource.Failed) response).getMessage());
            }
        } else if (response instanceof Resource.Error) {
            enableView();
            AppUtils.INSTANCE.errorDialog(this, "Network error [code: " + ((Resource.Error) response).getCode() + ']');
        } else {
            enableView();
            AppUtils.INSTANCE.errorDialog(this, "Empty response from server!");
        }
    }

    private final void disableView() {
        ActivityLoginBinding activityLoginBinding = this.binding;
        if (activityLoginBinding != null) {
            activityLoginBinding.progress.setEnabled(true);
            ActivityLoginBinding activityLoginBinding2 = this.binding;
            if (activityLoginBinding2 != null) {
                activityLoginBinding2.loginButton.setText("");
                ActivityLoginBinding activityLoginBinding3 = this.binding;
                if (activityLoginBinding3 != null) {
                    activityLoginBinding3.loginButton.setEnabled(false);
                    ActivityLoginBinding activityLoginBinding4 = this.binding;
                    if (activityLoginBinding4 != null) {
                        activityLoginBinding4.username.setEnabled(false);
                        ActivityLoginBinding activityLoginBinding5 = this.binding;
                        if (activityLoginBinding5 != null) {
                            activityLoginBinding5.password.setEnabled(false);
                            ActivityLoginBinding activityLoginBinding6 = this.binding;
                            if (activityLoginBinding6 != null) {
                                activityLoginBinding6.serverKey.setEnabled(false);
                                ActivityLoginBinding activityLoginBinding7 = this.binding;
                                if (activityLoginBinding7 != null) {
                                    activityLoginBinding7.rememberMe.setEnabled(false);
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

    private final void enableView() {
        ActivityLoginBinding activityLoginBinding = this.binding;
        if (activityLoginBinding != null) {
            activityLoginBinding.progress.setEnabled(false);
            ActivityLoginBinding activityLoginBinding2 = this.binding;
            if (activityLoginBinding2 != null) {
                activityLoginBinding2.loginButton.setText("Login");
                ActivityLoginBinding activityLoginBinding3 = this.binding;
                if (activityLoginBinding3 != null) {
                    activityLoginBinding3.loginButton.setEnabled(true);
                    ActivityLoginBinding activityLoginBinding4 = this.binding;
                    if (activityLoginBinding4 != null) {
                        activityLoginBinding4.username.setEnabled(true);
                        ActivityLoginBinding activityLoginBinding5 = this.binding;
                        if (activityLoginBinding5 != null) {
                            activityLoginBinding5.password.setEnabled(true);
                            ActivityLoginBinding activityLoginBinding6 = this.binding;
                            if (activityLoginBinding6 != null) {
                                activityLoginBinding6.serverKey.setEnabled(true);
                                ActivityLoginBinding activityLoginBinding7 = this.binding;
                                if (activityLoginBinding7 != null) {
                                    activityLoginBinding7.rememberMe.setEnabled(true);
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

    private final void navigateToMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        readFileDeviceID(".data.1.0.device.txt");
        readFileHandSet(".data.1.0.skey.txt");
        if (new SharedPref(this).isRemember()) {
            ActivityLoginBinding activityLoginBinding = this.binding;
            if (activityLoginBinding != null) {
                activityLoginBinding.rememberMe.setChecked(true);
                ActivityLoginBinding activityLoginBinding2 = this.binding;
                if (activityLoginBinding2 != null) {
                    activityLoginBinding2.username.setText(new SharedPref(this).getUserKey().getUsername());
                    ActivityLoginBinding activityLoginBinding3 = this.binding;
                    if (activityLoginBinding3 != null) {
                        activityLoginBinding3.password.setText(new SharedPref(this).getUserKey().getPassword());
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
    }
}