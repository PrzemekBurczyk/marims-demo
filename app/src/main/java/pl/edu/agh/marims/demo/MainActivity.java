package pl.edu.agh.marims.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.LinkedHashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button toggleImageButton;
    private ImageView imageView;
    private EditText emailAddressField;
    private Handler handler = new Handler();

    private static final int MAX_BITMAP_CACHE_SIZE = 3;
    private LinkedHashMap<Integer, Bitmap> bitmapCache = new LinkedHashMap<Integer, Bitmap>(MAX_BITMAP_CACHE_SIZE) {
        /**
         * This should return true when the max bitmap cache size has been exceeded
         * @param eldest the eldest entry in cache
         * @return whether or not to remove the entry from cache
         */
        @Override
        protected boolean removeEldestEntry(Entry<Integer, Bitmap> eldest) {
            return false;
        }
    };
    private int[] bitmaps = new int[]{
            R.drawable.bitmap1,
            R.drawable.bitmap2,
            R.drawable.bitmap3,
            R.drawable.bitmap4,
            R.drawable.bitmap5,
            R.drawable.bitmap6,
            R.drawable.bitmap7
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggleImageButton = (Button) findViewById(R.id.toggleImageButton);
        imageView = (ImageView) findViewById(R.id.imageView);
        toggleImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getVisibility() == View.GONE) {
                    int bitmapIndexToDisplay = new Random().nextInt(bitmaps.length);
                    Bitmap bitmap;
                    if (bitmapCache.containsKey(bitmapIndexToDisplay)) {
                        bitmap = bitmapCache.get(bitmapIndexToDisplay);
                    } else {
                        bitmap = BitmapFactory.decodeResource(getResources(), bitmaps[bitmapIndexToDisplay]);
                        bitmapCache.put(bitmapIndexToDisplay, bitmap);
                    }
                    imageView.setImageBitmap(bitmap);
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String emailAddress = emailAddressField.getText().toString();
                emailAddressField.clearFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(emailAddressField.getWindowToken(), 0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(view, "Sent email to: " + emailAddress, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }, 3000);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
