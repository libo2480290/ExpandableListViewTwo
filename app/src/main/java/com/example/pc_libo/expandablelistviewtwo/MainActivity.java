package com.example.pc_libo.expandablelistviewtwo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private ExpandableListView expandableListView;

    private List<String> group_list;

    private List<List<String>> item_list;

    private List<List<Integer>> item_list2;

    private List<List<Boolean>> select_list;

    private int groupSelectIndex = 0;

    private MyExpandableListViewAdapter adapter;

    private Context context;

    private Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        initData();

        initButtonView();

        initExpandableListView();


    }

    private void initData(){
        // 随便一堆测试数据
        group_list = new ArrayList<String>();
        group_list.add("我的好友");
        group_list.add("我的家人");
        group_list.add("兄弟姐妹");
        group_list.add("我的同学");

        List<String> item_lt = new ArrayList<String>();
        item_lt.add("张三丰");
        item_lt.add("董存瑞");
        item_lt.add("李大钊");
        item_lt.add("张三丰");
        item_lt.add("董存瑞");


        item_list = new ArrayList<List<String>>();
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(new ArrayList<String>());//添加一个空的


        List<Boolean>  select_item1 = new ArrayList<Boolean>();
        select_item1.add(false);
        select_item1.add(false);
        select_item1.add(true);
        select_item1.add(false);
        select_item1.add(false);

        List<Boolean>  select_item2 = new ArrayList<Boolean>();
        select_item2.add(false);
        select_item2.add(false);
        select_item2.add(true);
        select_item2.add(false);
        select_item2.add(false);

        List<Boolean>  select_item3 = new ArrayList<Boolean>();
        select_item3.add(false);
        select_item3.add(false);
        select_item3.add(true);
        select_item3.add(false);
        select_item3.add(false);


        select_list = new ArrayList<List<Boolean>>();
        select_list.add(select_item1);
        select_list.add(select_item2);
        select_list.add(select_item3);
    }


    private void initButtonView(){
        btn_ok = (Button)findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                Iterator <List<Boolean>> iterator = select_list.iterator();
                while (iterator.hasNext()){
                    List<Boolean> list = iterator.next();
                    Iterator<Boolean>  iterator1 = list.iterator();
                    while (iterator1.hasNext()){
                        Boolean flag = iterator1.next();
                        if(flag){
                            count ++;
                        }
                    }
                }
                Toast.makeText(context,"你一共选择了" + count +"项" ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initExpandableListView(){
        expandableListView = (ExpandableListView) findViewById(R.id.expendlist);

        expandableListView.setGroupIndicator(null);//左边的指示剪头

        // 监听组点击
        expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (item_list.get(groupPosition).isEmpty()) {
                    return true;//return true if the click was handled
                }

                if (groupPosition == groupSelectIndex) {
                    return true;
                }
                return false;
            }
        });

        // 监听每个分组里子控件的点击事件
        expandableListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                boolean original = select_list.get(groupPosition).get(childPosition);
                boolean nowFlag = !original;
                select_list.get(groupPosition).set(childPosition , nowFlag);
                CheckBox checkBox = (CheckBox)v.findViewById(R.id.cb_select);
                if(nowFlag){
                    checkBox.setChecked(true);
                }else {
                    checkBox.setChecked(false);
                }

                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != groupSelectIndex) {
                    expandableListView.collapseGroup(groupSelectIndex);
                    groupSelectIndex = groupPosition;
                }
            }
        });

        adapter = new MyExpandableListViewAdapter(this);

        expandableListView.setAdapter(adapter);

        expandableListView.expandGroup(groupSelectIndex);
    }

    // 用过ListView的人一定很熟悉，只不过这里是BaseExpandableListAdapter
    class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

        private Context context;

        public MyExpandableListViewAdapter(Context context) {
            this.context = context;
        }

        /**
         * 获取组的个数
         *
         * @return
         * @see android.widget.ExpandableListAdapter#getGroupCount()
         */
        @Override
        public int getGroupCount() {
            return group_list.size();
        }

        /**
         * 获取指定组中的子元素个数
         *
         * @param groupPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
         */
        @Override
        public int getChildrenCount(int groupPosition) {
            return item_list.get(groupPosition).size();
        }

        /**
         * 获取指定组中的数据
         *
         * @param groupPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getGroup(int)
         */
        @Override
        public Object getGroup(int groupPosition) {
            return group_list.get(groupPosition);
        }

        /**
         * 获取指定组中的指定子元素数据。
         *
         * @param groupPosition
         * @param childPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getChild(int, int)
         */
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return item_list.get(groupPosition).get(childPosition);
        }

        /**
         * 获取指定组的ID，这个组ID必须是唯一的
         *
         * @param groupPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getGroupId(int)
         */
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        /**
         * 获取指定组中的指定子元素ID
         *
         * @param groupPosition
         * @param childPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getChildId(int, int)
         */
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        /**
         * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
         *
         * @return
         * @see android.widget.ExpandableListAdapter#hasStableIds()
         */
        @Override
        public boolean hasStableIds() {
            return true;
        }

        /**
         * 获取显示指定组的视图对象
         *
         * @param groupPosition 组位置
         * @param isExpanded    该组是展开状态还是伸缩状态
         * @param convertView   重用已有的视图对象
         * @param parent        返回的视图对象始终依附于的视图组
         * @return
         * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View,
         * android.view.ViewGroup)
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.expendlist_group, null);
                groupHolder = new GroupHolder();
                groupHolder.txt = (TextView) convertView.findViewById(R.id.txt);
                groupHolder.img = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }

            if (!isExpanded) {
                groupHolder.img.setBackgroundResource(R.drawable.filter_ico_drill);
            } else {
                groupHolder.img.setBackgroundResource(R.drawable.setting_language_arrow_down);
            }

            groupHolder.txt.setText(group_list.get(groupPosition));
            return convertView;
        }

        /**
         * 获取一个视图对象，显示指定组中的指定子元素数据。
         *
         * @param groupPosition 组位置
         * @param childPosition 子元素位置
         * @param isLastChild   子元素是否处于组中的最后一个
         * @param convertView   重用已有的视图(View)对象
         * @param parent        返回的视图(View)对象始终依附于的视图组
         * @return
         * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
         * android.view.ViewGroup)
         */
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ItemHolder itemHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.expendlist_item, null);
                itemHolder = new ItemHolder();
                itemHolder.txt = (TextView) convertView.findViewById(R.id.txt);
                itemHolder.select = (CheckBox) convertView.findViewById(R.id.cb_select);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            itemHolder.txt.setText(item_list.get(groupPosition).get(childPosition));

            if(select_list.get(groupPosition).get(childPosition)){
                itemHolder.select.setChecked(true);
            }else {
                itemHolder.select.setChecked(false);
            }
//            itemHolder.img.setBackgroundResource(item_list2.get(groupPosition).get(childPosition));
            return convertView;
        }

        /**
         * 是否选中指定位置上的子元素。
         *
         * @param groupPosition
         * @param childPosition
         * @return
         * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
         */
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    class GroupHolder {
        public TextView txt;

        public ImageView img;
    }

    class ItemHolder {
        public CheckBox select;

        public TextView txt;
    }

}