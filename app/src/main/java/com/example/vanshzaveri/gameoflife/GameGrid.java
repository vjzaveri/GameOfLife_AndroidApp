package com.example.vanshzaveri.gameoflife;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class GameGrid extends View {
    //private String mExampleString; // TODO: use a default from R.string...
    //private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    //private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    //private Drawable mExampleDrawable;

    //private TextPaint mTextPaint;
    //private float mTextWidth;
    //private float mTextHeight;

    private boolean[][] grid;
    private Paint cell = new Paint();
    private int cell_size=10;
    private int rows=12,cols=12;
    private int width, height;
    private int cell_width, cell_height;


    public GameGrid(Context context) {
        //super(context);
        this(context,null);
        //init(null, 0);
    }

    public GameGrid(Context context, AttributeSet attrs) {
        super(context, attrs);

        cell_width = getWidth() / cols;
        cell_height = getHeight() / rows;
        this.width=getWidth();
        this.height=getHeight();

        grid = new boolean[rows][cols];
        //grid[5][6]=true;
        Log.d("vansh","inside const");
        invalidate();
        //init(attrs, 0);
    }

    public GameGrid(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //init(attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        this.width=getWidth();
        this.height=getHeight();
        cell_width = getWidth() / cols;
        cell_height = getHeight() / rows;
        //Log.d("vansh","inside ondraw"+height);
        canvas.drawColor(Color.WHITE);
        Paint background = new Paint();
        Paint lines = new Paint();
        lines.setColor(Color.BLACK);
        cell.setStyle(Paint.Style.FILL_AND_STROKE);
        cell.setColor(Color.RED);
        //Resources.Theme temp;
        //int myColor = getContext().getResources().getColor(com.example.vanshzaveri.gameoflife.R.color.cell);
        //Resources.Theme theme = getContext().getTheme();
        background.setColor(Color.WHITE);
        //cell.setColor(0xff74BC23);

        canvas.drawRect(0,0,height,width,background);

        for(int i=1;i<rows;i++)
        {
            //Log.d("vansh","inside for");
            canvas.drawLine(0,i*cell_height,width,i*cell_height,lines);
            canvas.drawLine(i*cell_width,0,i*cell_width,height,lines);
        }

/*        for(int i=1;i<rows;i++)
        {
            //Log.d("vansh","inside for");
            //canvas.drawLine(0,i*cell_height,width,i*cell_height,cell);
            canvas.drawLine(i*cell_width,0,i*cell_width,height,cell);
        }*/



        for(int i=0;i<rows;i++)
        {
            for (int j=0;j<cols;j++)
            {
                if(grid[i][j]!=false)
                {
                    //canvas.drawRect(i*cell_width,j*cell_height,(i+1)*cell_width,(j+1)*cell_height, cell);
                    canvas.drawCircle((i*cell_width)+cell_width/2,(j*cell_height)+cell_height/2,cell_width/2,cell);
                }
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int row = (int)(event.getY() / cell_height);
            int column = (int)(event.getX() / cell_width);

            grid[column][row] = !grid[column][row];
            invalidate();
        }

        return true;
    }

    public void next()
    {
        boolean[][] nextGrid = new boolean[rows][cols];

        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
            {
                int neighbors = getNeighbors(i,j);

                if(grid[i][j])
                {
                    if(neighbors==2 || neighbors==3)
                        nextGrid[i][j]=true;

                }
                else
                {
                    if(neighbors==3)
                    {
                        nextGrid[i][j]=true;
                    }
                }
            }
        }

        grid=nextGrid;
        invalidate();

    }

    public int getNeighbors(int i, int j)
    {

        int total=0;

        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                if (grid[(12 + (i + k)) % 12][(12 + (j + l))
                        % 12] != false) {
                    total++;
                }
            }
        }

        if(grid[i][j])
            total--;
        return total;
    }


    public void resetGame()
    {
        Log.d("Vansh","in reset game");
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.confirm))
                .setMessage(getResources().getString(R.string.AskReset))
                .setPositiveButton(getResources().getString(R.string.PositiveYes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for(int i=0;i<cols;i++)
                                {
                                    for(int j=0;j<rows;j++)
                                    {
                                        grid[i][j]=false;
                                    }
                                }
                                invalidate();
                                dialog.dismiss();
                            }})
                .setNegativeButton(getResources().getString(R.string.NegativeNo),
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                        ).show();
    }
}
