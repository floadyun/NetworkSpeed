package com.base.lib.util;


/**
 * Created by yixiaofei on 2017/3/24 0024.
 */

public class TimerThread implements Runnable {

    public interface OnTimerListerner{
        void onTimeProgress(int time);
        void onTimeFinish();
    }
    public boolean isRun = true;

    private int time = 0;
    //间隔时间
    private int intervalTime;

    private OnTimerListerner timerListerner;

    public TimerThread(int maxTime,int intervalTime) {
        this.time = maxTime;
        this.intervalTime = intervalTime;
    }

    public void setTimerListerner(OnTimerListerner timerListerner) {
        this.timerListerner = timerListerner;
    }
    @Override
    public void run() {
        while (isRun){
            try{
                Thread.sleep(intervalTime);
                if(time>0){
                    time--;
                    if(timerListerner!=null){
                        timerListerner.onTimeProgress(time);
                    }
                }else{
                    if(timerListerner!=null){
                        isRun = false;
                        timerListerner.onTimeFinish();
                    }
                }
            }catch(Exception e){

            }
        }
    }

    public void stop(){
        isRun = false;
    }
}
