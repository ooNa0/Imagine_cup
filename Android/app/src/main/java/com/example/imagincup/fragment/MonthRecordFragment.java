package com.example.imagincup.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.db.williamchart.Tooltip;

import com.db.williamchart.view.LineChartView;
import com.example.imagincup.Constants;
import com.example.imagincup.R;
import com.example.imagincup.back.DTO.DTOPerson;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MonthRecordFragment extends Fragment {

    private LineChart lineChart;

    private DTOPerson dtoPerson;
    private Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();
        dtoPerson = (DTOPerson) bundle.getSerializable(Constants.DATABASE_PERSON_TABLENAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_month, container, false);

        lineChart = view.findViewById(R.id.monthly_score);
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            float val = (float) (Math.random() * 10);
            values.add(new Entry(i, val));
        }

//        final ArrayList<String> xVal = new ArrayList<>();
//        xVal.add("last month");
//        xVal.add("this month");

//        XAxis xl1 = lineChart.getXAxis();
//        xl1.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xl1.setDrawAxisLine(false);
//        xl1.setDrawGridLinesBehindData(false);
//        xl1.setDrawGridLines(false);
//        xl1.setTextSize(15f);
//        xl1.setTextColor(Color.parseColor("#445A55"));
//        IndexAxisValueFormatter xaxisFormatter = new IndexAxisValueFormatter(xVal);
//        xl1.setValueFormatter(xaxisFormatter);
//        xl1.setGranularity(1);

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
                new LinearGradient(0, 10, 10, 10, Color.parseColor("#00cfcce3"), Color.parseColor("#99C5C4"), Shader.TileMode.CLAMP));
        //barChart.getMeasuredWidth()

        lineChart.setViewPortOffsets(150, 10, 150, 0);

        //확대
        lineChart.setTouchEnabled(false);

        // xaxis
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        // 위아래 선이랑 숫자 안보이게 하는 것
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setDrawGridLines(false);
        // 설명 숨기기
        lineChart.getDescription().setEnabled(false);
        // 아래 색상 나타내는 거 숨기기
        lineChart.getLegend().setEnabled(false);

        lineChart.animateXY(0, 1000);
        // set data
        lineChart.setData(data);

        HorizontalBarChart barChart = (HorizontalBarChart)view.findViewById(R.id.weekly_score);

        // Create bars
        ArrayList<BarEntry> points = new ArrayList<>();
        points.add(new BarEntry(0f, 80));
        points.add(new BarEntry(1f, 50));
        points.add(new BarEntry(2f, 20));
        points.add(new BarEntry(3f, 60));
        points.add(new BarEntry(4f, 80));
        points.add(new BarEntry(5f, 100));
        points.add(new BarEntry(6f, 50));

        // Create a data set
        BarDataSet dataSet = new BarDataSet(points, "weekly");
        // 막대 위의 값

        dataSet.setDrawIcons(false);
        dataSet.setDrawValues(true);

        BarData barData = new BarData(dataSet);
        // 바 너비
        barData.setBarWidth(0.35f);
        // 테이터 형식 백분율로
        //barData.setValueFormatter(new PercentFormatter());

        // barData.setDrawValues(true);
        // barChart.setDrawValueAboveBar(true);

        // 데이터 적용
        barChart.setData(barData);

        // 레이블
        final ArrayList<String> xVals = new ArrayList<>();
        xVals.add("mon");
        xVals.add("tue");
        xVals.add("wed");
        xVals.add("thu");
        xVals.add("fri");
        xVals.add("sat");
        xVals.add("sun");

        // 안차지하는 부분 회색, true도 고려해볼만함
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

        // 최댓값
        barChart.getAxisLeft().setAxisMaximum(100);

        // 왼에서 오로 애니메이션
        barChart.animateXY(1000, 1000);
        // 막대안에 값 표시
        barChart.setDrawValueAboveBar(true);

        // 위아래 선이랑 숫자 안보이게 하는 것
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setDrawGridLines(false);
        // 설명 숨기기
        barChart.getDescription().setEnabled(false);
        // 아래 색상 나타내는 거 숨기기
        barChart.getLegend().setEnabled(false);


        // Design
        RoundedHorizontalBarChartRenderer roundedBarChartRenderer= new RoundedHorizontalBarChartRenderer(barChart , barChart.getAnimator(), barChart.getViewPortHandler());
        roundedBarChartRenderer.setmRadius(20f);
        barChart.setRenderer(roundedBarChartRenderer);

        barData.setValueTextSize(140f);

        barChart.getRenderer().getPaintRender().setShader(
                new LinearGradient(0, 0, 1000, 1000, Color.parseColor("#88BEB7"), Color.parseColor("#39655B"), Shader.TileMode.CLAMP));
        //barChart.getMeasuredWidth()
        // hor 에서는 안댐
        //dataSet.setGradientColor(Color.parseColor("#00cfcce3"),Color.parseColor("#CCCCCC"));

        barChart.setTouchEnabled(false); //확대 X

        barChart.invalidate();
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
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