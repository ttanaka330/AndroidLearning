package jp.ttanaka330.androidlearning.ui.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 1行の文字列を表示するだけのシンプルな {@link RecyclerView.Adapter}.
 * {@see android.R.layout#simple_list_item_1}
 */
public class RecyclerSimpleAdapter extends RecyclerView.Adapter<RecyclerSimpleAdapter.ViewHolder> {

    private final Object lock = new Object();
    private final List<String> mDataList;

    public RecyclerSimpleAdapter() {
        mDataList = new ArrayList<>();
    }

    public RecyclerSimpleAdapter(@NonNull List<String> list) {
        mDataList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * アイテム追加
     *
     * @param value 表示文字列
     */
    public void add(String value) {
        final int position;
        synchronized (lock) {
            position = mDataList.size();
            mDataList.add(value);
        }
        notifyItemChanged(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
