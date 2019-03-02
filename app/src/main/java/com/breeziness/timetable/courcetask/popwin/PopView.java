package com.breeziness.timetable.courcetask.popwin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.breeziness.timetable.R;

import java.util.List;

public class PopView extends RelativeLayout implements Checkable, View.OnClickListener, PopWindowManager.OnDismissListener, AdapterView.OnItemClickListener {

    private final static String TAG = "PopView";
    /*顶部标题*/
    private TextView tv_title;

    private boolean isChecked = true;//被转中标志
    private Context context;
    private PopWindowManager popWindowManager;
    private PopListAdapter adapter;
    private OnDropItemSelectListener onDropItemSelectListener;

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
        init(context);
    }

    public PopView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        //设置点击监听事件
        setOnClickListener(this);
    }

    /**
     * 设置数据
     *
     * @param dropBeanList
     */
    public void setData(List<DropBean> dropBeanList) {
        //默认选择第一个位置的内容
        drops = dropBeanList;
        drops.get(0).setCheck(true);
        Log.d(TAG, "setData: " + drops.get(0).getWeekday());
        tv_title.setText(drops.get(0).getWeekday());
        selectPosition = 0;
        View view = LayoutInflater.from(context).inflate(R.layout.drop_content, null);
        view.findViewById(R.id.drop_content).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowManager.hide();//当点击弹出菜单后关闭弹出菜单
            }
        });

        ListView listView = view.findViewById(R.id.drop_listview);
        listView.setOnItemClickListener(this);
        adapter = new PopListAdapter(drops, context);
        listView.setAdapter(adapter);

        popWindowManager = new PopWindowManager(context, view, this);//锚点设置为这个PopView,实际上就是我们的标题
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
        setChecked(!isChecked);
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
        // Drawable icon;//设置倒三角图标
        if (checked) {
            //icon = getResources().getDrawable(R.drawable.ic_drop_menu_week, null);
            popWindowManager.show();//显示弹出菜单

        } else {
            //icon = getResources().getDrawable(R.drawable.ic_drop_menu_week, null);
            popWindowManager.hide();//关闭弹出菜单

        }
       // isChecked = !checked;
        //把倒三角图标设置到标题的右边
        //tv_title.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (selectPosition == position) {
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
