package hminq.dev.madridata.presentation.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import hminq.dev.madridata.R
import hminq.dev.madridata.databinding.ItemFavPlayerBinding
import hminq.dev.madridata.databinding.ItemFavPlayerLoadingBinding
import hminq.dev.madridata.domain.model.OnboardingFavPlayer

class FavPlayerAdapter(
    private val onPlayerClick: (OnboardingFavPlayer) -> Unit
) : ListAdapter<FavPlayerItem, RecyclerView.ViewHolder>(PlayerDiffCallback) {

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_PLAYER = 1
        private const val LOADING_ITEM_COUNT = 6
    }

    private var isLoading = false

    fun setLoading(loading: Boolean) {
        if (isLoading == loading) return
        isLoading = loading
        if (loading) {
            submitList(List(LOADING_ITEM_COUNT) { FavPlayerItem.Loading })
        }
    }

    fun submitPlayers(players: List<OnboardingFavPlayer>) {
        isLoading = false
        submitList(players.map { FavPlayerItem.Player(it) })
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FavPlayerItem.Loading -> VIEW_TYPE_LOADING
            is FavPlayerItem.Player -> VIEW_TYPE_PLAYER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LOADING -> {
                val binding = ItemFavPlayerLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LoadingViewHolder(binding)
            }
            else -> {
                val binding = ItemFavPlayerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PlayerViewHolder(binding, onPlayerClick)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is FavPlayerItem.Player -> (holder as PlayerViewHolder).bind(item.player)
            is FavPlayerItem.Loading -> { /* Nothing to bind for loading */ }
        }
    }

    class LoadingViewHolder(
        binding: ItemFavPlayerLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class PlayerViewHolder(
        private val binding: ItemFavPlayerBinding,
        private val onPlayerClick: (OnboardingFavPlayer) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(player: OnboardingFavPlayer) {
            binding.apply {
                tvPlayerName.text = player.name
                tvPlayerNumber.text = player.number.toString()
                tvPlayerPosition.text = player.position.displayName

                ivPlayerImage.load(player.imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.image_player_default)
                }

                root.setOnClickListener { onPlayerClick(player) }
            }
        }
    }

    private object PlayerDiffCallback : DiffUtil.ItemCallback<FavPlayerItem>() {
        override fun areItemsTheSame(
            oldItem: FavPlayerItem,
            newItem: FavPlayerItem
        ): Boolean {
            return when {
                oldItem is FavPlayerItem.Loading && newItem is FavPlayerItem.Loading -> true
                oldItem is FavPlayerItem.Player && newItem is FavPlayerItem.Player ->
                    oldItem.player.id == newItem.player.id
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: FavPlayerItem,
            newItem: FavPlayerItem
        ): Boolean = oldItem == newItem
    }
}

sealed class FavPlayerItem {
    data object Loading : FavPlayerItem()
    data class Player(val player: OnboardingFavPlayer) : FavPlayerItem()
}
