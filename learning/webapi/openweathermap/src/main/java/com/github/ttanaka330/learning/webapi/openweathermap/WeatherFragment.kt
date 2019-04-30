package com.github.ttanaka330.learning.webapi.openweathermap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ttanaka330.learning.webapi.openweathermap.data.WeatherRepository
import com.github.ttanaka330.learning.webapi.openweathermap.data.WeatherRepositoryImpl
import com.github.ttanaka330.learning.webapi.openweathermap.model.City
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var repository: WeatherRepository
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = WeatherRepositoryImpl.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initCitySpinner()
        initWeatherList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }

    private fun initCitySpinner() {
        repository.getCity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { createCitySpinner(it) }
            .addTo(compositeDisposable)
    }

    private fun initWeatherList() {
        with(list_weather) {
            val layoutManager = this.layoutManager as LinearLayoutManager
            val decoration = DividerItemDecoration(context, layoutManager.orientation)
            this.addItemDecoration(decoration)
            this.adapter = WeatherAdapter()
        }
    }

    private fun createCitySpinner(cityList: List<City>) {
        val spinnerLayout = R.layout.support_simple_spinner_dropdown_item
        val adapter = ArrayAdapter<City>(spinner_city.context, spinnerLayout, cityList)
        with(spinner_city) {
            this.adapter = adapter
            this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val city = parent.getItemAtPosition(position) as City
                    search(city.id)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    private fun search(id: String) {
        repository.getWeather(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> (list_weather.adapter as WeatherAdapter).submitList(response) },
                { t: Throwable? ->
                    t?.printStackTrace()
                    showError(getString(R.string.error_weather))
                }
            )
            .addTo(compositeDisposable)
    }

    private fun showError(message: String) {
        activity?.findViewById<View>(android.R.id.content)?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}
