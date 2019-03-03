package jp.ttanaka330.androidlearning.presentation.retrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import jp.ttanaka330.androidlearning.R;

/**
 * @see <a href="http://square.github.io/picasso/">Picasso</a>
 */
public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.ViewHolder> {

    private final Object lock = new Object();
    private final List<RetrofitWeatherViewModel> mDataList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.view_weather_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RetrofitWeatherViewModel model = mDataList.get(position);
        holder.datetimeView.setText(model.getDatetime());
        holder.weatherView.setText(model.getWeather());
        holder.celsiusView.setText(model.getCelsius());
        Picasso.get().load(model.getIcon()).into(holder.iconView);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void add(RetrofitWeatherViewModel model) {
        final int position;
        synchronized (lock) {
            position = mDataList.size();
            mDataList.add(model);
        }
        notifyItemChanged(position);
    }

    public void clear() {
        int size = mDataList.size();
        mDataList.clear();
        notifyItemRangeRemoved(0, size);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView iconView;
        final TextView datetimeView;
        final TextView weatherView;
        final TextView celsiusView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.icon);
            datetimeView = itemView.findViewById(R.id.text_datetime);
            weatherView = itemView.findViewById(R.id.text_weather);
            celsiusView = itemView.findViewById(R.id.text_celsius);
        }
    }
}
