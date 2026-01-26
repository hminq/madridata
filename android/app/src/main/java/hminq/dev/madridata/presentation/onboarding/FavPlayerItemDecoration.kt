package hminq.dev.madridata.presentation.onboarding

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FavPlayerItemDecoration(
    private val spanCount: Int,
    private val middleSpacing: Int,
    private val bottomSpacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        when (column) {
            0 -> {
                outRect.left = 0
                outRect.right = middleSpacing / 2
            }
            spanCount - 1 -> {
                outRect.left = middleSpacing / 2
                outRect.right = 0
            }
            else -> {
                outRect.left = middleSpacing / 2
                outRect.right = middleSpacing / 2
            }
        }

        outRect.bottom = bottomSpacing
    }
}
