package jp.ttanaka330.androidlearning.library.realm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * {@link Realm} データベースを管理するクラスです。
 * {@link Realm} で取り扱われる {@link RealmObject} には素の状態 StandaloneObject と
 * {@link Realm} 管理下に置かれている ManagedObject の 2 種類があります。
 * 編集を行う場合には状態に気を付けてください。
 * データベースに追加する（管理下に置く場合）には {@link Realm#createObject(Class)} を使用します。
 *
 * @see <a href="https://realm.io/jp/">Realm</a>
 */
public class RealmDatabase {

    public RealmDatabase() {
    }

    /**
     * {@link Realm} データベースのインスタンスを取得します。
     *
     * @return {@link Realm} インスタンス
     */
    private Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }

    /**
     * モデルをデータベースに追加します。
     * 引数で渡されたモデルオブジェクトは管理下に置かれていないため、編集を行いたい場合は、再検索するか
     * {@link #execute(Realm.Transaction)} にて登録を行ってください。
     *
     * @param model 追加するモデル
     */
    public void add(final RealmObject model) {
        getRealmInstance().executeTransaction(realm -> realm.insert(model));
    }

    /**
     * 指定したモデルクラスをデータベースから全件取得します。
     *
     * @param clazz 取得する {@link RealmObject} クラス
     * @param <T>   {@link RealmObject} クラス
     * @return 指定した {@link RealmObject} の一覧
     */
    public <T extends RealmObject> List<T> findAll(Class<T> clazz) {
        return getRealmInstance().where(clazz).findAll();
    }

    /**
     * 指定したトランザクション処理を実行します。
     *
     * @param transaction トランザクション内で行う処理
     */
    public void execute(Realm.Transaction transaction) {
        getRealmInstance().executeTransaction(transaction);
    }

    /**
     * データベースをクローズします。
     */
    public void close() {
        getRealmInstance().close();
    }

}
