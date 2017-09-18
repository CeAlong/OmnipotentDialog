package com.along.dialoglibrary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by MARK on 2017/9/16.
 */

public class ViewHelper {
    private Context mContext;
    private View mContentView;
    private SparseArray<WeakReference<View>> viewArray = new SparseArray<>();

    protected ViewHelper(Context context, View contentView) {
        this.mContext = context;
        this.mContentView = contentView;
    }

    protected ViewHelper(Context context, int contentID) {
        this.mContext = context;
        this.mContentView = LayoutInflater.from(context).inflate(contentID, null);
    }

    /**
     * 获取显示的布局View
     * @return
     */
    protected View getContentView() {
        return mContentView;
    }

    protected void setText(int viewId, CharSequence charSequence) {
        TextView tv = findViewById(viewId);
        if (tv == null) {
            return;
        }
        tv.setText(charSequence);
    }

    protected void setIcon(int viewId, Object o) {
        ImageView view = findViewById(viewId);
        if (view == null) {
            return;
        }
        if (o instanceof Drawable) {
            view.setImageDrawable((Drawable) o);
        } else if (o instanceof Integer) {
            view.setImageResource((Integer) o);
        }
    }


    protected void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        View view = findViewById(viewId);
        if (view == null) {
            return;
        }
        if (onClickListener instanceof OmnipotentDialog.OnClickListener){
            ((OmnipotentDialog.OnClickListener) onClickListener).setViewHelper(this);
        }
        view.setOnClickListener(onClickListener);
    }

    protected void setOnLongClickListener(int viewId, View.OnLongClickListener onLongClickListener) {
        View view = findViewById(viewId);
        if (view == null) {
            return;
        }
        view.setOnLongClickListener(onLongClickListener);
    }

    public <T extends View> T findViewById(@IdRes int viewId) {
        T view = (T) findView(viewId);
        return view;
    }

    private View findView(@IdRes int viewId) {
        View view = null;
        WeakReference<View> viewWeakReference = viewArray.get(viewId);
        if (viewWeakReference != null) {
            view =  viewWeakReference.get();
            if (view != null) {
                return view;
            }
        }
        view =  mContentView.findViewById(viewId);
        if (view != null) {
            viewArray.put(viewId, new WeakReference<>(view));
        }
        return view;
    }

}
