package com.example.olegpatraschku.gobang.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.olegpatraschku.gobang.R;
import com.example.olegpatraschku.gobang.models.Direction;

/**
 * Created by Oleg Patraschku on 5/5/2016.
 */
public class FigureView extends ImageView {
    private Direction direction;
    private int row;
    private int col;
    private Paint circlePaint = new Paint();
    private Paint linePaint = new Paint();
    private float startX = 0, startY = 0, stopX = 0, stopY = 0;

    public FigureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackground(context.getResources().getDrawable(R.drawable.table_view_border, null));
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.TRANSPARENT);
        setImageDrawable(getResources().getDrawable(R.drawable.figure_view, null));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int CENTER = getWidth() >> 1;
        int RADIUS = getWidth() / 3;

        canvas.drawCircle(CENTER, CENTER, RADIUS, circlePaint);

        if(direction != null) {
            switch (direction) {
                default:
                    break;
                case HORIZONTAL:
                    startY = CENTER;
                    stopY = CENTER;
                    startX = 0;
                    stopX = getWidth();
                    break;
                case VERTICAL:
                    startY = 0;
                    stopY = getHeight();
                    startX = CENTER;
                    stopX = CENTER;
                    break;
                case LEFT_DIAGONAL:
                    startY = 0;
                    stopY = getHeight();
                    startX = 0;
                    stopX = getWidth();
                    break;
                case RIGHT_DIAGONAL:
                    startY = 0;
                    stopY = getHeight();
                    startX = getWidth();
                    stopX = 0;
                    break;
            }
        }

        canvas.drawLine(startX, startY, stopX, stopY, linePaint);
    }

    public void setCircleColor(int circleColor) {
        circlePaint.setColor(circleColor);
        invalidate();
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setLineDirectionWithColor(Direction direction, int color) {
        this.direction = direction;
        linePaint.setColor(color);
        invalidate();
    }
}
