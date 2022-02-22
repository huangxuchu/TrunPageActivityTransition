package com.example.learnactivitytransitionanimation.model3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.learnactivitytransitionanimation.R
import com.example.learnactivitytransitionanimation.databinding.ActivityMode3StartBinding

class Mode3StartActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "Mode3StartActivity"
        fun open(activity: Activity) {
            activity.startActivity(Intent(activity, Mode3StartActivity::class.java))
        }
    }

    private lateinit var binding: ActivityMode3StartBinding
    private val handler = Handler(Looper.getMainLooper())

    private val bookshelfDates = mutableListOf(
        BookshelfData(0, "我世袭狱卒，开局镇压长公主", "https://bookcover.yuewen.com/qdbimg/349573/1031948468/150"),
        BookshelfData(1, "特拉福买家俱乐部", "https://bookcover.yuewen.com/qdbimg/349573/1003530168/300"),
        BookshelfData(2, "炼狱艺术家", "https://bookcover.yuewen.com/qdbimg/349573/1027713794/300"),
        BookshelfData(3, "精灵世界的底层训练家", "https://bookcover.yuewen.com/qdbimg/349573/1029939166/150"),
        BookshelfData(4, "谁还不是个修行者了", "https://bookcover.yuewen.com/qdbimg/349573/1031784529/150"),
        BookshelfData(5, "全球航海：我的概率百分百", "https://bookcover.yuewen.com/qdbimg/349573/1027653710/150"),
        BookshelfData(6, "我有一身被动技", "https://bookcover.yuewen.com/qdbimg/349573/1020152464/150"),
        BookshelfData(7, "大魏督主", "https://bookcover.yuewen.com/qdbimg/349573/1030980084/150"),
        BookshelfData(8, "人在东京，专业男友", "https://bookcover.yuewen.com/qdbimg/349573/1027316375/150"),
        BookshelfData(9, "我有一个剑仙娘子", "https://bookcover.yuewen.com/qdbimg/349573/1021518584/150"),
        BookshelfData(10, "重生之我真不是股神", "https://bookcover.yuewen.com/qdbimg/349573/1028035413/150"),
        BookshelfData(11, "陛下因何造反", "https://bookcover.yuewen.com/qdbimg/349573/1025332926/150"),
        BookshelfData(12, "星门", "https://bookcover.yuewen.com/qdbimg/349573/1027669580/150"),
        BookshelfData(13, "夜的命名术", "https://bookcover.yuewen.com/qdbimg/349573/1021617576/150"),
        BookshelfData(14, "我就是不按套路出牌", "https://bookcover.yuewen.com/qdbimg/349573/1021578188/150"),
        BookshelfData(15, "明克街13号", "https://bookcover.yuewen.com/qdbimg/349573/1030870265/150"),
        BookshelfData(16, "大奉打更人", "https://bookcover.yuewen.com/qdbimg/349573/1019664125/150"),
    )

    private val movePos = 0
    private var clickPos = -1
    private var animator: RecyclerView.ItemAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMode3StartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ivBook = binding.ivBook
        ivBook.setOnClickListener { startMode3EndActivity(it, Bundle()) }

        val rvBookList = binding.rvBookList
        val bookshelfAdapter = BookshelfAdapter()
        rvBookList.adapter = bookshelfAdapter
        rvBookList.layoutManager = GridLayoutManager(this, 3)
        animator = binding.rvBookList.itemAnimator

        bookshelfAdapter.list = bookshelfDates
        bookshelfAdapter.notifyItemRangeInserted(0, bookshelfDates.size)
        bookshelfAdapter.clickListener = { view, pos ->
            binding.rvBookList.itemAnimator = null

            val ivCover = view.findViewById<ImageView>(R.id.iv_cover)
            val item = bookshelfDates.removeAt(pos)
            val bundle = Bundle();
            bundle.putString("imgUrl", item.imgUrl)

            startMode3EndActivity(ivCover, bundle)

            bookshelfDates.add(movePos, item)
            clickPos = pos

            handler.postDelayed({
                if (clickPos >= 0) {
                    // 注意不能使用 binding.rvBookList.adapter?.notifyDataSetChanged()，否则返回动画无效
                    binding.rvBookList.adapter?.notifyItemMoved(clickPos, movePos)
                    binding.rvBookList.scrollToPosition(0)
                    clickPos = -1
                }
            }, Mode3EndActivity.TRANSITION_DURATION)
        }

        bookshelfAdapter.longClickListener = { view, pos ->
            val item = bookshelfDates.removeAt(pos)
            bookshelfAdapter.notifyItemRemoved(pos)
            Toast.makeText(this, "删除书籍《${item.name}》", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.rvBookList.itemAnimator = animator
    }

    private fun startMode3EndActivity(view: View, bundle: Bundle) {
        view.transitionName = "open_book"
        val i = Intent(this, Mode3EndActivity::class.java)
        i.putExtras(bundle)
        val transitionActivityOptions =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair(view, "open_book"),
                Pair(view, "open_book2")
            )
        startActivity(i, transitionActivityOptions.toBundle())
    }

    class BookshelfAdapter : RecyclerView.Adapter<BookshelfViewHolder>() {
        var list = mutableListOf<BookshelfData>()
        var clickListener: ((view: View, pos: Int) -> Unit)? = null
        var longClickListener: ((view: View, pos: Int) -> Unit)? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookshelfViewHolder {
            val viewHolder = BookshelfViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_mode3_start, null, false)
            )
            // 为什么在onCreateViewHolder创建监听，而不是onBindViewHolder。
            // 参考：https://www.cnblogs.com/guanxinjing/p/12192114.html
            viewHolder.itemView.setOnClickListener { v ->
                if (viewHolder.adapterPosition >= 0) {
                    clickListener?.invoke(v, viewHolder.adapterPosition)
                }
            }
            viewHolder.itemView.setOnLongClickListener { v ->
                var click = false
                if (viewHolder.adapterPosition >= 0) {
                    longClickListener?.invoke(v, viewHolder.adapterPosition)
                    click = true
                }
                click
            }
            return viewHolder
        }

        override fun onBindViewHolder(holder: BookshelfViewHolder, position: Int) {
            val (num, name, imgUrl) = list[position]
            holder.tvName.text = name
            holder.ivCover.load(imgUrl)
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

    class BookshelfViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val ivCover: ImageView = itemView.findViewById(R.id.iv_cover)
    }

    data class BookshelfData(val num: Int, var name: String, var imgUrl: String)

}