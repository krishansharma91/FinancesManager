package com.liangge.financesmanager.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.liangge.financesmanager.exception.ChartInfoException;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义折线图
 * liangge0218
 */
public class CustomLineChart extends View{
    private Paint paint = new Paint();
    /*当天星期数 0-6*/
    private int dayOfWeek;
    /*图表展示数据*/
    private float[] costArray = {
            10.5f, 89.1f, 67.3f, 111f, 22f
    };
    private static final float costMax = 90f;
    private static final int showWeekNum = 7;
    private static final String[] weekArray = {
            "周日","周一","周二","周三","周四","周五","周六",
            "周日","周一","周二","周三","周四","周五","周六"
    };
    private static final String[] costVertical = {
            "更多","90","60","30","0"
    };

    public CustomLineChart(Context context) {
        super(context);
    }
    public CustomLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public float[] getCostArray() {
        return costArray;
    }

    public void setCostArray(float[] costArray) {
        this.costArray = costArray;
    }



    /**
     * 设置图表数据信息
     * @param dayOfWeek 0-6星期数
     * @param costArray 小于7个长度的数据数组
     */
    public void setChartInfo(int dayOfWeek, float[] costArray) throws ChartInfoException{
        if(dayOfWeek < 0 || dayOfWeek > 6){
            throw new ChartInfoException("dayOfWeek值错误");
        }
        this.dayOfWeek = dayOfWeek;
        this.costArray = costArray;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint textPaint = this.getNormalTextPaint();      /*文字画笔*/
        /*计算横坐标偏移量*/
        float weekIntelval = getWidth() / (showWeekNum + 1);        /*周单位显示间隔*/
        String[] weekList = this.getWeekArray();
        float textWidth = textPaint.measureText(weekList[0]);     /*字体宽度*/
        float weekOffset = weekIntelval - (textWidth / 2);                /*字体偏移量*/
        /*计算纵坐标偏移量*/
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float descent = fontMetrics.descent;
        float textHeight = (float)Math.ceil(fontMetrics.descent - fontMetrics.ascent);      /*字体高度*/
        float costIntelval = (getHeight() - textHeight) / costVertical.length;          /*Y坐标系名称间隔*/

        /*插入横坐标值*/
        for(int i = 0;i < weekList.length;i++){
            Paint paint = (i == showWeekNum / 2) ? this.getTodayTextPaint() : this.getNormalTextPaint();
            canvas.drawText(weekList[i], i * weekIntelval + weekOffset, getHeight() - descent, paint);
        }
        /*插入纵坐标值*/
        for(int i = 0;i < costVertical.length;i++){
            String cost = costVertical[i];
            float costWidth = textPaint.measureText(cost);
            canvas.drawText(cost, (weekOffset - costWidth) / 2, costIntelval * (i + 1) - descent, textPaint);
        }
        /*绘制已消费的动态数据*/
        List<Map<String, Object>> circleList = new ArrayList<Map<String, Object>>();    /*圆点列表*/
        float coordiate_zero = costIntelval * costVertical.length - textHeight / 2;    /*0坐标Y轴*/
        float coordiate_full = costIntelval - textHeight / 2;                          /*Y轴顶值*/
        float precoordiateY = -1;
        int costLength = costArray.length;
        Paint costLinePaint = this.getCostLinePaint();
        for(int i = 0;i < costLength;i++){
            float cost = costArray[i];

            /*如果大于消费折线最大值，则直接指向顶值*/
            float coordiateY = coordiate_full;
            if(cost <= costMax){
                float costMaxCoordiateY = costIntelval * 2 - textHeight / 2;    /*消费金额最大值的Y坐标*/
                float costMaxHeight = coordiate_zero - costMaxCoordiateY;           /*获取0-costMax占有的纵向空间*/
                float radio = costMaxHeight * (cost / costMax);         /*获取在新坐标系中应占有的纵向空间*/
                coordiateY = costMaxCoordiateY + (costMaxHeight - radio);
            }

            float coordiateX = weekIntelval * (i + 1);
            /*加入圆点列表*/
            Map<String, Object> circleMap = new HashMap<String, Object>();
            circleMap.put("x", coordiateX);
            circleMap.put("y", coordiateY);
            circleMap.put("isCost", true);
            circleList.add(circleMap);
//            canvas.drawCircle(coordiateX, coordiateY, 5, costLinePaint);

            if(precoordiateY != -1){
                canvas.drawLine(weekIntelval * i, precoordiateY, coordiateX, coordiateY, costLinePaint);
            }
            precoordiateY = coordiateY;
        }
        /*绘制未消费的动态数据*/
        if(costLength < showWeekNum){
            Paint noCostLinePaint = this.getNoCostLinePaint();
            for(int i = costLength,len = showWeekNum;i < len;i++){
                float coordiateX = weekIntelval * (i + 1);
                /*加入圆点列表*/
                Map<String, Object> circleMap = new HashMap<String, Object>();
                circleMap.put("x", coordiateX);
                circleMap.put("y", coordiate_zero);
                circleMap.put("isCost", false);
                circleList.add(circleMap);
//                canvas.drawCircle(coordiateX, coordiate_zero, 5, noCostLinePaint);
                canvas.drawLine(weekIntelval * i, precoordiateY, coordiateX, coordiate_zero, noCostLinePaint);
                precoordiateY = coordiate_zero;
            }
        }
        /*画折线图实心圆点*/
        for(Map<String, Object> circleMap : circleList){
            Paint paint = (Boolean)circleMap.get("isCost") ? this.getCostLinePaint() : this.getNoCostLinePaint();
            canvas.drawCircle((Float)circleMap.get("x"), (Float)circleMap.get("y"), 5, paint);
        }

    }
    /**
     * 获取今日着重标识的画笔
     * @return
     */
    private Paint getTodayTextPaint(){
        paint.reset();
        paint.setTextSize(22);
        paint.setColor(Color.GREEN);
        return paint;
    }
    /**
     * 获取普通文字画笔
     * @return
     */
    private Paint getNormalTextPaint(){
        paint.reset();
        paint.setTextSize(22);
        paint.setColor(Color.GRAY);
        return paint;
    }

    /**
     * 获取消费折线画笔
     * @return
     */
    private Paint getCostLinePaint(){
        paint.reset();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        return paint;
    }

    /**
     * 获取未消费折线画笔
     * @return
     */
    private Paint getNoCostLinePaint(){
        paint.reset();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(3);
        return paint;
    }
    /**
     * 根据dayOfWeek获取展示的星期
     * @return
     */
    private String[] getWeekArray(){
        int middle = showWeekNum/2;
        int center = dayOfWeek < middle ? dayOfWeek + showWeekNum : dayOfWeek;
        return ArrayUtils.subarray(weekArray, center - middle, center + middle + 1);
    }
}
