package com.youyou.xiaofeibao.version2.xunfei;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

/**
 * 作者：young on 2017/5/31 15:50
 */

public class VoiceUtils {


    private static VoiceUtils mVoice;


    public static VoiceUtils getInstance(){
        synchronized (VoiceUtils.class){
            if(null==mVoice){
                mVoice=new VoiceUtils();
            }
            return mVoice;
        }
    }

    // 语音合成对象
    private SpeechSynthesizer mTts;

    // 默认发音人
    private String voicer = "xiaoyan";

    private InitListener Listener;

    private SynthesizerListener mSynthesizerListener;

    private String mString="智慧返收到4988.12元";

    public void initmTts(Context context, final String msg){

        Log.i("wy", "InitListener init() code = " + context.toString());
        if(null==Listener){
            Listener=new InitListener() {
                @Override
                public void onInit(int code) {
                    Log.i("wy", "InitListener init() code = " + code);
                    if (code != ErrorCode.SUCCESS) {
                        Log.i("wy", "初始化失败,错误码 " + code);
                    } else {
                        // 初始化成功，之后可以调用startSpeaking方法
                        // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                        // 正确的做法是将onCreate中的startSpeaking调用移至这里
                        Log.i("wy", "初始化成功 " + code);
                        mTts.startSpeaking(msg,mSynthesizerListener);
                    }
                }
            };
        }

        if(null==mSynthesizerListener){
            mSynthesizerListener= new SynthesizerListener() {
                @Override
                public void onSpeakBegin() {
                    Log.i("wy", "onSpeakBegin ");
                }

                @Override
                public void onBufferProgress(int i, int i1, int i2, String s) {
                    Log.i("wy", "onBufferProgress ");
                }

                @Override
                public void onSpeakPaused() {
                    Log.i("wy", "onSpeakPaused ");
                }

                @Override
                public void onSpeakResumed() {
                    Log.i("wy", "onSpeakResumed ");
                }

                @Override
                public void onSpeakProgress(int i, int i1, int i2) {
                    Log.i("wy", "onSpeakProgress ");
                }

                @Override
                public void onCompleted(SpeechError speechError) {
                    Log.i("wy", "onCompleted ");
                }

                @Override
                public void onEvent(int i, int i1, int i2, Bundle bundle) {
                    Log.i("wy", "onEvent ");
                }
            };
        }

        if(null==mTts){
            mTts=SpeechSynthesizer.createSynthesizer(context, Listener);
            mTts.setParameter(SpeechConstant.VOICE_NAME,voicer);
            mTts.setParameter(SpeechConstant.SPEED,"50");
            mTts.setParameter(SpeechConstant.VOLUME,"80");
            mTts.setParameter(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_CLOUD);
        }else {
            Log.i("wy", "InitListener init() code = " + mTts.toString());
            mTts.startSpeaking(msg,mSynthesizerListener);
        }
    }

}
