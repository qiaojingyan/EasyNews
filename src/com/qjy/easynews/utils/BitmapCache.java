package com.qjy.easynews.utils;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.toolbox.ImageLoader;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qjy on 15-4-7.
 */
public class BitmapCache implements ImageLoader.ImageCache {
    private static BitmapCache bitmapCash;
    private LruCache<String, Bitmap> lruCache;
    private Map<String, SoftReference<Bitmap>> softMap;

    private BitmapCache() {
        int memorySize = (int) (Runtime.getRuntime().maxMemory() / 16);
        softMap = new HashMap<String, SoftReference<Bitmap>>();
        lruCache = new LruCache<String, Bitmap>(memorySize) {
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }

            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(oldValue);
                softMap.put(key, softReference);
            }
        };
    }

    public static BitmapCache getInstance() {
        if (bitmapCash == null) {
            bitmapCash = new BitmapCache();
        }
        return bitmapCash;
    }

    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        bm = lruCache.get(url);
        if (bm == null) {
            SoftReference<Bitmap> softReference = softMap.get(url);
            if(softReference!=null){
                bm = softMap.get(url).get();
            }
        }
        return bm;
    }

    public void putBitmap(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }
}
