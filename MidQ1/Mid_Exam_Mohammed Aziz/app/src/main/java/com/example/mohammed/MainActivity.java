package com.example.mohammed;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "current_index";

    private TextView tvCounter;
    private ImageView ivArtwork;
    private TextView tvTitle;
    private TextView tvArtistYear;
    private Button btnPrevious;
    private Button btnNext;

    private List<Artwork> artworks;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCounter = findViewById(R.id.tvCounter);
        ivArtwork = findViewById(R.id.ivArtwork);
        tvTitle = findViewById(R.id.tvTitle);
        tvArtistYear = findViewById(R.id.tvArtistYear);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);

        initArtworks();

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateUI();

        btnPrevious.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateUI();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentIndex < artworks.size() - 1) {
                currentIndex++;
                updateUI();
            }
        });

        ivArtwork.setOnClickListener(v -> {
            Artwork current = artworks.get(currentIndex);
            Intent intent = new Intent(MainActivity.this, ArtworkDetailsActivity.class);
            intent.putExtra(ArtworkDetailsActivity.EXTRA_TITLE, current.getTitle());
            intent.putExtra(ArtworkDetailsActivity.EXTRA_ARTIST, current.getArtist());
            intent.putExtra(ArtworkDetailsActivity.EXTRA_YEAR, current.getYear());
            intent.putExtra(ArtworkDetailsActivity.EXTRA_URL, current.getWebsiteUrl());
            startActivity(intent);
        });
    }

    private void initArtworks() {
        artworks = new ArrayList<>();
        artworks.add(new Artwork(R.drawable.art1, "Mona Lisa", "Leonardo da Vinci", 1503, "https://www.louvre.fr"));
        artworks.add(new Artwork(R.drawable.art2, "The Persistence of Memory", "Salvador Dalí", 1931, "https://www.moma.org"));
        artworks.add(new Artwork(R.drawable.art3, "Girl with a Pearl Earring", "Johannes Vermeer", 1665, "https://www.mauritshuis.nl"));
        artworks.add(new Artwork(R.drawable.art4, "The Scream", "Edvard Munch", 1893, "https://www.nasjonalmuseet.no"));
        artworks.add(new Artwork(R.drawable.art5, "Water Lilies", "Claude Monet", 1919, "https://www.musee-orangerie.fr"));
        artworks.add(new Artwork(R.drawable.art6, "The Great Wave off Kanagawa", "Hokusai", 1831, "https://www.metmuseum.org"));
    }

    private void updateUI() {
        Artwork current = artworks.get(currentIndex);
        tvCounter.setText((currentIndex + 1) + " / " + artworks.size());
        ivArtwork.setImageResource(current.getImageResId());
        tvTitle.setText(current.getTitle());
        tvArtistYear.setText(current.getArtist() + " (" + current.getYear() + ")");

        boolean isFirst = currentIndex == 0;
        boolean isLast = currentIndex == artworks.size() - 1;

        btnPrevious.setEnabled(!isFirst);
        btnNext.setEnabled(!isLast);

        int prevActiveColor = ContextCompat.getColor(this, R.color.btn_prev_bg);
        int nextActiveColor = ContextCompat.getColor(this, R.color.btn_next_bg);
        int disabledColor = ContextCompat.getColor(this, R.color.btn_disabled_bg);

        int prevColor = isFirst ? disabledColor : prevActiveColor;
        int nextColor = isLast ? disabledColor : nextActiveColor;

        ViewCompat.setBackgroundTintList(btnPrevious, ColorStateList.valueOf(prevColor));
        ViewCompat.setBackgroundTintList(btnNext, ColorStateList.valueOf(nextColor));

        int barColor = ContextCompat.getColor(this, R.color.bar_bg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(barColor);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, currentIndex);
    }
}