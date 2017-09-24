package jp.ttanaka330.androidlearning.repository;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmDatabase {

    public RealmDatabase() {
    }

    private Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    public void add(final RealmObject model) {
        getRealm().executeTransaction(realm -> realm.insert(model));
    }

    public <T extends RealmObject> List<T> findAll(Class<T> clazz) {
        return getRealm().where(clazz).findAll();
    }

    public void close() {
        getRealm().close();
    }

}
