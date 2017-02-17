package cn.cassan.pm.myphonecontact;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Created by zhihao on 2016/10/29.
 * @describe
 * @version_
 **/
public abstract class PhoneBooksAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    private ArrayList<PhoneInfo> items = new ArrayList<PhoneInfo>();

    public PhoneBooksAdapter() {
        setHasStableIds(true);
    }

    public void add(PhoneInfo object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, PhoneInfo object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends PhoneInfo> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(PhoneInfo... items) {
        addAll(Arrays.asList(items));
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(String object) {
        items.remove(object);
        notifyDataSetChanged();
    }

    public PhoneInfo getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}