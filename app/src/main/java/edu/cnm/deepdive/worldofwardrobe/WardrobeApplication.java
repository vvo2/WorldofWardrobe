package edu.cnm.deepdive.worldofwardrobe;

import android.app.Application;
import com.facebook.stetho.Stetho;

/**
 * class to intialize Stetho onCreate.
 */
public class WardrobeApplication extends Application {

  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
  }
}
