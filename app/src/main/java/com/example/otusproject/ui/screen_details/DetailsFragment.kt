package com.example.otusproject.ui.screen_details

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TimePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.otusproject.DownloadAndSaveImageTask
import com.example.otusproject.R
import com.example.otusproject.data.vo.MovieItem
import com.example.otusproject.ui.screen_home.HomeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class DetailsFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var viewModel: HomeFragmentViewModel? = null
    private var currentTime = Calendar.getInstance()
    private var reminderTime: Calendar  = Calendar.getInstance()
    private lateinit var movieItem: MovieItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HomeFragmentViewModel::class.java)
        viewModel!!.selectedMovie.observe(this.viewLifecycleOwner, Observer {
                selectedMovie ->
        Log.d("mTAG", selectedMovie.id.toString())
        movieItem = selectedMovie
        val toolbar = collapsingToolbar
        toolbar!!.title = "Details"

        val toolbarImg = moviePoster
        val title = title
        val movieDate = movieReleaseDate
        val rating = rating
        val description = description

        selectedMovie?.let {
            Glide.with(view.context)
                .load("https://image.tmdb.org/t/p/w500" + it.posterPath)
                .into(toolbarImg!!)

            title.text = it.title
            movieDate.text = it.releaseDate
            rating.text = it.voteAverage.toString()
            description.text = it.overview
        }
        })

        watchLaterBtn.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                this,
                currentTime.get(Calendar.YEAR),
                currentTime.get(Calendar.MONTH),
                currentTime.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        downloadBtn.setOnClickListener {
            DownloadAndSaveImageTask(activity!!.baseContext)
                .execute("https://image.tmdb.org/t/p/w500"+movieItem.posterPath)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        reminderTime.set(year, month, dayOfMonth)
        TimePickerDialog(
            requireActivity(),
            this,
            currentTime.get(Calendar.HOUR_OF_DAY),
            currentTime.get(Calendar.MINUTE),
            true
        ).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        reminderTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        reminderTime.set(Calendar.MINUTE, minute)
        (activity as? Reminder)?.movieRemind(reminderTime, movieItem)
    }

    interface Reminder {
        fun movieRemind(remindTime: Calendar, movie: MovieItem)
    }

}

