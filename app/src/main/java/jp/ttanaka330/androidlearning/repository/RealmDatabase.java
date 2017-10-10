package jp.ttanaka330.androidlearning.repository;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmDatabase {

    public RealmDatabase() {
    }

    public Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }

    public void add(final RealmObject model) {
        getRealmInstance().executeTransaction(realm -> realm.insert(model));
    }

    public <T extends RealmObject> List<T> findAll(Class<T> clazz) {
        return getRealmInstance().where(clazz).findAll();
    }

    public void execute(Realm.Transaction transaction) {
        getRealmInstance().executeTransaction(transaction);
    }

    public void close() {
        getRealmInstance().close();
    }

}
