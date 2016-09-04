package com.edus.apollo.common.utils.photo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by PandaPan on 2016/9/4.
 */
public class PhotoUtils {
    /**
     * make new bitmap from current drawable
     * @param drawable
     * @return
     */
    public static Bitmap newBitmapFromDrawable(Drawable drawable){
        if(drawable == null){
            return null;
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w,h,config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * get current drawable's bitmap if drawable is BitmapDrawable
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable){
        if(drawable == null || !(drawable instanceof BitmapDrawable)){
            return null;
        }
        return ((BitmapDrawable)drawable).getBitmap();
    }

    public static Drawable bitmap2Drawable(Bitmap bitmap){
        if(bitmap == null){
            return null;
        }
        return new BitmapDrawable(bitmap);
    }


}
