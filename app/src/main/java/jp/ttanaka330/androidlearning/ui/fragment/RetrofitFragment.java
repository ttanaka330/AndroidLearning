package jp.ttanaka330.androidlearning.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import jp.ttanaka330.androidlearning.R;
import jp.ttanaka330.androidlearning.databinding.FragmentRetrofitBinding;
import jp.ttanaka330.androidlearning.databinding.ViewWeatherListBinding;
import jp.ttanaka330.androidlearning.service.WeatherApi;
import jp.ttanaka330.androidlearning.service.entity.WeatherResponse;
import jp.ttanaka330.androidlearning.util.FileUtils;
import jp.ttanaka330.androidlearning.viewmodel.RetrofitWeatherViewModel;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RetrofitFragment extends BaseFragment {

    private static final String CITY_FILE = "city_japan.txt";

    private FragmentRetrofitBinding mBinding;
    private WeatherDataAdapter mAdapter;

    public static RetrofitFragment newInstance() {
        return new RetrofitFragment();
    }

    public RetrofitFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentRetrofitBinding.inflate(inflater, container, false);
        initCity();
        initWeather();
        return mBinding.getRoot();
    }

    private void initCity() {
        Context context = getContext();
        List<City> list = new ArrayList<>();
        try {
            List<String> cityData = FileUtils.readAssetsCsv(context, CITY_FILE);
            for (String data : cityData) {
                String[] values = data.split("\t");
                list.add(new City(values[0], values[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mBinding.spinnerCity.setAdapter(
                new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, list));
        mBinding.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) parent.getItemAtPosition(position);
                search(city.getId(), city.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initWeather() {
        Context context = getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        DividerItemDecoration decoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        mAdapter = new WeatherDataAdapter();
        mBinding.listView.setAdapter(mAdapter);
        mBinding.listView.setLayoutManager(layoutManager);
        mBinding.listView.addItemDecoration(decoration);
    }

    private void search(final String id, final String name) {
        Timber.d("retrofit search id = " + id);
        mAdapter.clear();
        if (TextUtils.isEmpty(id)) return;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApi.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofit.create(WeatherApi.class)
                .getWeather(WeatherApi.API_KEY, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<WeatherResponse>() {
                    @Override
                    public void onNext(@NonNull WeatherResponse response) {
                        List<RetrofitWeatherViewModel> list = convertWeatherList(response);
                        for (RetrofitWeatherViewModel model : list) {
                            mAdapter.add(model);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.d(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @NonNull
    private List<RetrofitWeatherViewModel> convertWeatherList(@NonNull WeatherResponse response) {
        List<RetrofitWeatherViewModel> result = new ArrayList<>();
        if (response.lists != null) {
            for (WeatherResponse.Data data : response.lists) {
                String datetime = data.dtTxt;
                String weather = null;
                String icon = null;
                Double celsius = null;
                if (data.main != null) {
                    celsius = convertKelvinToCelsius(data.main.temp);
                }
                if (data.weathers != null && data.weathers.size() > 0) {
                    WeatherResponse.Weather w = data.weathers.get(0);
                    weather = w.main;
                    icon = WeatherApi.ICON_URL + w.icon + ".png";
                }
                result.add(new RetrofitWeatherViewModel(datetime, weather, icon, celsius));
            }
        }
        return result;
    }

    private double convertKelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    private class City {
        private String id;
        private String name;

        City(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.BindingViewHolder> {

        private final Object lock = new Object();
        List<RetrofitWeatherViewModel> mDataList = new ArrayList<>();

        @Override
        public WeatherDataAdapter.BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            final ViewWeatherListBinding binding = ViewWeatherListBinding.inflate(inflater, parent, false);
            return new WeatherDataAdapter.BindingViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(WeatherDataAdapter.BindingViewHolder holder, int position) {
            RetrofitWeatherViewModel model = mDataList.get(position);
            holder.binding.setModel(model);
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

        class BindingViewHolder extends RecyclerView.ViewHolder {

            private final ViewWeatherListBinding binding;

            BindingViewHolder(@NonNull ViewWeatherListBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

        }
    }
}
