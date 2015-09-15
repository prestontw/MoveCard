package com.example.preston.movecard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


/**
 * View to display a card!
 */
public class CardView extends View {
    private Drawable mExampleDrawable;
    private Card card;

    private float left;
    private float top;
    private float width;
    private float height;
    private boolean touched = false;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public CardView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public void setCard(Card nCard){
        card = nCard;
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CardView, defStyle, 0);

        if (a.hasValue(R.styleable.CardView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.CardView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }
        if (a.hasValue(R.styleable.CardView_cardSuit)
                && a.hasValue(R.styleable.CardView_cardValue)) {
            card = new Card(a.getString(R.styleable.CardView_cardSuit),
                    a.getInt(R.styleable.CardView_cardValue, 2));
        }
        else {
            card = new Card("heart", 2);
        }
        if (a.hasValue(R.styleable.CardView_leftDimension)) {
            left = a.getDimension(R.styleable.CardView_leftDimension, 0.0f);
        }
        if (a.hasValue(R.styleable.CardView_topDimension)) {
            top = a.getDimension(R.styleable.CardView_topDimension, 0.0f);
        }
        if (a.hasValue(R.styleable.CardView_width)) {
            width = a.getDimension(R.styleable.CardView_width, 132);
        }
        if (a.hasValue(R.styleable.CardView_cardheight)) {
            height = a.getDimension(R.styleable.CardView_cardheight, 200);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        DisplayMetrics dm = getResources().getDisplayMetrics();

        float density = dm.density;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = (int) width;
        int contentHeight = (int) height;

        int leftPos = (int) (left );
        int topPos = (int) (top);
        // we use something like Drawable ... = ...;
        // Draw the text.
        //canvas.drawPicture();

        // Draw the example drawable on top of the text.
        if ((mExampleDrawable = getImage(card))!= null) {

//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.getLayoutParams();
//            params.topMargin = (int)(top);
//            params.leftMargin = (int)(left);
            mExampleDrawable.setBounds(leftPos, topPos, leftPos + contentWidth, topPos + contentHeight);
//            setLayoutParams(params);
            mExampleDrawable.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Toast.makeText(getContext(), "touched at: " + event.getX() + ", " + event.getY(),
                    Toast.LENGTH_SHORT).show();
            left = event.getX();
            top = event.getY();
            touched = true;
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    //turns a card into the appropriate image
    private Drawable getImage(Card card) {
        String suit = card.getSuit();
        int value = card.getValue();
        String fileName = suit + "_" + getValueFromInt(value);

        //get id of image to draw
        int resID = getResources().getIdentifier(fileName, "drawable", getContext().getPackageName());
        return getResources().getDrawable(resID);
    }

    //necessary for parsing card file name from value
    //TODO: add support for face cards
    private String getValueFromInt(int value) {
        boolean debug = true;
        if (debug)
            System.out.println("get value from int will return: " + value);
        return "" + value;
    }


    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
