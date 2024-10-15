package com.smart.xapp.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

//未完善之处1、文件动态创建，2、动态申请存储权限
public class DynamicLoadUtils {
    private Context context;
    private DexClassLoader dexClassLoader;
    public final static String CachePath = android.os.Environment.getExternalStorageDirectory().toString() + "/aaa/";

    public DynamicLoadUtils(Context context) {
        this.context = context;
        runApkMethod("app-debug.apk", "com.eoffcn.commontest.JustForTest");
    }

    public void runApkMethod(String resName, String className) {
        try {
            File resFile = getResFile(resName);
            getClassLoader(context, resFile);
            runClassMethod(context, className);
        } catch (Exception e) {
            Log.e("wechatmomentstat", "exception", e);
        }
    }

    private void runClassMethod(Context context, String className) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class clazz = dexClassLoader.loadClass(className);
        Object helloClassInstance = clazz.newInstance();

        // 反射调用 无参函数 hello 方法
        Method helloMethod = clazz.getDeclaredMethod("hello");
        helloMethod.invoke(helloClassInstance);

        // 反射调用 有参函数showLog 方法
        Method showLogMethod = clazz.getDeclaredMethod("showLog", String.class, String.class);
        String logTag = "JarUtil";
        String logMessage = "Hello from showLog!";
        showLogMethod.invoke(helloClassInstance, logTag, logMessage);

        // 反射调用 有参函数sendMsg 方法
        Method sendMsgMethod = clazz.getDeclaredMethod("sendMsg", Context.class, String.class);
        //Context context = this; // 使用应用上下文
        String message = "Hello from sendMsg!";
        sendMsgMethod.invoke(helloClassInstance, context, message);
    }


    private DexClassLoader getClassLoader(Context context, File outputAPKFile) {
        if (dexClassLoader == null) {
            dexClassLoader = new DexClassLoader(
                    outputAPKFile.getAbsolutePath(),
                    context.getDir("outdex", 0).getAbsolutePath(),
                    null,
                    ClassLoader.getSystemClassLoader());
        }
        return dexClassLoader;
    }

    private File getResFile(String resName) {
        String filePath = CachePath + resName;
        File outputAPKFile = new File(filePath);
        boolean isExists = outputAPKFile.exists();
        if (!isExists) {
            copyAPKFromAssets(context, resName);
        }
        return outputAPKFile;
    }

    private void copyAPKFromAssets(Context context, String apkName) {
        InputStream assetInputStream = null;
        File outputAPKFile = new File(CachePath + apkName);
        if (outputAPKFile.exists())
            outputAPKFile.delete();
        byte[] buf = new byte[1024];
        try {
            outputAPKFile.createNewFile();
            assetInputStream = context.getAssets().open(apkName);
            FileOutputStream outAPKStream = new FileOutputStream(outputAPKFile);
            int read;
            while ((read = assetInputStream.read(buf)) != -1) {
                outAPKStream.write(buf, 0, read);
            }
            assetInputStream.close();
            outAPKStream.close();
        } catch (Exception e) {
            Log.e("wechatmomentstat", "exception", e);
        }
    }


}
