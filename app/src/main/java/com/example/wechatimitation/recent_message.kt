package com.example.wechatimitation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wechatimitation.recent_message
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class recent_message : AppCompatActivity() {
    private val recentList: MutableList<Recent_Msg> = ArrayList()
    private var peoList: MutableList<Friend?>? = ArrayList()
    private var mContext: Context? = null
    private var recyclerView: RecyclerView? = null
    private var recentAdapter: RecentAdapter? = null
    private var peoAdapter: FriendAdapter? = null
    private var weather: Weather? = null
    private var ad: AndroidServer? = null
    private var userIDText: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_message)
        mContext = this
        if (ad == null) {
            ad = AndroidServer()
        }
        if (userIDText == null) {
            userIDText = intent.getStringExtra("userName")
            if (userIDText == null) {
                userIDText = savedInstanceState!!.getString("userName")
                if (userIDText == null) {
                    userIDText = "10086"
                }
            }
        }
        initPeos()
        initRects()
        if (recyclerView == null) {
            recyclerView = findViewById<View>(R.id.recent_message_recyclerList) as RecyclerView
        }
        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        recentAdapter = RecentAdapter(recentList, userIDText)
        recyclerView!!.adapter = recentAdapter

        // BottomNavigationView btw = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        // 设置消息标志

