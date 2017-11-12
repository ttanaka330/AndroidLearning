package jp.ttanaka330.androidlearning.library.realm;

import android.text.TextUtils;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import jp.ttanaka330.androidlearning.library.realm.RealmActivity;
import jp.ttanaka330.androidlearning.library.realm.RealmFragment;

/**
 * ユーザー情報の {@link RealmObject} です。
 *
 * @see RealmActivity
 * @see RealmFragment
 */
public class User extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private Integer age;
    private String url;

    public static String createKey() {
        return UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return name
                + ((age == null) ? "" : "(" + age + ")")
                + ((TextUtils.isEmpty(url)) ? "" : " : " + url);
    }
}
