package com.tarek360.animationsamples;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final static String GITHUB = "https://github.com/tarek360/Material-Animation-Samples";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if (id == R.id.action_git_hub) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(GITHUB));
            startActivity(browserIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {
            case R.id.layoutTransitionBtn:
                intent = new Intent(this, BlogTransitionActivity.class);
                break;

            case R.id.searchWidgetBtn:
                intent = new Intent(this, SearchWidgetActivity.class);
                break;

            default:
                intent = new Intent();
        }

        startActivity(intent);
    }
}
