/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.xlog.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xuexiang.xlog.annotation.LogLevel;
import com.xuexiang.xlog.annotation.LogSegment;


/**
 * 打印相关.
 */
public class PrinterUtils {

    /** 控制台打印的内容格式. */
    private static final String PRINT_CONSOLE_FORMAT =
            "------------------------------ (%1$s:%2$d)#%3$s Thread:%4$s" + Utils.LINE_BREAK
                    + "%5$s" + Utils.LINE_BREAK + " ";
    /** 文件中保存的内容格式. */
    private static final String PRINT_FILE_FORMAT =
            "[%1$s  %2$s  %3$s:%4$d  Thread: %5$s]" + Utils.LINE_BREAK
                    + "%6$s" + Utils.LINE_BREAK + Utils.LINE_BREAK;

    private PrinterUtils() {
    }

    /**
     * 日志打印输出到控制台.
     *
     * @param level   级别
     * @param tag     标签
     * @param message 信息
     */
    public static void printConsole(@LogLevel String level, @NonNull String tag,
                                    @NonNull String message) {
        Utils.log(level, tag, message);
    }

    /**
     * 日志打印输出到文件.
     *
     * @param context Context
     * @param message 信息
     */
    public static void printFile(@NonNull Context context, @NonNull String logDir, String logPrefix,
                                 @LogSegment int logSegment, @TimeUtils.ZoneOffset long zoneOffset,
                                 @NonNull String message) {
        String dirPath = FileUtils.getDiskCacheDir(context, logDir);
        String fileName = Utils.getLogFileName(logPrefix, logSegment, zoneOffset);
        Utils.write(context, dirPath, fileName, message);
    }

    /**
     * 装饰打印到控制台的信息.
     *
     * @param message 信息
     * @param element 对战元素
     * @return 装饰后的信息
     */
    public static String decorateMsgForConsole(@NonNull String message,
                                               @NonNull StackTraceElement element) {
        String methodName = element.getMethodName();
        int lineNumber = element.getLineNumber();
        String fileName = element.getFileName();
        String threadName = Thread.currentThread().getName();
        return String.format(PRINT_CONSOLE_FORMAT, fileName, lineNumber, methodName, threadName,
                message);
    }

    /**
     * 装饰打印到文件的信息.
     *
     * @param level      级别
     * @param message    信息
     * @param element    堆栈元素
     * @param zoneOffset 时区偏移
     * @param timeFmt    时间格式
     * @return 装饰后的信息
     */
    public static String decorateMsgForFile(@LogLevel String level, @NonNull String message,
                                            @NonNull StackTraceElement element, @TimeUtils.ZoneOffset long zoneOffset,
                                            @NonNull String timeFmt) {
        String time = TimeUtils.getCurTime(zoneOffset, timeFmt);
        String fileName = element.getFileName();
        int lineNum = element.getLineNumber();
        String threadName = Thread.currentThread().getName();
        return String.format(PRINT_FILE_FORMAT, time, level, fileName, lineNum, threadName,
                message);
    }
}