//        BadgeDrawable badge = btw.getOrCreateBadge(R.id.page_1);
//        badge.setVisible(true);
//
//        btw.setSelectedItemId(R.id.page_2); // 设置被选中项
//        badge.setNumber(99999); // 设置消息数
        val btw = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        val badge = btw.getOrCreateBadge(R.id.page_1)
        badge.isVisible = true
        badge.number = 9999 // 设置消息数
        btw.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            clearIcon(btw)
            when (id) {
                R.id.page_1 -> {
                    // Msg.showText(mContext, "You click the page 1");
                    recyclerView!!.adapter = recentAdapter
                    item.setIcon(R.drawable.baseline_chat_bubble_24)
                    //                        btw.setSelectedItemId(id);
                    return@OnNavigationItemSelectedListener true
                }
                R.id.page_2 -> {
                    // Msg.showText(mContext, "You click the page 2");
                    if (peoAdapter == null) {
                        // peoAdapter = new PeoAdapter(peoList);
                        peoAdapter = FriendAdapter(peoList, userIDText)
                    }
                    recyclerView!!.adapter = peoAdapter
                    item.setIcon(R.drawable.baseline_account_circle_24)
                    //                        btw.setSelectedItemId(id);
                    return@OnNavigationItemSelectedListener true
                }
                else -> {
                }
            }
            false
        })
        btw.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                }
                R.id.page_2 -> {
                }
                else -> {
                }
            }
        }
        val tooAppbar = findViewById<View>(R.id.topAppBar) as Toolbar
        tooAppbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.favorite -> {
                    try {
                        if (weather == null) {
                            weather = Weather()
                        }
                        Msg.Companion.showText(this@recent_message, String.format(
                                "\n温度：%.2f℃\n天气情况：%s",
                                weather!!.temperature, weather!!.skycon))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    return@OnMenuItemClickListener true
                }
                R.id.search -> {
                    Msg.Companion.showText(this@recent_message, "click search")
                    return@OnMenuItemClickListener true
                }
                else -> {
                }
            }
            false
        })
    }

    override fun onRestart() {
        super.onRestart()
        peoList!!.clear()
        recentList.clear()
        initPeos()
        initRects()
        peoAdapter = null
        peoAdapter = FriendAdapter(peoList, userIDText)
        recentAdapter = null
        recentAdapter = RecentAdapter(recentList, userIDText)
        if (recyclerView == null) {
            recyclerView = findViewById<View>(R.id.recent_message_recyclerList) as RecyclerView
        }
        if (findViewById<View>(R.id.page_1).isSelected) {
            recyclerView!!.adapter = recentAdapter
        } else {
            recyclerView!!.adapter = peoAdapter
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("userName", userIDText)
    }

    private fun clearIcon(btw: BottomNavigationView) {
        btw.menu.findItem(R.id.page_1).setIcon(R.drawable.baseline_chat_bubble_outline_24)
        btw.menu.findItem(R.id.page_2).setIcon(R.drawable.outline_account_circle_24)
    }

    private fun initPeos() {
        peoList = ad!!.getContactsByUserName(userIDText)
        if (peoList != null) {
            peoList.removeAt(0)
        }

//        peoList.add(new Peo("朱一龙", R.drawable.n1));
//        peoList.add(new Peo("邓伦", R.drawable.n2));
//        peoList.add(new Peo("王大陆", R.drawable.n3));
//        peoList.add(new Peo("范丞丞", R.drawable.n4));
//        peoList.add(new Peo("张云雷", R.drawable.n5));
//        peoList.add(new Peo("刘翔", R.drawable.n6));
//        peoList.add(new Peo("林更新", R.drawable.n7));
//        peoList.add(new Peo("吴秀波", R.drawable.n8));
//        peoList.add(new Peo("李易峰", R.drawable.n9));
//        peoList.add(new Peo("成毅", R.drawable.n10));
//        peoList.add(new Peo("黄渤", R.drawable.n11));
//        peoList.add(new Peo("权志龙", R.drawable.n12));
//        peoList.add(new Peo("李小龙", R.drawable.n13));
//        peoList.add(new Peo("白敬亭", R.drawable.n14));
//        peoList.add(new Peo("张纪中", R.drawable.n15));
//        peoList.add(new Peo("魏大勋", R.drawable.n16));
//        peoList.add(new Peo("边伯贤", R.drawable.n17));
//        peoList.add(new Peo("胡先煦", R.drawable.n18));
//        peoList.add(new Peo("林正英", R.drawable.n19));
//        peoList.add(new Peo("韩永华", R.drawable.n20));
//        peoList.add(new Peo("张伦硕", R.drawable.n21));
//        peoList.add(new Peo("李宁", R.drawable.n22));
//        peoList.add(new Peo("经超", R.drawable.n23));
//        peoList.add(new Peo("刘宪华", R.drawable.n24));
//        peoList.add(new Peo("张嘉译", R.drawable.n25));
//        peoList.add(new Peo("沈浩", R.drawable.n26));
//        peoList.add(new Peo("许凯", R.drawable.n27));
//        peoList.add(new Peo("王千源", R.drawable.n28));
//        peoList.add(new Peo("徐小明", R.drawable.n29));
//        peoList.add(new Peo("罗晋", R.drawable.n30));
//        peoList.add(new Peo("高以翔", R.drawable.n31));
//        peoList.add(new Peo("龚俊", R.drawable.n32));
//        peoList.add(new Peo("王泷正", R.drawable.n33));
//        peoList.add(new Peo("田蕤", R.drawable.n34));
//        peoList.add(new Peo("陈晓", R.drawable.n35));
//        peoList.add(new Peo("佟大为", R.drawable.n36));
//        peoList.add(new Peo("高伟光", R.drawable.n37));
//        peoList.add(new Peo("水木年华", R.drawable.n38));
//        peoList.add(new Peo("李栋旭", R.drawable.n39));
//        peoList.add(new Peo("牛骏峰", R.drawable.n40));
//        peoList.add(new Peo("袁冰妍", R.drawable.n41));
//        peoList.add(new Peo("小雪", R.drawable.n42));
//        peoList.add(new Peo("马苏", R.drawable.n43));
//        peoList.add(new Peo("伍宇娟", R.drawable.n44));
//        peoList.add(new Peo("文咏珊", R.drawable.n45));
//        peoList.add(new Peo("白鹿", R.drawable.n46));
//        peoList.add(new Peo("江祖平", R.drawable.n47));
//        peoList.add(new Peo("曹曦文", R.drawable.n48));
//        peoList.add(new Peo("郭书瑶", R.drawable.n49));
//        peoList.add(new Peo("施诗", R.drawable.n50));
//        peoList.add(new Peo("左小青", R.drawable.n51));
//        peoList.add(new Peo("李小萌", R.drawable.n52));
//        peoList.add(new Peo("孟子义", R.drawable.n53));
//        peoList.add(new Peo("钟楚曦", R.drawable.n54));
//        peoList.add(new Peo("陈昱霖", R.drawable.n55));
//        peoList.add(new Peo("蔡思贝", R.drawable.n56));
//        peoList.add(new Peo("景甜", R.drawable.n57));
//        peoList.add(new Peo("梁丹妮", R.drawable.n58));
//        peoList.add(new Peo("李彩桦", R.drawable.n59));
//        peoList.add(new Peo("高圆圆", R.drawable.n60));
//        peoList.add(new Peo("陈凯师", R.drawable.n61));
//        peoList.add(new Peo("文梦洋", R.drawable.n62));
//        peoList.add(new Peo("许佳琪", R.drawable.n63));
//        peoList.add(new Peo("宋祖儿", R.drawable.n64));
//        peoList.add(new Peo("李洁", R.drawable.n65));
//        peoList.add(new Peo("姜妍", R.drawable.n66));
//        peoList.add(new Peo("王莎莎", R.drawable.n67));
//        peoList.add(new Peo("翟煦飞", R.drawable.n68));
//        peoList.add(new Peo("王思懿", R.drawable.n69));
//        peoList.add(new Peo("李小璐", R.drawable.n70));
//        peoList.add(new Peo("郭碧婷", R.drawable.n71));
//        peoList.add(new Peo("裴珠泫", R.drawable.n72));
//        peoList.add(new Peo("张子枫", R.drawable.n73));
//        peoList.add(new Peo("秦雪", R.drawable.n74));
//        peoList.add(new Peo("姜素拉", R.drawable.n75));
//        peoList.add(new Peo("陈瑾", R.drawable.n76));
//        peoList.add(new Peo("张馨予", R.drawable.n77));
//        peoList.add(new Peo("张柏芝", R.drawable.n78));
//        peoList.add(new Peo("刘楚恬", R.drawable.n79));
//        peoList.add(new Peo("孙怡", R.drawable.n80));
    }

    private fun initRects() {
//        recentList.add(new Recent_Msg(
//                new Peo("朱一龙", R.drawable.n1),
//                new Msg("test2 msg", Msg.TYPE_SENT),
//                new Date(2020 - 1900, 11 - 1, 28, 12, 30)));
//        recentList.add(new Recent_Msg(
//                new Peo("邓伦", R.drawable.n2),
//                new Msg("test2 msg", Msg.TYPE_RECEIVED),
//                new Date(2020 - 1900, 11 - 1, 27, 11, 29)));
//        recentList.add(new Recent_Msg(
//                new Peo("白敬亭", R.drawable.n14),
//                new Msg("test3 msg", Msg.TYPE_SENT),
//                new Date(2020 - 1900, 11 - 1, 26, 10, 28)));
        var msgList: List<Msg?>? = null
        var friend: Friend? = null
        for (i in peoList!!.indices) {
            friend = peoList!![i]

            // 时间按从大到小排序
            msgList = ad!!.getMsgByTwoUserName(userIDText, friend.getUser_name())
            if (msgList == null) {
                continue
            }
            if (msgList.isEmpty()) {
                continue
            }
            recentList.add(Recent_Msg(friend, msgList[0], msgList[0].getTime()))
        }
    }
}