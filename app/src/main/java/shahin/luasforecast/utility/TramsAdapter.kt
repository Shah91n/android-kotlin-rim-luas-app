package shahin.luasforecast.utility

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import shahin.luasforecast.databinding.TramViewItemBinding
import shahin.luasforecast.network.Tram
import timber.log.Timber

/**
 * Adapter Customization for the RecyclerView
 */
class TramsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Tram, TramsAdapter.TramViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TramViewHolder {
        Timber.i("onCreateViewHolder is called")
        return TramViewHolder(TramViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: TramViewHolder, position: Int) {
        val tram = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(tram)
        }
        Timber.i("onBindViewHolder is called")
        holder.bind(tram)
    }

    class TramViewHolder(var binding: TramViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tram: Tram) {
            Timber.i("TramViewHolder.Bind is called")
            binding.tram = tram
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Tram>() {
        override fun areItemsTheSame(oldItem: Tram, newItem: Tram): Boolean {
            Timber.i("areItemsTheSame is called")
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Tram, newItem: Tram): Boolean {
            Timber.i("areContentsTheSame is called")
            return oldItem.destination == newItem.destination
        }
    }

    class OnClickListener(val clickListener: (tram: Tram) -> Unit) {
        fun onClick(tram: Tram) = clickListener(tram)
    }
}