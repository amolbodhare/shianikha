package com.nikha.shianikha;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class JobSchedulerHelper extends JobService
{
    MediaPlayer mPlayer;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ali_ke_sath);
        if (jobParameters.getJobId() == 314749)
            try
            {
                boolean foreground=new ForegroundCheckTask().execute(getApplicationContext()).get();

                if(foreground==true)
                {
                    mPlayer.start();
                    Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
                    restartJobScheduler(jobParameters);
                }
                else {
                    //restartJobScheduler(jobParameters);
                    Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
                }
                /*else {
                    restartJobScheduler(jobParameters);
                }
*/            }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


    Handler handler = new Handler();
    Runnable runnable;

    private void restartJobScheduler(final JobParameters jobParameters) {
        if (runnable != null)
            handler.removeCallbacks(runnable);
        runnable = new Runnable() {
            @Override
            public void run() {
                jobFinished(jobParameters, false);
                ComponentName componentName = new ComponentName(JobSchedulerHelper.this, JobSchedulerHelper.class);
                JobInfo info = new JobInfo.Builder(314749, componentName)
                        //.setRequiresCharging(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPersisted(true)
                        //.setPeriodic(321)
                        .build();

                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                scheduler.cancelAll();

                int resultCode = scheduler.schedule(info);
                if (resultCode == JobScheduler.RESULT_SUCCESS) {
                    Log.e("startJobScheduler", "Job scheduled");
                } else {
                    Log.e("startJobScheduler", "Job scheduling failed");
                }
            }
        };
        handler.postDelayed(runnable, 300);
    }

    class ForegroundCheckTask extends AsyncTask<Context,Void,Boolean>
    {

        @Override
        protected Boolean doInBackground(Context... contexts) {
            final Context context=contexts[0].getApplicationContext();
            return  isAppOnForeground(context);
        }

        private boolean isAppOnForeground(Context context)
        {
            boolean bool = false;
            ActivityManager activityManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses =activityManager.getRunningAppProcesses();
            if(appProcesses==null)
            {
                bool = false;
            }
            final  String packageName=context.getPackageName();
            for(ActivityManager.RunningAppProcessInfo appProcess:appProcesses)
            {
                if(appProcess.importance== ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&appProcess.processName.equals(packageName))
                {
                    bool = true;
                }
            }
            Log.e("boolIs",bool+"");
            return bool;
        }
    }
}
