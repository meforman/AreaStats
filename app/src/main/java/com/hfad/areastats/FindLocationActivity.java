package com.hfad.areastats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class FindLocationActivity extends Activity {
    public  static String lCode = "01099";
    public static String sCode = "36";
    public static String cCode = "029";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);

        // Git Test Commit 2
    }

    public void onSearchAddress(View view) {
        switch (view.getId()) {
            case R.id.push_button:
                Intent intent = new Intent(FindLocationActivity.this, LocationDataActivity.class);
                intent.putExtra(LocationDataActivity.LOCATION_CODE, lCode);
                intent.putExtra(LocationDataActivity.STATE_CODE, sCode);
                intent.putExtra(LocationDataActivity.COUNTY_CODE, cCode);
                startActivity(intent);
                break;
            case R.id.send:
                intent = new Intent(FindLocationActivity.this, LocationDataActivity.class);
                intent.putExtra(LocationDataActivity.LOCATION_CODE, lCode);
                intent.putExtra(LocationDataActivity.STATE_CODE, sCode);
                intent.putExtra(LocationDataActivity.COUNTY_CODE, cCode);
                startActivity(intent);
                break;
        }
    }



}
