package shahin.luasforecast.utility

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import shahin.luasforecast.R
import shahin.luasforecast.forecast.LuasApiStatus
import shahin.luasforecast.network.Tram

/**
 * Binding for data on the views
 */
@BindingAdapter("due")
fun TextView.setDueMins(item: Tram?) {
    item.let {
        if (item != null) {
            text = String.format(
                when (item.due) {
                    "DUE" -> "It's coming now"
                    else -> "In ${item.due} minutes"
                }
            )
        }
    }
}

@BindingAdapter("destination")
fun TextView.setDestination(item: Tram?) {
    item.let {
        if (item != null) {
            text = String.format("to ${item.destination}")
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Tram>?) {
    val adapter = recyclerView.adapter as? TramsAdapter
    adapter?.submitList(data)
}

/**
 * For displaying loading or error image in while fetching the data based on three status
 * LOADING -> While it's fetching, it will display loading icon
 * ERROR -> If no internet connection, it will display image no connection
 * DONE -> No image related to that but to update the status
 */
@BindingAdapter("luasApiStatus")
fun bindStatus(statusImageView: ImageView, status: LuasApiStatus?) {
    when (status) {
        LuasApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        LuasApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        LuasApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

/**
 * For downloading and display Luas Stop image based on the constants below
 */

const val BROOMBRIDGE = "https://live.staticflickr.com/4738/38958701972_62388a6d56_b.jpg"
const val SANDYFORD = "hhttps://live.staticflickr.com/6006/5973421607_ab6a758528_b.jpg"
const val BRIDES_GLEN =
    "https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/The_New_Luas_Extension_-_Brides_Glen_Terminus_%285094539530%29.jpg/1599px-The_New_Luas_Extension_-_Brides_Glen_Terminus_%285094539530%29.jpg"
const val PARNELL =
    "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c8/Parnell_Luas_Stop_%28Dublin%29.jpg/800px-Parnell_Luas_Stop_%28Dublin%29.jpg"

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}