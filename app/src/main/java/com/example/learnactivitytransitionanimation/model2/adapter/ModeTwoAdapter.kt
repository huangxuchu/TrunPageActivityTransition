package com.example.learnactivitytransitionanimation.model2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnactivitytransitionanimation.R

/**
 * @author Huangxuchu
 * @version
 * @date 2021/9/7
 * @Description
 */
class ModeTwoAdapter : RecyclerView.Adapter<ModeTwoViewHolder>() {

    var list = arrayOf(
        "1阿富汗塔利班宣布组建新政府热494万",
        "2起底“江西行李箱藏尸案”逃犯482万",
        "3河南堵决口车主领到足额补偿款472万",
        "4西南大学核检异常学生排除新冠感染新465万",
        "5世预赛国足vs日本452万",
        "6缅怀袁爷爷:今天有乖乖把饭吃光了444万",
        "7阿里回应女员工被侵害案:相信正义434万",
        "8中秋节最热门旅行目的地北京排第一427万",
        "9塔利班欢迎美国参与重建阿富汗411万",
        "10阿洪扎达将以埃米尔身份领导阿富汗402万",
        "11警方通报辅警骑摩托撞路人致死新395万",
        "12李伟被控受贿3296万余元当庭认罪387万",
        "13广东队包揽全运会男女团跳水金牌373万",
        "14刘国梁提名国际乒联执行副主席366万",
        "15塔利班空军基地移交中国?中方辟谣",
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModeTwoViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return ModeTwoViewHolder(v)
    }

    override fun onBindViewHolder(holder: ModeTwoViewHolder, position: Int) {
        holder.tvTitle.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class ModeTwoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
}