package cn.cassan.pm.wheelPicker;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

import cn.cassan.pm.model.City;
import cn.cassan.pm.model.Province;

/**
 * @author Created by zhihao on 2016/10/26.
 * @describe
 * @version_
 **/
public class WheelAreaPicker extends LinearLayout implements IWheelAreaPicker {

    private static final float ITEM_TEXT_SIZE = 18;
    private static final String SELECTED_ITEM_COLOR = "#353535";
    public static final int PROVINCE_INITIAL_INDEX = 0;

    private Context mContext;

    public List<Province> mProvinceList;
    public List<City> mCityList;
    private List<String> mProvinceName, mCityName;

    private String provice_name, city_name;
    private int province_id = 0, city_id = 0;
    private LayoutParams mLayoutParams;

    public WheelPicker mWPProvince, mWPCity;

    public WheelAreaPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        initLayoutParams();

        initView(context);

        addListenerToWheelPicker();
    }


    private void initLayoutParams() {
        mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.setMargins(5, 5, 5, 5);
        mLayoutParams.width = 0;
    }

    private void initView(Context context) {

        setOrientation(HORIZONTAL);
        this.mContext = context;

        mProvinceName = new ArrayList<>();
        mCityName = new ArrayList<>();

        mWPProvince = new WheelPicker(context);
        mWPCity = new WheelPicker(context);


        initWheelPicker(mWPProvince, 1);
        initWheelPicker(mWPCity, 1.5f);

    }

    private void initWheelPicker(WheelPicker wheelPicker, float weight) {
        mLayoutParams.weight = weight;
        wheelPicker.setItemTextSize(dip2px(mContext, ITEM_TEXT_SIZE));
        wheelPicker.setSelectedItemTextColor(Color.parseColor(SELECTED_ITEM_COLOR));
        wheelPicker.setCurved(true);
        wheelPicker.setLayoutParams(mLayoutParams);
        addView(wheelPicker);
    }


    private void addListenerToWheelPicker() {
        //监听省份的滑轮,根据省份的滑轮滑动的数据来设置市跟地区的滑轮数据
        mWPProvince.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                //获得该省所有城市的集合
                mCityList = mProvinceList.get(position).getCityList();
                //                province_id = mProvinceList.get(position).getProvinceid();
                setCityData(position);
            }
        });

        mWPCity.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                //获取城市对应的城区的名字
                //                mWPArea.setData(mCityList.get(position).getArea());
                //                city_id = mCityList.get(position).getCityid();
            }
        });
    }

    private void setCityData(int position) {
        //获得该省所有城市的集合
        mCityList = mProvinceList.get(position).getCityList();
        //获取所有city的名字
        //重置先前的城市集合数据
        mCityName.clear();
        for (City city : mCityList)
            mCityName.add(city.getCityname());
        mWPCity.setData(mCityName);
        mWPCity.setSelectedItemPosition(0);

    }


    public String getProvice_name() {
        return provice_name = mProvinceList.get(mWPProvince.getCurrentItemPosition()).getProvincename();

    }

    public String getCity_name() {
        return city_name = mCityList.get(mWPCity.getCurrentItemPosition()).getCityname();
    }

    public int getCity_id() {
        return city_id = mCityList.get(mWPCity.getCurrentItemPosition()).getCityid();
    }

    public int getProvince_id() {
        return province_id = mProvinceList.get(mWPProvince.getCurrentItemPosition()).getProvinceid();
    }

    @Override
    public String getProvince() {

        return provice_name = mProvinceList.get(mWPProvince.getCurrentItemPosition()).getProvincename();

    }

    @Override
    public String getCity() {

        return city_name = mCityList.get(mWPCity.getCurrentItemPosition()).getCityname();
    }


    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}