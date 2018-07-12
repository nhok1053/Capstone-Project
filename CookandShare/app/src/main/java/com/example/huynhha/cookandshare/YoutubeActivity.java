package com.example.huynhha.cookandshare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.huynhha.cookandshare.adapter.ListTipsAdapter;
import com.example.huynhha.cookandshare.adapter.ListYouTubeActivityAdapter;
import com.example.huynhha.cookandshare.entity.YouTube;
import com.example.huynhha.cookandshare.entity.YoutubeConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YoutubeActivity extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    String ytb = "";
    YouTubePlayer.OnInitializedListener onInitializedListener;
    ListYouTubeActivityAdapter listYouTubeActivityAdapter;
    @BindView(R.id.btnYoutubeClose)
    Button btnYoutubeClose;
    @BindView(R.id.rvYoutubeActivity)
    RecyclerView rvYoutubeActivity;
    int tipID = 0;
    String title = "";
    String youtubeUrl = "";
    private ArrayList<YouTube> youTubes;
    public static int cnt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        ButterKnife.bind(this);
        youTubePlayerView = findViewById(R.id.youtubePlayer);
        ytb = getIntent().getStringExtra("youtube");


    }

    @Override
    protected void onStart() {
        super.onStart();
        youTubes = new ArrayList<>();
        importListCategories();
        if (cnt == 1) {
            playYoutube(ytb);
        } else if(cnt==2){
            playYoutube(YouTube.youTubeFix.getYoutubeUrl());
            System.out.println(cnt+"LLLLLLLLLLLLLLL");
        }

    }

    public void importListCategories() {
        LinearLayoutManager gln = new LinearLayoutManager(this);
        rvYoutubeActivity.setLayoutManager(gln);
        MainActivity.db.collection("Youtube")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            tipID = Integer.parseInt(documentSnapshot.get("tipID").toString());
                            title = documentSnapshot.get("title").toString();
                            youtubeUrl = documentSnapshot.get("youtubeUrl").toString();
                            if (!youtubeUrl.equals(ytb)) {
                                YouTube youTube = new YouTube(tipID, title, youtubeUrl);
                                youTubes.add(youTube);
                            }
                        }
                        listYouTubeActivityAdapter = new ListYouTubeActivityAdapter(YoutubeActivity.this, youTubes);
                        rvYoutubeActivity.setAdapter(listYouTubeActivityAdapter);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void playYoutube(final String s) {
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(s);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(YoutubeActivity.this, "Error while loading!!!", Toast.LENGTH_SHORT).show();
            }
        };
        youTubePlayerView.initialize(YoutubeConfig.getApiKey(), onInitializedListener);
    }

    public void closeYoutubeActivity(View view) {
        finish();
    }
}
