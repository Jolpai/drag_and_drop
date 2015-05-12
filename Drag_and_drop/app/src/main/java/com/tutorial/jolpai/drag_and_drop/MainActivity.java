package com.tutorial.jolpai.drag_and_drop;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;


public class MainActivity extends Activity implements View.OnTouchListener{

    private ImageView imageView;
    private static final String IMAGEVIEW_TAG = "Android Logo";
    private android.widget.RelativeLayout.LayoutParams layoutParams;
    String msg;

    private final static int START_DRAGGING = 0;
    private final static int STOP_DRAGGING = 1;

    private Button btn;
    private FrameLayout layout;
    private int status;
    private RadioGroup.LayoutParams params;

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setTag(IMAGEVIEW_TAG);

        layout = (FrameLayout) findViewById(R.id.LinearLayout01);
        // layout.setOnTouchListener(this);

        btn = (Button) findViewById(R.id.btn);
        btn.setDrawingCacheEnabled(true);
        btn.setOnTouchListener(this);

        params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());

                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData(v.getTag().toString(),
                        mimeTypes, item);

                // Instantiates the drag shadow builder.
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(imageView);

                // Starts the drag
                v.startDrag(dragData,  // the data to be dragged
                        myShadow,  // the drag shadow builder
                        null,      // no need to use local data
                        0          // flags (not currently used, set to 0)
                );
                return true;
            }
        });


        imageView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        layoutParams = (RelativeLayout.LayoutParams)
                                v.getLayoutParams();
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_STARTED");
                        // Do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENTERED");
                        int x_cord = (int) event.getX();
                        int y_cord = (int) event.getY();
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_EXITED");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        layoutParams.leftMargin = x_cord;
                        layoutParams.topMargin = y_cord;
                        v.setLayoutParams(layoutParams);
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_LOCATION");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");
                        // Do nothing
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.d(msg, "ACTION_DROP event");
                        // Do nothing
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


    }


    @Override
    public boolean onTouch(View view, MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            status = START_DRAGGING;
            image = new ImageView(this);
            image.setImageBitmap(btn.getDrawingCache());
            layout.addView(image, params);
        }
        if (me.getAction() == MotionEvent.ACTION_UP) {
            status = STOP_DRAGGING;
            Log.i("Drag", "Stopped Dragging");
        } else if (me.getAction() == MotionEvent.ACTION_MOVE) {
            if (status == START_DRAGGING) {
                System.out.println("Dragging");
                image.setPadding((int) me.getRawX(), (int) me.getRawY(), 0, 0);
                image.invalidate();
            }
        }
        return false;
    }


    @Override
    protected  void onResume(){
        super.onResume();





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
