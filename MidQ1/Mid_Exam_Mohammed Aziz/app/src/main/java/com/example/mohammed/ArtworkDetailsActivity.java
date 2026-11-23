package com.example.mohammed;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ArtworkDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_ARTIST = "extra_artist";
    public static final String EXTRA_YEAR = "extra_year";
    public static final String EXTRA_URL = "extra_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_details);

        int barColor = ContextCompat.getColor(this, R.color.bar_bg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(barColor);
        }

        TextView tvDetails = findViewById(R.id.tvDetails);
        Button btnVisitWebsite = findViewById(R.id.btnVisitWebsite);
        Button btnBackToMain = findViewById(R.id.btnBackToMain);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String artist = getIntent().getStringExtra(EXTRA_ARTIST);
        int year = getIntent().getIntExtra(EXTRA_YEAR, 0);
        String url = getIntent().getStringExtra(EXTRA_URL);

        String info = "Title: " + title + "\nArtist: " + artist + "\nYear: " + year;
        tvDetails.setText(info);

        btnVisitWebsite.setOnClickListener(v -> {
            if (url != null && !url.isEmpty()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            } else {
                Toast.makeText(this, "No website available", Toast.LENGTH_SHORT).show();
            }
        });

        btnBackToMain.setOnClickListener(v -> finish());
    }
}