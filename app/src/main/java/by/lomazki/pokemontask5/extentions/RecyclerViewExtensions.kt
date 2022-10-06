package by.lomazki.pokemontask5.extentions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addPaginationListener(
    linearLayoutManager: LinearLayoutManager,
    itemsToLoad: Int,
    onLoadMore: () -> Unit
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            val lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition()
            val itemCount = linearLayoutManager.itemCount

            if (itemsToLoad + lastVisiblePosition >= itemCount) {
                onLoadMore()
            }
        }
    })
}
