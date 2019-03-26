package com.breeziness.timetable.UI.selectitemview;

import android.content.Context;
import android.util.AttributeSet;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class TermPicker extends WheelSelectView<String>{

    private int selectedTerm = 0;
    private List<String> termList = new ArrayList<>();//本人年级集合
    private OnTermSelectedListener onTermSelectedListener;
    public TermPicker(Context context) {
        this(context,null);
    }

    public TermPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TermPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSelectedTerm(selectedTerm);
        setOnSelectedChangeListener(new OnSelectedChangeListener() {
            @Override
            public void OnSelectedChange(String item, int position) {
                onTermSelectedListener.onTermSelected(item,position);
            }
        });
    }

    public List<String> getTermList() {
        return termList;
    }

    public void setTermList(List<String> termList) {
        this.termList = termList;
        setDataList(termList);
    }
    public int getSelectedTerm() {
        return selectedTerm;
    }

    public void setSelectedTerm(int selectedTerm) {
        this.selectedTerm = selectedTerm;
        setCurrentPosition(selectedTerm);
    }



    public void setOnTermSelectedListener(OnTermSelectedListener onTermSelectedListener) {
        this.onTermSelectedListener = onTermSelectedListener;
    }
    public interface OnTermSelectedListener{
        void onTermSelected(String item,int position);
    }
}
