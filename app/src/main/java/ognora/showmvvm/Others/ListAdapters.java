package ognora.showmvvm.Others;


import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;

import ognora.showmvvm.Model.FileModel;
import ognora.showmvvm.R;
import ognora.showmvvm.View.DisplayActivity;

/**
 * Created by hp on 16/05/2019.
 */

public class ListAdapters extends RecyclerView.Adapter<ListAdapters.MyViewHolder >{

    LiveData<ArrayList<FileModel>> arrayList ;
    Context context ;
    int type;

    public ListAdapters(LiveData<ArrayList<FileModel>> arrayList, int type, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        return  type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        if(viewType == 1)
        { v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subfolder, parent, false); }

        else
        { v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false); }

        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if(type==1) {

            holder.textView.setText(arrayList.getValue().get(position).getName());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DisplayActivity.class);
                    intent.putExtra("File", arrayList.getValue().get(position).getPath());
                    intent.putExtra("Folderid", arrayList.getValue().get(position).getId());
                    context.startActivity(intent);

                }
            });
        }
        else {

           //convert file to bitmap to set in image view

            Uri imageUri = Uri.fromFile(arrayList.getValue().get(position).getFile());

            Glide.with(context)
                    .load(imageUri)
                    .into(holder.imageview);

            holder.imageViewdel.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);

                    // LayoutInflater inflater = ((DisplayActivity) context).getLayoutInflater();
                    LayoutInflater inflater = (LayoutInflater)context.getSystemService
                            (Context.LAYOUT_INFLATER_SERVICE);

                    View view1 = inflater.inflate(R.layout.alert_delete, null) ;
                    dialogbuilder.setView(view1);

                    final AlertDialog alertDialog = dialogbuilder.create();
                    alertDialog.show();

                    Button byes;
                    Button bno;

                    byes = view1.findViewById(R.id.delete_yes);
                    bno = view1.findViewById(R.id.delete_no);

                    byes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // delete file from storage

                            ((DisplayActivity)context).viewModel.del(arrayList.getValue().get(position));
                            boolean l = arrayList.getValue().get(position).getFile().delete();


                        if(l) {
                            arrayList.getValue().remove(position);
                            notifyDataSetChanged();
                        } else
                            Toast.makeText(context, "Cannot DELETE", Toast.LENGTH_SHORT);

                            alertDialog.dismiss();

                        }
                    });

                    bno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            alertDialog.dismiss();


                        }
                    });


                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return (arrayList.getValue().size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageview;
        LinearLayout linearLayout;
        ImageView imageViewdel ;


        public MyViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.name);
            imageview = itemView.findViewById(R.id.img);
            linearLayout = itemView.findViewById(R.id.detail);
            imageViewdel = itemView.findViewById(R.id.delete_Img);

        }


    }


}
