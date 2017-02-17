package cn.cassan.pm.wheelPicker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import cn.cassan.pm.R;
import cn.cassan.pm.model.City;
import cn.cassan.pm.model.Province;

import static cn.cassan.pm.wheelPicker.WheelAreaPicker.PROVINCE_INITIAL_INDEX;

/**
 * @author Created by zhihao on 2016/10/27.
 * @describe
 * @version_
 **/
public class WheelAreaPopWindow extends PopupWindow implements View.OnClickListener {


    private WheelAreaPicker wheelAreaPicker;
    private Button btn_confirm;
    private ArrayList<Province> provinceList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
    private Context mContext;
    private List<String> mProvinceName, mCityName;
    private SelectAreaListener listener;

    public WheelAreaPopWindow(Context context) {

        View view = View.inflate(context, R.layout.wheelareapicker_pop, null);
        setContentView(view);
        this.mContext = context;
        initView(view);

        mProvinceName = new ArrayList<>();
        mCityName = new ArrayList<>();
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);


    }

    public void setOnSelectDataListener(SelectAreaListener listener) {
        this.listener = listener;
    }

    public void obtainProvinceData() {
        for (Province province : wheelAreaPicker.mProvinceList) {
            mProvinceName.add(province.getProvincename());
        }
        wheelAreaPicker.mWPProvince.setData(mProvinceName);
        setCityData(PROVINCE_INITIAL_INDEX);
    }

    private void setCityData(int position) {
        //获得该省所有城市的集合
        wheelAreaPicker.mCityList = wheelAreaPicker.mProvinceList.get(position).getCityList();
        //获取所有city的名字
        //重置先前的城市集合数据
        mCityName.clear();
        for (City city : wheelAreaPicker.mCityList)
            mCityName.add(city.getCityname());
        wheelAreaPicker.mWPCity.setData(mCityName);
        wheelAreaPicker.mWPCity.setSelectedItemPosition(0);

    }

    public void setData(ArrayList<Province> provinceList) {
        //读取保存的数据
        wheelAreaPicker.mProvinceList = provinceList;
        obtainProvinceData();
    }

    public List<Province> getData() {

        return wheelAreaPicker.mProvinceList == null ? null : wheelAreaPicker.mProvinceList;
    }

    private void initView(View v) {

        wheelAreaPicker = (WheelAreaPicker) v.findViewById(R.id.WheelAreaPicker);
        v.findViewById(R.id.btn_confirm).setOnClickListener(this);
        v.findViewById(R.id.area_emptyview).setOnClickListener(this);


    }

    @Override
    public String toString() {

        return wheelAreaPicker.getProvince() + "-" + wheelAreaPicker.getCity();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_confirm:
                listener.confirm(wheelAreaPicker.getProvice_name(), wheelAreaPicker.getCity_name(), wheelAreaPicker.getProvince_id(), wheelAreaPicker.getCity_id());
                dismiss();
                break;
            case R.id.area_emptyview:
                dismiss();
                break;
        }
    }

    public interface SelectAreaListener {

        public void confirm(String provice_name, String city_name, int provice_id, int city_id);
    }
}
