package com.yiyuanliu.sample;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;

import com.yiyuanliu.eleloadingview.EleLoadingView;

public class MainActivity extends AppCompatActivity {

    EleLoadingView[] eleLoadingViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eleLoadingViews = new EleLoadingView[] {
                (EleLoadingView) findViewById(R.id.loading1),
                (EleLoadingView) findViewById(R.id.loading2),
                (EleLoadingView) findViewById(R.id.loading3),
                (EleLoadingView) findViewById(R.id.loading4)
        };

        final Drawable[] foods = new Drawable[]{
                getResources().getDrawable(R.drawable.food1),
                getResources().getDrawable(R.drawable.food2),
                getResources().getDrawable(R.drawable.food3),
                getResources().getDrawable(R.drawable.food4),
                getResources().getDrawable(R.drawable.food5),
        };
        eleLoadingViews[1].setIcon(foods);

        SeekBar iconWidth = (SeekBar) findViewById(R.id.icon_width);
        iconWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                for (EleLoadingView eleLoadingView: eleLoadingViews) {
                    eleLoadingView.setIconWidthDp(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar iconHeight = (SeekBar) findViewById(R.id.icon_height);
        iconHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                for (EleLoadingView eleLoadingView: eleLoadingViews) {
                    eleLoadingView.setIconHeightDp(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar jumpHeight = (SeekBar) findViewById(R.id.jump_height);
        jumpHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                for (EleLoadingView eleLoadingView: eleLoadingViews) {
                    eleLoadingView.setJumpHeightDp(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar shadowMax = (SeekBar) findViewById(R.id.shadow_max);
        shadowMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                for (EleLoadingView eleLoadingView: eleLoadingViews) {
                    eleLoadingView.setShadowMax(progress / (float) 100);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar shadowMin = (SeekBar) findViewById(R.id.shadow_min);
        shadowMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                for (EleLoadingView eleLoadingView: eleLoadingViews) {
                    eleLoadingView.setShadowMin(progress / (float) 100);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        final SeekBar duration = (SeekBar) findViewById(R.id.jump_duration);
        duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                for (EleLoadingView eleLoadingView: eleLoadingViews) {
                    eleLoadingView.setDuration(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int position = 0;
        switch (item.getItemId()) {
            case R.id.emoji:
                position = 0;
                break;
            case R.id.food:
                position = 1;
                break;
            case R.id.act:
                position = 2;
                break;
            case R.id.water:
                position = 3;
                break;
        }
        for (int i = 0;i < eleLoadingViews.length; i ++) {
            eleLoadingViews[i].setVisibility(View.GONE);
        }
        eleLoadingViews[position].setVisibility(View.VISIBLE);
        return true;
    }
}
