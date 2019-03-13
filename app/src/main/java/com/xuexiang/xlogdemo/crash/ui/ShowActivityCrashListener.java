package com.xuexiang.xlogdemo.crash.ui;

import android.content.Context;
import android.content.Intent;

import com.xuexiang.xlog.crash.ICrashHandler;
import com.xuexiang.xlog.crash.OnCrashListener;
import com.xuexiang.xlogdemo.R;

/**
 * @author xuexiang
 * @since 2019/3/13 下午11:29
 */
public class ShowActivityCrashListener implements OnCrashListener {

    /**
     * 主题颜色
     */
    private int mThemeId;


    public ShowActivityCrashListener() {
        this(R.style.SpiderManTheme_Light);
    }

    public ShowActivityCrashListener(int themeId) {
        mThemeId = themeId;
    }


    /**
     * 发生崩溃
     *
     * @param context
     * @param crashHandler
     * @param throwable
     */
    @Override
    public void onCrash(Context context, ICrashHandler crashHandler, Throwable throwable) {
        CrashModel crashModel = CrashUtils.parseCrash(context, throwable);

        Intent intent = new Intent(context, CrashActivity.class);
        intent.putExtra(CrashActivity.KEY_CRASH_MODEL, crashModel);
        intent.putExtra(CrashActivity.KEY_THEME_ID, mThemeId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        //禁止重启
        crashHandler.setIsHandledCrash(true);
        crashHandler.setIsNeedReopen(false);
    }


}
