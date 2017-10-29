package jp.ttanaka330.androidlearning.ui.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.RealmResults;
import jp.ttanaka330.androidlearning.databinding.ViewSimpleListBinding;
import jp.ttanaka330.androidlearning.viewmodel.SimpleListViewModel;

/**
 * 1つの文字列を表示するだけのシンプルな {@link RecyclerView.Adapter}。
 * {@link RecyclerSimpleAdapter} は以下の機能を有します。
 * <li>指定したオブジェクトの #toString をリストに表示</li>
 * <li>クリックイベントは {@link #onItemClicked(int, Object)} を override して実装</li>
 */
public class RecyclerSimpleAdapter<T> extends RecyclerView.Adapter<RecyclerSimpleAdapter.ViewHolder> {

    private final Object lock = new Object();
    private final List<T> mDataList;

    public RecyclerSimpleAdapter() {
        mDataList = new ArrayList<>();
    }

    public RecyclerSimpleAdapter(@NonNull List<T> list) {
        if (list instanceof RealmResults) {
            mDataList = Observable.fromIterable(list)
                    .toList()
                    .blockingGet();
        } else {
            mDataList = list;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ViewSimpleListBinding binding = ViewSimpleListBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleListViewModel model = new SimpleListViewModel();
        model.setText(mDataList.get(position).toString());
        holder.binding.setModel(model);
        holder.binding.getRoot().setOnClickListener(v -> {
            T item = mDataList.get(position);
            onItemClicked(position, item);
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * アイテムの取得
     *
     * @param position 取得する行番号
     * @return 指定したアイテム
     */
    public T getItem(int position) {
        return mDataList.get(position);
    }

    /**
     * アイテム追加
     *
     * @param value 表示文字列
     */
    public void add(T value) {
        final int position;
        synchronized (lock) {
            position = mDataList.size();
            mDataList.add(value);
        }
        notifyItemChanged(position);
    }

    /**
     * アイテム削除
     *
     * @param position 削除する行番号
     */
    public void removeItem(int position) {
        synchronized (lock) {
            mDataList.remove(position);
        }
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataList.size());
    }

    /**
     * 全データクリア
     */
    public void clear() {
        int size = mDataList.size();
        mDataList.clear();
        notifyItemRangeRemoved(0, size);
    }

    /**
     * アイテムクリックイベント
     *
     * @param position クリックした行番号
     * @param item     クリックしたアイテム
     */
    protected void onItemClicked(int position, T item) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ViewSimpleListBinding binding;

        ViewHolder(@NonNull ViewSimpleListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
