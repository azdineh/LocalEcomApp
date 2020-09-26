package com.sim.localecomapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import androidx.annotation.Nullable;

/**
 * {@link View.OnClickListener} used to translate the product grid sheet downward on
 * the Y-axis when the navigation icon in the toolbar is pressed.
 */
public class NavigationIconClickListener implements View.OnClickListener {

    BackDropAnimator backDropAnimator;

    NavigationIconClickListener(Context context, View sheet) {
        this(context, sheet, null);
    }

    NavigationIconClickListener(Context context, View sheet, @Nullable Interpolator interpolator) {
        this(context, sheet, interpolator, null, null);
    }

    NavigationIconClickListener(
            Context context, View sheet, @Nullable Interpolator interpolator,
            @Nullable Drawable openIcon, @Nullable Drawable closeIcon) {

        backDropAnimator=BackDropAnimator.getInstance(context,sheet,interpolator,openIcon,closeIcon);
    }


    @Override
    public void onClick(View view) {
        BackDropAnimator.imageViewicon=(ImageView) view;
        backDropAnimator.moveBackDrop();
    }

}
