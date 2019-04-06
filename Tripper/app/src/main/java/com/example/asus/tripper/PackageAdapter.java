package com.example.asus.tripper;



import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.asus.tripper.R;

import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {

    private Context mCtx;

    private List<com.example.asus.tripper.Package> packageList;
    Dialog myDialog;


    public PackageAdapter(Context mCtx,List<com.example.asus.tripper.Package> productList){
        this.mCtx = mCtx;
        this.packageList = productList;


    }




    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.home_fragment_recyclerview,null);

        final PackageViewHolder pHolder =new PackageViewHolder(view);

        myDialog = new Dialog(mCtx);
        myDialog.setContentView(R.layout.cad_menu_options);
        TextView dialog_report_tv = (TextView) myDialog.findViewById(R.id.reportOption);
        TextView dialog_track_tv = (TextView) myDialog.findViewById(R.id.trackOption);
        TextView dialog_travellers_tv = (TextView) myDialog.findViewById(R.id.travellersOption);
        TextView dialog_guide_tv = (TextView) myDialog.findViewById(R.id.guidesOption);
        TextView dialog_mark_complete_tv = (TextView) myDialog.findViewById(R.id.markComplete);

        dialog_report_tv.setText("Report...");
        dialog_track_tv.setText("Track");
        dialog_travellers_tv.setText("View Travellers");
        dialog_guide_tv.setText("View Guides");
        dialog_mark_complete_tv.setText("Mark Complete");


        pHolder.radioButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.show();
            }
        });






        pHolder.radioButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.show();
            }
        });






        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PackageViewHolder productViewHolder, int i) {

        com.example.asus.tripper.Package product = packageList.get(i);
        productViewHolder.imageViewTitle.setImageDrawable(mCtx.getResources().getDrawable(product.getImageTitle()));
        productViewHolder.textViewTitle.setText(product.getTitle());
        productViewHolder.imageViewPackage.setImageDrawable(mCtx.getResources().getDrawable(product.getImagePackage()));
        productViewHolder.textViewPackage.setText(product.getPackageTitle());
        productViewHolder.textViewShortDesc.setText(product.getShortDesc());






    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }


    class PackageViewHolder extends RecyclerView.ViewHolder{


        TextView textViewTitle,textViewShortDesc,textViewPackage;
        ImageView imageViewPackage,imageViewTitle;
        RadioButton radioButtonMenu;

        public PackageViewHolder(View itemView){

            super((itemView));


            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewPackage = itemView.findViewById(R.id.textViewPackage);
            imageViewTitle =  itemView.findViewById(R.id.imageViewTitle);
            imageViewPackage =  itemView.findViewById(R.id.imageViewPackage);
            radioButtonMenu = itemView.findViewById(R.id.radio_menu);



        }
    }
}
