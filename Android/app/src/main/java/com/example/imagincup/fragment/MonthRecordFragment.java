package com.example.imagincup.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.imagincup.Constants;
import com.example.imagincup.R;
import com.example.imagincup.back.DTO.DTOPerson;
import com.example.imagincup.back.task.record.SelectRecordMonthThread;
import com.example.imagincup.back.task.record.SelectRecordWeekThread;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MonthRecordFragment extends Fragment {

    private LineChart lineChart;
    private TextView percent;
    private TextView arrow;

    private Bundle bundle;

    private DTOPerson dtoPerson;
    private String personID;
    private String selectYear;
    private String selectMonth;
    private String selectDay;

    private String startDate;
    private String midDate;
    private String endDate;
    private String weekDate;

    private Float lastMonth;
    private Float thisMonth;
    private Float interval;

    private Date date;

    private SelectRecordMonthThread recordThisMonthThread;
    private SelectRecordMonthThread recordLastMonthThread;
    private SelectRecordWeekThread recordWeekThread;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMMM-dd", Locale.US);
    private SimpleDateFormat dateFormatInteger = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dateFormatNotDay = new SimpleDateFormat("yyyyMM");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            dtoPerson = (DTOPerson) getArguments().getSerializable(Constants.DATABASE_PERSON_TABLENAME);
            personID = String.valueOf(dtoPerson.getPersonId());
            selectYear = getArguments().getString("Year");
            selectMonth = getArguments().getString("Month");
            selectDay = getArguments().getString("Day");

            setDate();

            recordThisMonthThread = new SelectRecordMonthThread(personID, midDate, endDate);
            recordLastMonthThread = new SelectRecordMonthThread(personID, startDate, midDate);
            recordWeekThread = new SelectRecordWeekThread(personID, endDate, weekDate);
            recordThisMonthThread.start();
            recordLastMonthThread.start();
            recordWeekThread.start();
            try {
                recordThisMonthThread.join();
                recordLastMonthThread.join();
                recordWeekThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thisMonth = recordThisMonthThread.getResultAverage();
            lastMonth = recordLastMonthThread.getResultAverage();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_month, container, false);

        percent = view.findViewById(R.id.percent);
        arrow = view.findViewById(R.id.arrow);
        lineChart = view.findViewById(R.id.monthly_score);
        ArrayList<Entry> values = new ArrayList<>();

        values.add(new Entry(0, lastMonth));
        values.add(new Entry(1, thisMonth));

        interval = thisMonth - lastMonth;
        if(interval > 0){
            arrow.setText("↑");
            arrow.setTextColor(Color.parseColor("#fa3423"));
            percent.setTextColor(Color.parseColor("#fa3423"));
        }
        else{
            arrow.setText("↓");
            arrow.setTextColor(Color.parseColor("#3548e7"));
            percent.setTextColor(Color.parseColor("#3548e7"));
        }
        percent.setText(String.valueOf(interval));

        LineDataSet set1 = new LineDataSet(values, "monthly");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // black lines and points
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);

        set1.setDrawFilled(true);
        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.gradient);
        set1.setFillDrawable(drawable);

        lineChart.getRenderer().getPaintRender().setShader(
                new LinearGradient(0, 10, 10, 10, Color.parseColor("#FFD279"), Color.parseColor("#5B2A0C"), Shader.TileMode.CLAMP));

        lineChart.setViewPortOffsets(150, 10, 150, 0);

        lineChart.setTouchEnabled(false);

        // xaxis
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);

        lineChart.animateXY(0, 1000);
        // set data
        lineChart.setData(data);

        HorizontalBarChart barChart = (HorizontalBarChart)view.findViewById(R.id.weekly_score);

        // Create bars
        ArrayList<BarEntry> points = new ArrayList<>();
        points.add(new BarEntry(0f, recordWeekThread.getScoreweek2())); //mon
        points.add(new BarEntry(1f, recordWeekThread.getScoreweek3())); // tu
        points.add(new BarEntry(2f, recordWeekThread.getScoreweek4())); // wed
        points.add(new BarEntry(3f, recordWeekThread.getScoreweek5())); // thu
        points.add(new BarEntry(4f, recordWeekThread.getScoreweek6())); // 금
        points.add(new BarEntry(5f, recordWeekThread.getScoreweek7())); // 토
        points.add(new BarEntry(6f, recordWeekThread.getScoreweek1())); // 일

        // Create a data set
        BarDataSet dataSet = new BarDataSet(points, "weekly");

        dataSet.setDrawIcons(false);
        dataSet.setDrawValues(false);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.35f);

        barChart.setData(barData);

        final ArrayList<String> xVals = new ArrayList<>();
        xVals.add("mon");
        xVals.add("tue");
        xVals.add("wed");
        xVals.add("thu");
        xVals.add("fri");
        xVals.add("sat");
        xVals.add("sun");

        barChart.setDrawBarShadow(false);

        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
        xl.setTextSize(15f);
        xl.setTextColor(Color.parseColor("#CCCCCC"));
        IndexAxisValueFormatter xaxisFormatter1 = new IndexAxisValueFormatter(xVals);
        xl.setValueFormatter(xaxisFormatter1);
        xl.setGranularity(1);

        barChart.getAxisLeft().setAxisMaximum(50);

        barChart.animateXY(1000, 1000);
        barChart.setDrawValueAboveBar(false);

        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);


        // Design
        RoundedHorizontalBarChartRenderer roundedBarChartRenderer= new RoundedHorizontalBarChartRenderer(barChart , barChart.getAnimator(), barChart.getViewPortHandler());
        roundedBarChartRenderer.setmRadius(20f);
        barChart.setRenderer(roundedBarChartRenderer);

        barData.setValueTextSize(140f);

        barChart.getRenderer().getPaintRender().setShader(
                new LinearGradient(0, 0, 1000, 1000, Color.parseColor("#F2B743"), Color.parseColor("#5B2A0C"), Shader.TileMode.CLAMP));

        barChart.setTouchEnabled(false);

        barChart.invalidate();
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void setDate(){
        try {
            Calendar cal = Calendar.getInstance();
            date = dateFormat.parse(selectYear + "-" +selectMonth + "-" + selectDay);

            endDate = String.valueOf(dateFormatInteger.format(dateFormat.parse(selectYear + "-" +selectMonth + "-" + selectDay))); // 지금 날짜
            midDate = String.valueOf(dateFormatInteger.format(dateFormat.parse(selectYear + "-" +selectMonth + "-" + "01"))); // 지금 날짜에서 01일로 세팅값

            cal.setTime(date);
            cal.add(Calendar.DATE, -7);
            weekDate = dateFormatInteger.format(cal.getTime());

            date = dateFormat.parse(selectYear + "-" +selectMonth + "-" + "01");

            cal.setTime(date);
            midDate = dateFormatInteger.format(cal.getTime());
            cal.add(Calendar.MONTH, -1);
            startDate = dateFormatInteger.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

class RoundedHorizontalBarChartRenderer extends HorizontalBarChartRenderer {
    public RoundedHorizontalBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }
    private float mRadius=5f;

    public void setmRadius(float mRadius) {
        this.mRadius = mRadius;
    }

    @Override
    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        mShadowPaint.setColor(dataSet.getBarShadowColor());

        float phaseX = mAnimator.getPhaseX();
        float phaseY = mAnimator.getPhaseY();

        // initialize the buffer
        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setDataSet(index);
        buffer.setBarWidth(mChart.getBarData().getBarWidth());
        buffer.setInverted(mChart.isInverted(dataSet.getAxisDependency()));

        buffer.feed(dataSet);

        trans.pointValuesToPixel(buffer.buffer);

        // if multiple colors
        if (dataSet.getColors().size() > 1) {

            for (int j = 0; j < buffer.size(); j += 4) {

                if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]))
                    continue;

                if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                    break;

                if (mChart.isDrawBarShadowEnabled()) {
                    if (mRadius > 0)
                        c.drawRoundRect(new RectF(buffer.buffer[j], mViewPortHandler.contentTop(),
                                buffer.buffer[j + 2],
                                mViewPortHandler.contentBottom()), mRadius, mRadius, mShadowPaint);
                    else
                        c.drawRect(buffer.buffer[j], mViewPortHandler.contentTop(),
                                buffer.buffer[j + 2],
                                mViewPortHandler.contentBottom(), mShadowPaint);
                }

                // Set the color for the currently drawn value. If the index
                // is
                // out of bounds, reuse colors.
                mRenderPaint.setColor(dataSet.getColor(j / 4));
                if (mRadius > 0)
                    c.drawRoundRect(new RectF(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                            buffer.buffer[j + 3]), mRadius, mRadius, mRenderPaint);
                else
                    c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                            buffer.buffer[j + 3], mRenderPaint);
            }
        } else {

            mRenderPaint.setColor(dataSet.getColor());

            for (int j = 0; j < buffer.size(); j += 4) {

                if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]))
                    continue;

                if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                    break;

                if (mChart.isDrawBarShadowEnabled()) {
                    if (mRadius > 0)
                        c.drawRoundRect(new RectF(buffer.buffer[j], mViewPortHandler.contentTop(),
                                buffer.buffer[j + 2],
                                mViewPortHandler.contentBottom()), mRadius, mRadius, mShadowPaint);
                    else
                        c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                                buffer.buffer[j + 3], mRenderPaint);
                }

                if (mRadius > 0)
                    c.drawRoundRect(new RectF(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                            buffer.buffer[j + 3]), mRadius, mRadius, mRenderPaint);
                else
                    c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                            buffer.buffer[j + 3], mRenderPaint);
            }
        }
    }
}