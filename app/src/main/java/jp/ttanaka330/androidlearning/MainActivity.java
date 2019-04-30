package jp.ttanaka330.androidlearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

/**
 * アプリ起動時に表示される {@link android.app.Activity} です。
 * API やライブラリ等の学習で作成した {@link android.app.Activity} へのポータルになります。
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_licences) {
            startActivity(new Intent(this, OssLicensesMenuActivity.class));
            return true;
        }
        return false;
    }
}
