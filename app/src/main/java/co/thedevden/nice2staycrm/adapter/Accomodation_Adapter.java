package co.thedevden.nice2staycrm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import co.thedevden.nice2staycrm.R;
import co.thedevden.nice2staycrm.model.AccomodationModel;
import co.thedevden.nice2staycrm.view.AccomodationsItem;

public class Accomodation_Adapter extends RecyclerView.Adapter<Accomodation_Adapter.MyViewHolder>{

    Context context;
    private List<AccomodationModel> myaccomodationList;

    RequestOptions options;

    public Accomodation_Adapter(Context context, List<AccomodationModel> accomo) {
        this.context = context;
        this.myaccomodationList = accomo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

        View view;
        final LayoutInflater inflater = LayoutInflater.from(context);
        view=inflater.inflate(R.layout.accomodation_list_item,viewGroup,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.mycardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AccomodationsItem.class);
                intent.putExtra("accomo_Id",myaccomodationList.get(viewHolder.getAdapterPosition()).getId());
                intent.putExtra("accomo_Name",myaccomodationList.get(viewHolder.getAdapterPosition()).getName());
                intent.putExtra("accomo_Country",myaccomodationList.get(viewHolder.getAdapterPosition()).getCountry_name());
                intent.putExtra("accomo_Type",myaccomodationList.get(viewHolder.getAdapterPosition()).getType());
                intent.putExtra("accomo_Region",myaccomodationList.get(viewHolder.getAdapterPosition()).getRegion_name());
                intent.putExtra("accomo_PersonNo",myaccomodationList.get(viewHolder.getAdapterPosition()).getPersons_number());
                intent.putExtra("accomo_Listed_To",myaccomodationList.get(viewHolder.getAdapterPosition()).getListed_to());


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


        return viewHolder;

    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.accomoName.setText(myaccomodationList.get(i).getName());
        myViewHolder.accmoCountry.setText("Country: " + myaccomodationList.get(i).getCountry_name());
        myViewHolder.accomoType.setText("Type: " + myaccomodationList.get(i).getType());

    }

    @Override
    public int getItemCount() {
        return myaccomodationList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView accomoName;
        TextView accmoCountry;
        TextView accomoType;
        CardView mycardview;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mycardview = (CardView) itemView.findViewById(R.id.my_container_accomodation);
            accomoName = (TextView) itemView.findViewById(R.id.Name_accomodation);
            accmoCountry = (TextView) itemView.findViewById(R.id.country_accomodation);
            accomoType = (TextView) itemView.findViewById(R.id.type_accomodation);

        }
    }
}