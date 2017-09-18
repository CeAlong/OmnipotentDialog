package com.along.dialoglibrary;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StyleRes;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by MARK on 2017/9/16.
 */

class Controller {
    private String TAG = "Controller";
    private Window mWindow;
    private Dialog mDialog;
    private ViewHelper mViewHelper;

    Controller(Dialog dialog, Window window) {
        mWindow = window;
        mDialog = dialog;
    }

    private void setViewHelper(ViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
        mDialog.setContentView(mViewHelper.getContentView());
    }

    protected ViewHelper getViewHelper() {
       return mViewHelper;
    }

    private void setWidthAndHeight(int width, int height) {
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.width = width;
        params.height = height;
        mWindow.setAttributes(params);
    }

    private void setGravity(int gravity) {
        mWindow.setGravity(gravity);
    }

    private void setAnimation(@StyleRes int animation) {
        mWindow.setWindowAnimations(animation);
    }


    static class DialogParams {
        Context mContext;
        int mContentID;
        View mContentView;
        SparseArray<CharSequence> mTextArray;
        SparseArray<View.OnClickListener> mClickListenerArray;
        SparseArray<View.OnLongClickListener> mLongClickListenerArray;
        SparseArray<Object> mDrawableArray;
        DialogInterface.OnKeyListener mOnKeyListener;
        boolean mCancelable = true;
        DialogInterface.OnCancelListener mOnCancelListener;
        DialogInterface.OnDismissListener mOnDismissListener;
        int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        int mGravity = Gravity.CENTER;
        int mAnimation = 0;

        DialogParams(Context context) {
            mContext = context;
            mTextArray = new SparseArray<>();
            mClickListenerArray = new SparseArray<>();
            mLongClickListenerArray = new SparseArray<>();
            mDrawableArray = new SparseArray<>();
        }

        void apply(Controller controller) {
            ViewHelper viewHelper = null;
            if (mContentView != null) {
                viewHelper = new ViewHelper(mContext, mContentView);
            } else if (mContentID != 0) {
                viewHelper = new ViewHelper(mContext, mContentID);
            }

            if (viewHelper != null) {
                controller.setViewHelper(viewHelper);
            } else {
                throw new IllegalArgumentException("未设置布局文件或View!!!");
            }

            for (int i = 0; i < mTextArray.size(); i++) {
                viewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            for (int i = 0; i < mDrawableArray.size(); i++) {
                viewHelper.setIcon(mDrawableArray.keyAt(i), mDrawableArray.valueAt(i));
            }

            for (int i = 0; i < mClickListenerArray.size(); i++) {
                viewHelper.setOnClickListener(mClickListenerArray.keyAt(i), mClickListenerArray.valueAt(i));
            }

            for (int i = 0; i < mLongClickListenerArray.size(); i++) {
                viewHelper.setOnLongClickListener(mLongClickListenerArray.keyAt(i), mLongClickListenerArray.valueAt(i));
            }

            controller.setWidthAndHeight(mWidth, mHeight);

            controller.setGravity(mGravity);

            if (mAnimation > 0) {
                controller.setAnimation(mAnimation);
            }


        }
    }


}
