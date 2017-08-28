package com.example.leo.customviews;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SuggestionSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    Button btnPress;
    ShapeSelectorView shapeSelectorView;

    ImageView imageView;

    EditText editText;

    TextView bookMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnPress = (Button) findViewById(R.id.btn_press);
        shapeSelectorView = (ShapeSelectorView) findViewById(R.id.shape_view);
        imageView = (ImageView) findViewById(R.id.img_drawable);

        editText = (EditText) findViewById(R.id.edit_text);

        bookMsg = (TextView) findViewById(R.id.book_msg);

        btnPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int count = random.nextInt(10);
                String msg = getResources().getQuantityString(R.plurals.book_count, count, count);

                SpannableString spannable = new SpannableString(msg);

                if (count == 0) {
                    spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, 0);
                } else {
                    spannable.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 1, 0);

                }

                bookMsg.setText(spannable);
            }
        });


    }
}
