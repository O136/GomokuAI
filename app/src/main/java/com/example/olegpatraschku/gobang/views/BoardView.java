package com.example.olegpatraschku.gobang.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.example.olegpatraschku.gobang.R;
import com.example.olegpatraschku.gobang.models.Constants;

/**
 * Created by Oleg Patraschku on 5/5/2016.
 */

public class BoardView extends LinearLayout implements View.OnClickListener {
    public TableLayoutView tableLayout;
    TableLayoutViewListener tableLayoutViewListener;

    public BoardView(Context context) {
        super(context);
        init(context, null);
    }


    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public BoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void setTableLayoutViewListener(TableLayoutViewListener tableLayoutViewListener) {
        this.tableLayoutViewListener = tableLayoutViewListener;
    }

    private void init(final Context context, AttributeSet attrs) {
        inflate(context, R.layout.table_view, this);
        tableLayout = (TableLayoutView) findViewById(R.id.table_view);
        //setWillNotDraw(false);
        setBackgroundColor(getResources().getColor(R.color.colorBoardView, null));

        TableRow row;
        //TODO LIMIT needs to be set by user
        final int LIMIT = Constants.SMALL_BOARD;

        for (int i = 0; i < LIMIT; ++i) {
            row = new TableRow(context);
            for (int j = 0; j < LIMIT; ++j) {
                FigureView f = new FigureView(context, attrs);
                f.setCol(j);
                f.setRow(i);
                f.setOnClickListener(this);
                row.addView(f);
            }
            tableLayout.addView(row);
        }
    }

    @Override
    public void onClick(View v) {
        //TODO maybe check what type is v?;

        if (tableLayoutViewListener != null)
            tableLayoutViewListener.FigureViewSelected((FigureView) v);
    }

}