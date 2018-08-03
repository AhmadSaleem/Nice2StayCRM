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

import org.w3c.dom.Text;

import java.util.List;

import co.thedevden.nice2staycrm.R;
import co.thedevden.nice2staycrm.model.AccomodationModel;
import co.thedevden.nice2staycrm.model.PromotionModel;
import co.thedevden.nice2staycrm.view.AccomodationsItem;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.MyViewHolder> {

    Context context;
    private List<PromotionModel> mypromotionslist;

    RequestOptions options;

    public PromotionAdapter(Context context, List<PromotionModel> promo) {
        this.context = context;
        this.mypromotionslist = promo;
    }

    @NonNull
    @Override
    public PromotionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

        View view;
        final LayoutInflater inflater = LayoutInflater.from(context);
        view=inflater.inflate(R.layout.promotion_list_item,viewGroup,false);
        final PromotionAdapter.MyViewHolder viewHolder = new PromotionAdapter.MyViewHolder(view);
        viewHolder.mycardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context,Promo.class);
//                intent.putExtra("promo_Id",mypromotionslist.get(viewHolder.getAdapterPosition()).getId());
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);

            }
        });


        return viewHolder;

    }



    @Override
    public void onBindViewHolder(@NonNull PromotionAdapter.MyViewHolder myViewHolder, int i) {

        myViewHolder.accomoName.setText(mypromotionslist.get(i).getShort_desc());
        myViewHolder.promoDiscount.setText(String.valueOf(mypromotionslist.get(i).getValue()));

        
        String from_date = mypromotionslist.get(i).getFrom();
        String to_date = mypromotionslist.get(i).getTo();
        String valid_date = mypromotionslist.get(i).getValid_to();

        myViewHolder.from_date.setText(from_date.substring(0, Math.min(from_date.length(), 10)));
        myViewHolder.to_date.setText(to_date.substring(0, Math.min(to_date.length(), 10)));
        myViewHolder.valid_To.setText(valid_date.substring(0, Math.min(valid_date.length(), 10)));
        myViewHolder.description.setText(mypromotionslist.get(i).getDescription());


    }

    @Override
    public int getItemCount() {
        return mypromotionslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView accomoName;
        TextView promoDiscount;
        TextView from_date;
        CardView mycardview;
        TextView to_date;
        TextView valid_To;
        TextView description;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mycardview = (CardView) itemView.findViewById(R.id.my_container_promotion);
            accomoName = (TextView) itemView.findViewById(R.id.name_Accomo_Promotion);
            promoDiscount = (TextView) itemView.findViewById(R.id.discount_promotoion);
            from_date = (TextView) itemView.findViewById(R.id.from_date_promotion);
            to_date = (TextView) itemView.findViewById(R.id.to_date_promotion);
            valid_To = (TextView) itemView.findViewById(R.id.valid_date_accomotion);
            description = (TextView) itemView.findViewById(R.id.description_promotion);

        }
    }
}
