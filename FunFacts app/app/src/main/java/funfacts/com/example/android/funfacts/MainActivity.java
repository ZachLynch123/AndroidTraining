package funfacts.com.example.android.funfacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    // Declare View variables
    private TextView factTextView;
    private Button showFactButton;
    private FactBook factBook = new FactBook();
    private RelativeLayout relativeLayout;
    private ColorWheel colorWheel = new ColorWheel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign the views from the layout file to the corresponding variables
        factTextView = findViewById(R.id.factTextView);
        showFactButton = findViewById(R.id.showFactBtn);
        relativeLayout = findViewById(R.id.relativeLayout);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update screen with new fact
                String fact = factBook.getFact();
                int color = colorWheel.getColors();
                relativeLayout.setBackgroundColor(color);
                showFactButton.setTextColor(color);
                factTextView.setText(fact);
            }
        };
        showFactButton.setOnClickListener(listener);

        //Toast.makeText(this, "Yay! activity created", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"We're logging from onCreate");
    }
}
