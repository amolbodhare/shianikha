package com.nikha;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Static;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.nikha.shianikha.R;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.CommonListHolder;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;

import java.util.List;

public class App extends Application implements View.OnTouchListener {
    public static String device_id = "";
    public static Json masterJson = new Json();
    public static Fragment tempFragment;
    public static boolean IS_DEV = false;
    public static boolean showName;
    public static boolean notificationFlag = true;

    public static String fcmToken = "";

    public static Json json = new Json();

    public static App app;
    public static String hashKey;

    public static String tempOTP;
    public static boolean showWidget;
    public static boolean stopByForce;

    private View view;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();

        device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        //device_id = Settings.Secure.ANDROID_ID;

        app = new App();

        Static.show_log = false;

        handleMusicPlayer();

    }//init

    private void handleMusicPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.ali_ke_sath);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        final Handler handler = new Handler();
        Runnable runnable;
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1230);
                boolean bool = isAppOnForeground(App.this, getPackageName());
                //H.log("boolIs", bool + "");
                if (!bool) {
                    mediaPlayer.pause();
                    if (view != null)
                        view.setVisibility(View.GONE);
                } else if (!stopByForce)
                    mediaPlayer.start();

                if (bool && showWidget) {
                    if (view == null)
                        handleStopMusicPlayer();
                    else
                        view.setVisibility(View.VISIBLE);
                }
            }
        };
        runnable.run();
    }

    private boolean isAppOnForeground(Context context, String appPackageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        final String packageName = appPackageName;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                //Log.e("app",appPackageName);
                return true;
            }
        }
        return false;
    }

    public void extractHashKey(Context context) {
        AppSignatureHashHelper appSignatureHashHelper = new AppSignatureHashHelper(context);

        if (appSignatureHashHelper.getAppSignatures() != null && appSignatureHashHelper.getAppSignatures().size() > 0) {
            hashKey = appSignatureHashHelper.getAppSignatures().get(0);
            H.log("hashKeyIs", hashKey);
        }
    }

    public void startSmsListener(Context context) {
        MySMSBroadcastReceiver mySMSBroadcastReceiver = new MySMSBroadcastReceiver(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        context.registerReceiver(mySMSBroadcastReceiver, intentFilter);

        SmsRetrieverClient client = SmsRetriever.getClient(context);

        Task<Void> task = client.startSmsRetriever();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // API successfully started
                H.log(getClass().getSimpleName(), "isExecuted");
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Fail to start API
                e.printStackTrace();
                H.log(getClass().getSimpleName(), e.toString());
            }
        });
    }

    private void handleStopMusicPlayer() {

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);


        view = LayoutInflater.from(this).inflate(R.layout.widget_layout, null, false);

        layoutParams.gravity = Gravity.TOP | Gravity.END;
        layoutParams.x = (int) H.convertDpToPixel(60, this);
        layoutParams.y = 0;
        view.setLayoutParams(layoutParams);
        windowManager.addView(view, layoutParams);

        //view.findViewById(R.id.imageView).setOnTouchListener(this);

        view.findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //H.showMessage(App.this,"fired");
                if (view.getTag().toString().equalsIgnoreCase("1")) {
                    mediaPlayer.pause();
                    view.setTag("0");
                    stopByForce = true;
                    ((ImageView) view.findViewById(R.id.imageView)).setImageDrawable(getDrawable(R.drawable.ic_play_arrow_black_24dp));
                } else {
                    stopByForce = false;
                    mediaPlayer.start();
                    view.setTag("1");
                    ((ImageView) view.findViewById(R.id.imageView)).setImageDrawable(getDrawable(R.drawable.ic_pause_black_24dp));
                }
            }
        });
    }

    private float dX;
    private float dY;
    private int lastAction;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;

                H.showMessage(this, "actionDownIsFired");
                break;

            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY() + dY);
                view.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN)
                    H.showMessage(this, "Toast is fired.");
                else {

                    H.showMessage(this, "Button is released.");
                }

                break;

            default:
                return false;
        }
        return true;
    }

    public void hitStateApi(String countryCode, final Context context, final StateAndCityListCallBack stateAndCityListCallBack) {
        final LoadingDialog loadingDialog = new LoadingDialog(context);

        Json json = new Json();
        json.addString(P.country, countryCode);

        RequestModel requestModel = RequestModel.newRequestModel("get_state_id");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(context, "Something went wrong.");
                    }
                })
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (isLoading)
                            loadingDialog.show("loading state...");
                        else
                            loadingDialog.hide();
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1) {
                            JsonList jsonList = json.getJsonList(P.data);
                            CommonListHolder commonListHolder = new CommonListHolder();
                            commonListHolder.makeStateList(jsonList);
                            stateAndCityListCallBack.listIsPrepared();
                        } else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitStateApi");
    }

    public void hitCityApi(String stateCode, final Context context, final StateAndCityListCallBack stateAndCityListCallBack) {
        final LoadingDialog loadingDialog = new LoadingDialog(context);

        Json json = new Json();
        json.addString(P.state, stateCode);

        RequestModel requestModel = RequestModel.newRequestModel("get_city_id");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(context, "Something went wrong.");
                    }
                })
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (isLoading)
                            loadingDialog.show("loading city...");
                        else
                            loadingDialog.hide();
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1) {
                            JsonList jsonList = json.getJsonList(P.data);
                            CommonListHolder commonListHolder = new CommonListHolder();
                            commonListHolder.makeCityList(jsonList);
                            stateAndCityListCallBack.listIsPrepared();

                        } else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitCityApi");
    }

    public interface StateAndCityListCallBack
    {
        void listIsPrepared();
    }
}
