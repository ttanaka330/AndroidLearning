package jp.ttanaka330.androidlearning;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

/**
 * デバッグ用アプリケーションクラス。
 * debug ビルド時に {@link Stetho} を有効にする。
 */
public class DebugApplication extends MainApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                        .withLimit(1000L)
                        .build())
                .build());
    }
}