package com.bei.myapplication.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by benny on 14-8-13.
 */
public class ProgressTextView extends TextView
{

    private Paint mReachedBarPaint;
    private Paint mUnreachedBarPaint;

    private Paint mReachedTextPaint;
    private Paint mUnreachedTextPaint;

    private RectF mUnreachedRectF = new RectF(0, 0, 0, 0);

    private RectF mReachedRectF = new RectF(0, 0, 0, 0);

    private Context mContext;
    private int reachBarColorId;
    private int unReachBarColorId;
    private int reachTextColorId;
    private int unReachTextColorId;

    private float progress;

    private float sizeWidth;
    private float sizeHeight;

    public ProgressTextView(Context context)
    {
        this(context, null, 0);
    }

    public ProgressTextView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public ProgressTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mContext = context;
        int[] attrsArray = new int[] {
                android.R.attr.id, // 0
                android.R.attr.background, // 1
                android.R.attr.layout_width, // 2
                android.R.attr.layout_height // 3
        };
        float density = getResources().getDisplayMetrics().density;
        rect_adius = rect_adius * density;

        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
        sizeWidth = ta.getDimensionPixelSize(2, ViewGroup.LayoutParams.MATCH_PARENT);
        sizeHeight = ta.getDimensionPixelSize(3, ViewGroup.LayoutParams.MATCH_PARENT);
        ta.recycle();

        TypedArray colorTa = context.obtainStyledAttributes(attrs, R.styleable.ProgressTextView);
        reachBarColorId = colorTa.getResourceId(R.styleable.ProgressTextView_reachBarColor, android.R.color.black);
        unReachBarColorId = colorTa.getResourceId(R.styleable.ProgressTextView_unReachBarColor, android.R.color.white);
        reachTextColorId = colorTa.getResourceId(R.styleable.ProgressTextView_reachTextColor, android.R.color.white);
        unReachTextColorId = colorTa.getResourceId(R.styleable.ProgressTextView_unReachTextColor, android.R.color.black);
        progress = colorTa.getFloat(R.styleable.ProgressTextView_textProgress, 0.1f);
        colorTa.recycle();
    }

    @Override protected void onDraw(Canvas canvas)
    {
        initializePainters();
        calculateDrawRectF();

        canvas.drawRect(mReachedRectF, mReachedBarPaint);
        canvas.drawRect(mUnreachedRectF, mUnreachedBarPaint);
        if (TextUtils.isEmpty(getText()))
        {
            return;
        }
        int reachTextLength = (int)Math.ceil(getText().length() * progress);
        String reachText = getText().toString().substring(0, reachTextLength);
        float startX = (sizeWidth - getPaint().measureText(getText().toString())) / 2;
        Rect textBounds = new Rect();
        Paint textPaint = new Paint();
        textPaint.getTextBounds(getText().toString(), 0, getText().toString().length(), textBounds);

        canvas.drawText(getText(), 0, reachTextLength, startX, (sizeHeight ) / 2.0f + textBounds.height()/2f, mReachedTextPaint);
        canvas.drawText(getText(), reachTextLength, getText().length(), startX + getPaint().measureText(reachText),
                (sizeHeight ) / 2.0f + textBounds.height()/2f, mUnreachedTextPaint);
    }

    private void initializePainters()
    {
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(mContext.getResources().getColor(reachBarColorId));

        mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedBarPaint.setColor(mContext.getResources().getColor(unReachBarColorId));

        mReachedTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedTextPaint.setTextSize(getTextSize());
        mReachedTextPaint.setColor(mContext.getResources().getColor(reachTextColorId));

        mUnreachedTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedTextPaint.setTextSize(getTextSize());
        mUnreachedTextPaint.setColor(mContext.getResources().getColor(unReachTextColorId));
    }

    private void calculateDrawRectF()
    {
        mReachedRectF.left = 0;
        mReachedRectF.top = 0;
        mReachedRectF.right = getPaddingLeft() + progress * sizeWidth;
        mReachedRectF.bottom = sizeHeight + getPaddingTop();

        mUnreachedRectF.left = mReachedRectF.right;
        mUnreachedRectF.right = sizeWidth;
        mUnreachedRectF.top = 0;
        mUnreachedRectF.bottom = sizeHeight + getPaddingTop();
    }

    public float dp2px(float dp)
    {
        final float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public float sp2px(float sp)
    {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public float getProgress()
    {
        return progress;
    }

    public void setProgress(float progress)
    {
        this.progress = progress;
        invalidate();
    }

    public int getReachBarColorId()
    {
        return reachBarColorId;
    }

    public void setReachBarColorId(int reachBarColorId)
    {
        this.reachBarColorId = reachBarColorId;
    }

    public int getUnReachBarColorId()
    {
        return unReachBarColorId;
    }

    public void setUnReachBarColorId(int unReachBarColorId)
    {
        this.unReachBarColorId = unReachBarColorId;
    }

    public int getReachTextColorId()
    {
        return reachTextColorId;
    }

    public void setReachTextColorId(int reachTextColorId)
    {
        this.reachTextColorId = reachTextColorId;
    }

    public int getUnReachTextColorId()
    {
        return unReachTextColorId;
    }

    public void setUnReachTextColorId(int unReachTextColorId)
    {
        this.unReachTextColorId = unReachTextColorId;
    }
}
