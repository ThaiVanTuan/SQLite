package com.example.sqlite;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

        import java.util.List;

public class ComputerAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<Computer> computerList;

    public ComputerAdapter(MainActivity context, int layout, List<Computer> computerList) {
        this.context = context;
        this.layout = layout;
        this.computerList = computerList;
    }

    @Override
    public int getCount() {
        return computerList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    private class ViewHolder
    {
        TextView txtTen;
        ImageView imgDelete, imgEdit;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.txtTen = (TextView) convertView.findViewById(R.id.textviewTen);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imageviewDelete);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.imageviewEdit);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Computer computer = computerList.get(position);
        holder.txtTen.setText(computer.getTenPC());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaComputer(computer.getTenPC(),computer.getIdPC());
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaPC(computer.getTenPC(),computer.getIdPC());
            }
        });

        return convertView;
    }
}
