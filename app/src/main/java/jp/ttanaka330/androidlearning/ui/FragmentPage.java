package jp.ttanaka330.androidlearning.ui;

import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;

import jp.ttanaka330.androidlearning.R;

public enum FragmentPage {

    MAIN_FRAGMENT(R.string.app_name, 0);

    private final int mTitleId;
    private final int mMenuId;

    FragmentPage(@StringRes int titleId, @MenuRes int menuId) {
        mTitleId = titleId;
        mMenuId = menuId;
    }
}
