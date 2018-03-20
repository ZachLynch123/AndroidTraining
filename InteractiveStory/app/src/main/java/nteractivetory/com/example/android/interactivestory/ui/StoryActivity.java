package nteractivetory.com.example.android.interactivestory.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Stack;

import nteractivetory.com.example.android.interactivestory.R;
import nteractivetory.com.example.android.interactivestory.model.Page;
import nteractivetory.com.example.android.interactivestory.model.Story;

public class StoryActivity extends AppCompatActivity {
    public static final String TAG = StoryActivity.class.getSimpleName();

    private Story story;
    private ImageView storyImageView;
    private TextView storyTextView;
    private Button choice1Button;
    private Button choice2Button;
    private String name;
    // Using a generic, not a primitive
    private Stack<Integer> pageStack = new Stack<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyImageView = findViewById(R.id.storyImageView);
        storyTextView = findViewById(R.id.storyTextView);
        choice1Button = findViewById(R.id.choice1Button);
        choice2Button = findViewById(R.id.choice2Button);


        Intent intent = getIntent();
        name = intent.getStringExtra(getString(R.string.key_name));
        if (name == null || name.isEmpty()) {
            name = "Fred";
        }
        Log.d(TAG, name);

        story = new Story();
        loadPage(0);
    }

    private void loadPage(int pageNumber) {
        // Build up a stack of the pages as we use them
        pageStack.push(pageNumber);

        final Page page = story.getPage(pageNumber);
        Drawable image = ContextCompat.getDrawable(this, page.getImageId());
        storyImageView.setImageDrawable(image);

        String pageText = getString(page.getTextId());
        // Add name if placeholder included. Won't add if not
        pageText = String.format(pageText, name);
        storyTextView.setText(pageText);

        if (page.isFinalPage()) {
            choice1Button.setVisibility(View.INVISIBLE);
            choice2Button.setText(R.string.play_again_button_text);
            choice2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadPage(0);

                }
            });

        } else {


            loadButtons(page);

    }
    }

    private void loadButtons(final Page page) {
        choice1Button.setVisibility(View.VISIBLE);
        choice1Button.setText(page.getChoice1().getTextId());
        choice1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextPage = page.getChoice1().getNextPage();
                loadPage(nextPage);
            }
        });

        choice2Button.setVisibility(View.VISIBLE);
        choice2Button.setText(page.getChoice2().getTextId());
        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextPage = page.getChoice2().getNextPage();
                loadPage(nextPage);
            }
        });
    }

    @Override
    public void onBackPressed() {
        pageStack.pop();
        if (pageStack.isEmpty()){
            super.onBackPressed();
        }else {
            loadPage(pageStack.pop());
        }
        /* Example
        page 1 from page 0
        when back is pressed, the initial pageStack.pop will take page 1 off the stack

        page 0
        the stack isn't empty so therefore it will hit the else block
        the second pop will remove page 0. but then we're calling load page on that 0.
        Will push page 0 back on the stack using pageStack.push(pageNumber) in loadPage

        the key to understanding this sort of complexity is to talk it out and do a LOT of testing


         */

    }
}
