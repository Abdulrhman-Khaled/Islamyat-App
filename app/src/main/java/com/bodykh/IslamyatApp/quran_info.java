package com.bodykh.IslamyatApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class quran_info extends ArrayAdapter<Sora> {

    Context context;
    int resource;

    public quran_info(@NonNull Context context, int resource, @NonNull List<Sora> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        TextView en = convertView.findViewById(R.id.soranameen);
        TextView ar = convertView.findViewById(R.id.soranamear);
        Sora soraname = getItem(position);
        en.setText(soraname.getEnsora());
        ar.setText(soraname.getArsora());
        return convertView;
    }
}
