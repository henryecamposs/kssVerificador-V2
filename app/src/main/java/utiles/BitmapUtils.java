package utiles;

/**
 * Created by HENRY on 05/03/2015.
 */

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

public class BitmapUtils {
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    void set(InputStream is) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;


    }

//      public void foo() {
//              mImageView.setImageBitmap(decodeSampledBitmapFromResource(
//                              getResources(), R.id.myimage, 100, 100));
//      }

    public void foo() {
//              BufferedInputStream bis = new BufferedInputStream(is);
//
//        final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
//        BufferedOutputStream bos = new BufferedOutputStream(dataStream);
//        try {
//                      int a = bis.available();
//
//                      while (bis.available() > 0)
//                      {
//                              bos.write(bis.read());
//                      }
//                      bos.flush();
//              } catch (IOException e) {
//                      // TODO Auto-generated catch block
//                      e.printStackTrace();
//              }

//        final byte[] data = dataStream.toByteArray();
//        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 1;

//        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);

//              imageView.setImageBitmap(bitmap);

    }
}