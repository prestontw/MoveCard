package com.example.preston.movecard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * View to display a card!
 */
public class CardView extends View {
    private Drawable mExampleDrawable;
    private Card card;

    private float left;
    private float top;
    private float tempLeft;
    private float tempTop;

    private float width;
    private float height;
    private boolean pressed = false;
    private boolean dragging = false;

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
        width = a.getDimension(R.styleable.CardView_width, 132 * getResources().getDisplayMetrics().density);
        height = a.getDimension(R.styleable.CardView_cardheight, 100 * getResources().getDisplayMetrics().density);

        a.recycle();

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int contentWidth = (int) width;
        int contentHeight = (int) height;

        int leftPos = (int) (left );
        int topPos = (int) (top);

        // Draw the example drawable on top of the text.
        if ((mExampleDrawable = getImage(card))!= null) {
            mExampleDrawable.setBounds(leftPos, topPos, leftPos + contentWidth, topPos + contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (dragging || (event.getX() < left + width &&
                    event.getX() > left &&
                    event.getY() < top + height &&
                    event.getY() > top)) {

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN: {
                    pressed = true;
                    tempLeft = event.getX();
                    tempTop = event.getY();

                    return true;
                }
                case MotionEvent.ACTION_MOVE: {
                    dragging = true;
                    pressed = false;
                    float dx = event.getX() - tempLeft;
                    float dy = event.getY() - tempTop;
                    left += dx;
                    top += dy;
                    tempTop = event.getY();
                    tempLeft = event.getX();
                    invalidate();
                    break;

                }
                case MotionEvent.ACTION_UP: {
                    dragging = false;
                    if (pressed) {
                        pressed = false;
                        // flip over card
                        card.flipOver();
                        invalidate();
                    }
                    return true;
                }
            }
        }

        return super.onTouchEvent(event);
    }

    //turns a card into the appropriate image
    private Drawable getImage(Card card) {
        if (card.getFaceDown())
            return getResources().getDrawable(getResources().getIdentifier(
                                "b1fv", "drawable", getContext().getPackageName()
                        ));
        String suit = card.getSuit();
        int value = card.getValue();
        String fileName = suit + "_" + getValueFromInt(value);

        //get id of image to draw
        int resID = getResources().getIdentifier(fileName, "drawable", getContext().getPackageName());
        return getResources().getDrawable(resID);
    }

    //necessary for parsing card file name from value
    private String getValueFromInt(int value) {
        if (value < 11 && value != 1)
            return "" + (value );
        String[] values = {"dummy", "jack", "queen", "king", "ace"};
        if (value == 1)
                return values[0];
        return values[value - 10];
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
