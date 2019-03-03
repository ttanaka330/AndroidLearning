package jp.ttanaka330.androidlearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.ttanaka330.androidlearning.common.view.RecyclerSimpleAdapter;
import jp.ttanaka330.androidlearning.presentation.retrofit.RetrofitActivity;

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
        initLearningList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_licences:
                startActivity(new Intent(this, OssLicensesMenuActivity.class));
                return true;
            default:
                return false;
        }
    }

    private void initLearningList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(RetrofitActivity.createIntent(this)));

        RecyclerSimpleAdapter adapter = new RecyclerSimpleAdapter<Item>(list) {
            @Override
            protected void onItemClicked(int position, Item item) {
                startActivity(item.getIntent());
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        RecyclerView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setLayoutManager(layoutManager);
        listView.addItemDecoration(decoration);
    }

    private class Item {
        private final Intent mIntent;

        Item(@NonNull Intent intent) {
            mIntent = intent;
        }

        @NonNull
        Intent getIntent() {
            return mIntent;
        }

        @Override
        public String toString() {
            assert mIntent.getComponent() != null;
            String className = mIntent.getComponent().getClassName();
            return className.substring(className.lastIndexOf(".") + 1);
        }
    }
}
