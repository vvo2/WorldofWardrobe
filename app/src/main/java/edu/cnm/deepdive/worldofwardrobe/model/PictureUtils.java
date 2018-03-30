package edu.cnm.deepdive.worldofwardrobe.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * This class is for the two overload method to scale the bitmap
 */
public class PictureUtils {

  /**
   * This overload is less efficient but more conservative.
   * @param path        path of photo
   * @param activity    to get window manager and the default display
   * @return            a Bitmap
   */
  public static Bitmap getScaledBitmap(String path, Activity activity) {
    Point size = new Point();
    activity.getWindowManager().getDefaultDisplay()
        .getSize(size);
    return getScaledBitmap(path, size.x, size.y);
  }

  /**
   * This overload is more efficient and is used in the
   * {@link edu.cnm.deepdive.worldofwardrobe.fragments.OutfitFragment Outfit Fragment}.
   * @param path        path of photo
   * @param destWidth    a prefer width passed when invoking this method
   * @param destHeight   a prefer height passed when invoking this method
   * @return            a Bitmap
   */
  public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
// Read in the dimensions of the image on disk
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(path, options);
    float srcWidth = options.outWidth;
    float srcHeight = options.outHeight;
// Figure out how much to scale down by
    int inSampleSize = 1;
    if (srcHeight > destHeight || srcWidth > destWidth) {
      float heightScale = srcHeight / destHeight;
      float widthScale = srcWidth / destWidth;
      inSampleSize = Math.round(heightScale > widthScale ? heightScale :
          widthScale);
    }
    options = new BitmapFactory.Options();
    options.inSampleSize = inSampleSize;
// Read in and create final bitmap
    return BitmapFactory.decodeFile(path, options);
  }

}
