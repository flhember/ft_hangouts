package com.example.ft_hangouts;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.Date;

public final class My_app extends Application implements Application.ActivityLifecycleCallbacks
{
    private static final int APP_VISIBLE = 0;
    private static final int APP_HIDDEN = 1;
    private static final int VISIBILITY_DELAY_IN_MS = 300;
    private Handler visibilityHandler;


    private static final class VisibilityCallback implements Handler.Callback
    {
        private Context context;
        private int previousVisibility;
        private Date date;


        public VisibilityCallback(Context context)
        {
            this.context = context;
            previousVisibility = My_app.APP_VISIBLE;
        }

        @Override
        public boolean handleMessage(Message msg)
        {
            if (previousVisibility != msg.what)
            {
                previousVisibility = msg.what;
                if (msg.what == My_app.APP_VISIBLE)
                {
                    Toast.makeText(context, date.getHours() + ":" + date.getMinutes(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    date = new Date();
                }
            }
            return true;
        }

    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        visibilityHandler = new Handler(new VisibilityCallback(getApplicationContext()));
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {

    }

    @Override
    public void onActivityStarted(Activity activity)
    {

    }

    @Override
    public void onActivityResumed(Activity activity)
    {
        visibilityHandler.removeMessages(My_app.APP_HIDDEN);
        visibilityHandler.sendEmptyMessageDelayed(My_app.APP_VISIBLE, My_app.VISIBILITY_DELAY_IN_MS);
    }

    @Override
    public void onActivityPaused(Activity activity)
    {
        visibilityHandler.removeMessages(My_app.APP_VISIBLE);
        visibilityHandler.sendEmptyMessageDelayed(My_app.APP_HIDDEN, My_app.VISIBILITY_DELAY_IN_MS);
    }

    @Override
    public void onActivityStopped(Activity activity)
    {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState)
    {

    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {

    }

}