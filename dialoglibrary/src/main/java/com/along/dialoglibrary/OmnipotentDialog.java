package com.along.dialoglibrary;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MARK on 2017/9/16.
 */

public class OmnipotentDialog extends Dialog {
    private static String TAG = "OmnipotentDialog";
    final Controller mController;
    private Context mContext;

    private OmnipotentDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mController = new Controller(this, getWindow());
    }

//    public <T extends View> T findViewById(@IdRes int viewId) {
//        return mController.getViewHelper().findViewById(viewId);
//    }

    public static class Builder {
        private final Controller.DialogParams P;
        private final int mTheme;

        public Builder(@NonNull Context context) {
            this(context, R.style.OmnipotentDialogTheme);
        }

        public Builder(@NonNull Context context, @StyleRes int themeResId) {
            P = new Controller.DialogParams(context);
            mTheme = themeResId;
        }

        /*-------------------设置dialog的相关-----------------------*/

        /**
         * 设置某个View的文本
         * @param viewId  id
         * @param titleId 文本的资源
         * @return
         */
        public Builder setText(@IdRes int viewId, @StringRes int titleId) {
            CharSequence text = P.mContext.getText(titleId);
            if (text != null) {
                P.mTextArray.put(viewId, text);
            }
            return this;
        }

        /**
         * 设置某个View的文本
         *
         * @param viewId id
         * @param text   文本
         * @return
         */
        public Builder setText(@IdRes int viewId, @NonNull CharSequence text) {
            if (text != null) {
                P.mTextArray.put(viewId, text);
            }
            return this;
        }

        /**
         * 设置imageView 的src
         *
         * @param viewId id
         * @param iconId 图片资源引用
         * @return
         */
        public Builder setIcon(@IdRes int viewId, @DrawableRes int iconId) {
            if (iconId > 0) {
                P.mDrawableArray.put(viewId, iconId);
            }
            return this;
        }

        /**
         * 设置imageView 的src
         *
         * @param viewId id
         * @param icon   图片
         * @return
         */
        public Builder setIcon(@IdRes int viewId, @Nullable Drawable icon) {
            P.mDrawableArray.put(viewId, icon);
            return this;
        }

        /**
         * 设置dialog的布局
         *
         * @param layoutResID 布局文件id
         * @return
         */
        public Builder setContentView(@LayoutRes int layoutResID) {
            P.mContentID = layoutResID;
            P.mContentView = null;
            return this;
        }

        /**
         * 设置dialog的布局
         *
         * @param view 显示的view
         * @return
         */
        public Builder setContentView(@NonNull View view) {
            P.mContentView = view;
            P.mContentID = 0;
            return this;
        }

        /**
         * 设置控件的点击事件,
         * 如果需要获取dialog中的其他View, 则使用OmnipotentDialog.OnClickListener,
         * 否则可以直接使用View.OnClickListener
         * @param viewId          id
         * @param onClickListener 点击事件监听
         * @return
         */
        public Builder addOnclickListener(@IdRes int viewId, @NonNull View.OnClickListener onClickListener) {
            P.mClickListenerArray.put(viewId, onClickListener);
            return this;
        }


        /**
         * 设置控件的长按事件
         *
         * @param viewId              id
         * @param onLongClickListener 长按事件监听
         * @return
         */
        public Builder addOnLongClickListener(@IdRes int viewId, @NonNull View.OnLongClickListener onLongClickListener) {
            P.mLongClickListenerArray.put(viewId, onLongClickListener);
            return this;
        }

        /**
         * 设置按键事件监听
         *
         * @param onKeyListener 按键事件监听
         * @return
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * 点击空白处是否消失
         *
         * @param cancelable true消失
         * @return
         */
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        /**
         * 消失监听
         *
         * @param onDismissListener 消失监听
         * @return
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * 宽度是否match_parent, 默认是false WRAP_CONTENT
         *
         * @param match
         * @return
         */
        public Builder matchWidth(boolean match) {
            if (match) {
                P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                P.mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            return this;
        }

        /**
         * 高度是否match_parent, 默认是false WRAP_CONTENT
         *
         * @param match
         * @return
         */
        public Builder matchHeight(boolean match) {
            if (match) {
                P.mHeight = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                P.mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            return this;
        }

        /**
         * 调用此方法则填充全部窗体
         *
         * @return
         */
        public Builder matchParent() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            P.mHeight = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 设置框的宽和高
         *
         * @param width  宽
         * @param height 高
         * @return
         */
        public Builder setWidthAndHeight(int width, int height) {
            if (width > 0 && height > 0) {
                P.mWidth = width;
                P.mHeight = height;
            }
            return this;
        }

        /**
         * 设置对话框位置
         *
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity) {
            P.mGravity = gravity;
            return this;
        }

        /**
         * 设置显示时的动画
         *
         * @param animation
         * @return
         */
        public Builder setAnimation(@StyleRes int animation) {
            P.mAnimation = animation;
            return this;
        }

        /**
         * 创建对话框
         *
         * @return
         */
        public OmnipotentDialog create() {
            final OmnipotentDialog dialog = new OmnipotentDialog(P.mContext, mTheme);
            P.apply(dialog.mController);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        /**
         * 显示dialog
         *
         * @return
         */
        public OmnipotentDialog show() {
            final OmnipotentDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }



    /**
     * 扩展的点击事件监听, 在这里面可以通过ViewHelper对象拿到布局文件中的控件
     */
    public static abstract class OnClickListener implements View.OnClickListener {
        private ViewHelper mHelper = null;

        @Override
        public void onClick(View v) {
            onClick(mHelper, v);
        }

        public abstract void onClick(ViewHelper helper, View view);

        void setViewHelper(ViewHelper helper) {
            mHelper = helper;
        }
    }
}

