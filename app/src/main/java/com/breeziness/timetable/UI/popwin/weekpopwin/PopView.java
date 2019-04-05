package com.breeziness.timetable.UI.popwin.weekpopwin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.breeziness.timetable.R;

import java.util.ArrayList;
import java.util.List;

public class PopView extends RelativeLayout implements Checkable, View.OnClickListener, PopWindowManager.OnDismissListener, AdapterView.OnItemClickListener {

    private final static String TAG = "PopView";
    /*顶部标题*/
    private TextView tv_title;
    private View titleView;
    private int CurWeek;
    private boolean isChecked = true;//被选中标志
    private Context context;
    private PopWindowManager popWindowManager;
    private PopListAdapter adapter;
    private OnDropItemSelectListener onDropItemSelectListener;

    //private OnShowListener onShowListener;
    //private OnDismissListener onDismissListener;


    /*传入显示的数据*/
    private List<DropBean> drops;

    /*当前被选中的item的位置*/
    private int selectPosition;

    public PopView(Context context) {
        this(context, null);
    }

    public PopView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);//初始化
    }


    /**
     * 初始化操作
     *
     * @param context
     */
    private void init(Context context) {
        this.context = context;

        /*顶部标题设置*/
        View view = LayoutInflater.from(getContext()).inflate(R.layout.drop_top_title, this, true);
        tv_title = view.findViewById(R.id.drop_title);
        titleView = view.findViewById(R.id.drop_top);

        //设置点击监听事件
        titleView.setOnClickListener(this);
    }

    /**
     * @param CurWeek    当前周
     * @param selectWeek 选择跳转到的周次
     */
    public void setData(int CurWeek, int selectWeek) {
        this.CurWeek = CurWeek;
        drops = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            drops.add(new DropBean("第" + (i + 1) + "周"));
        }
        //默认选择当前周的内容
        drops.get(selectWeek-1).setCheck(true);
        drops.get(CurWeek-1).setCurWeek(true);//默认当前周
        tv_title.setText(drops.get(CurWeek-1).getWeekday());
        selectPosition = CurWeek-1;
        View contentView = LayoutInflater.from(context).inflate(R.layout.drop_content, null);
        contentView.findViewById(R.id.drop_content).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowManager.hide();//当点击弹出菜单后关闭弹出菜单
            }
        });

        ListView listView = contentView.findViewById(R.id.drop_listview);
        listView.setOnItemClickListener(this);
        adapter = new PopListAdapter(drops, context);
        listView.setAdapter(adapter);

        popWindowManager = new PopWindowManager(context, contentView, this);//锚点设置为这个PopView,实际上就是我们的标题栏
        popWindowManager.setOnDismissListener(this);//设置视图消失监听

    }

    /**
     * 设置内容的方法
     *
     * @param content
     */
    public void setText(CharSequence content) {
        tv_title.setText(content);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drop_top:
                setChecked(!isChecked);
                break;
        }

    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
        // Drawable icon;//设置倒三角图标
        if (checked) {
            //icon = getResources().getDrawable(R.drawable.ic_drop_menu_week, null);
            popWindowManager.show();//显示弹出菜单
            //onShowListener.OnShow();//显示的状态回调


        } else {
            //icon = getResources().getDrawable(R.drawable.ic_drop_menu_week, null);
            popWindowManager.hide();//关闭弹出菜单
            // onDismissListener.OnDismiss();//关闭的状态回调


        }

        //把倒三角图标设置到标题的右边
        //tv_title.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (selectPosition == position) {
            popWindowManager.hide();
            return;//当与上次选择的item相同时，直接退出
        }
        drops.get(selectPosition).setCheck(false);//将上次选择的标志清空
        drops.get(position).setCheck(true);//将这次选择的item标记为已经选择
        tv_title.setText(drops.get(position).getWeekday());//显示这次所选的周次
        adapter.notifyDataSetInvalidated();//通知适配器更新listview数据
        selectPosition = position;//更新选择的位置
        popWindowManager.hide();
        //回调被点击的item的position
        if (onDropItemSelectListener != null) {
            onDropItemSelectListener.onDropItemSelect(position);
        }
    }

    /**
     * 设置获取点击位置的接口回调监听
     *
     * @param onDropItemSelectListener
     */
    public void setOnDropItemSelectListener(OnDropItemSelectListener onDropItemSelectListener) {
        this.onDropItemSelectListener = onDropItemSelectListener;
    }


    /**
     * 获取点击item位置的回调接口
     */
    public interface OnDropItemSelectListener {

        void onDropItemSelect(int Postion);
    }


    /*
     */
/**
 * 设置Popview弹出监听
 *
 * @param onShowListener
 *//*

    public void setOnShowListener(OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
    }

    */
/**
 * 设置Popview关闭监听
 *
 * @param onDismissListener
 *//*

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    */
/**
 * Popview弹出监听的回调接口
 *//*

    public interface OnShowListener {
        void OnShow();
    }

    */

    /**
     * Popview关闭监听的回调接口
     *//*

    public interface OnDismissListener {
        void OnDismiss();
    }
*/
    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    @Override
    public void OnDismiss() {
        setChecked(false);
    }
}
