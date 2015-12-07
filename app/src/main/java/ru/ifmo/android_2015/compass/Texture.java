package ru.ifmo.android_2015.compass;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

/**
 * Created by Owntage on 12/7/2015.
 */
public class Texture {
    int[] textureID = new int[1];
    public Texture(String filename)
    {

        int identifier = MainActivity.CONTEXT.getResources().getIdentifier(filename, "drawable", MainActivity.PACKAGE_NAME);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        final Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.CONTEXT.getResources(), identifier, options);

        GLES20.glGenTextures(1, textureID, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        if(textureID[0] == 0)
        {
            Log.e("texture", "texture ID equals zero");
        }
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
    }
    void bind()
    {

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID[0]);
    }
}
