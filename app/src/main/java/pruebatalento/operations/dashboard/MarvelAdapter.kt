package pruebatalento.operations.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.domain.operations.marvel.CharacterDomain
import com.pruebatalento.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_item_lay.view.*


class MarvelAdapter(
    private val context: Context,
    private val characters: List<CharacterDomain>,
    private val onSelectOption: (Int) -> Unit
): RecyclerView.Adapter<MarvelAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.character_item_lay, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: CharacterDomain) {
            itemView.marvelName.text = item.name
            if (item.comics?.returned!! > 0) itemView.tvComicsValue.text = "Sí"
            if (item.series?.returned!! > 0) itemView.tvSeriesValue.text = "Sí"
            if (item.stories?.returned!! > 0) itemView.tvStoriesValue.text = "Sí"
            if (item.events?.returned!! > 0) itemView.tvEventsValue.text = "Sí"

            Picasso.get().load(item.thumbnail?.path+"."+item.thumbnail?.extension).into(itemView.charImage)
            itemView.setOnClickListener {
                onSelectOption(item.id!!)
            }
        }
    }

}