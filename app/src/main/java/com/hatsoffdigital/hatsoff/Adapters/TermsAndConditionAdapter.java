package com.hatsoffdigital.hatsoff.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.Termsandcondition;
import com.hatsoffdigital.hatsoff.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TermsAndConditionAdapter extends RecyclerView.Adapter<TermsAndConditionAdapter.TermCondViewHolder> {

    Context context;
    ArrayList<Termsandcondition> getTermsandCondition;
    String status;

    public TermsAndConditionAdapter(Context context, ArrayList<Termsandcondition> getTermsandCondition, String status) {
        this.context = context;
        this.getTermsandCondition = getTermsandCondition;
        this.status = status;
    }

    @NonNull
    @Override
    public TermCondViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.term_condition_view,parent,false);
        TermCondViewHolder holder=new TermCondViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TermCondViewHolder holder, int position) {

        holder.txt_subject.setText(getTermsandCondition.get(position).getTc_subject());
        holder.txt_description.setText(getTermsandCondition.get(position).getTc_msg());

        if(getTermsandCondition.get(position).getTc_subject().equals(status))
        {
           holder.rel_hide_desc.setVisibility(View.VISIBLE);
        }

    }



    @Override
    public int getItemCount() {
        return getTermsandCondition.size();
    }

    public class TermCondViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
     TextView txt_subject,txt_termcond_plus,txt_description;

    RelativeLayout rel_plus,rel_termcond_title,rel_hide_desc;



        public TermCondViewHolder(View itemView) {
            super(itemView);

        rel_plus=(RelativeLayout)itemView.findViewById(R.id.rel_plus);
        txt_subject=(TextView)itemView.findViewById(R.id.txt_subject);
        txt_description=(TextView)itemView.findViewById(R.id.txt_description);
        txt_termcond_plus=(TextView)itemView.findViewById(R.id.txt_termcond_plus);
        rel_termcond_title=(RelativeLayout)itemView.findViewById(R.id.rel_termcond_title);
        rel_termcond_title.setOnClickListener(this);
        rel_hide_desc=(RelativeLayout)itemView.findViewById(R.id.rel_hide_desc);

        //rel_plus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

       int id=view.getId();
       if(id==rel_termcond_title.getId())
          {


              if (rel_hide_desc.getVisibility()==View.GONE) {


                rel_hide_desc.setVisibility(View.VISIBLE);


                txt_subject.setTextColor(context.getResources().getColor(R.color.white));
                txt_termcond_plus.setTextColor(context.getResources().getColor(R.color.white));
                txt_termcond_plus.setText("-");
                rel_termcond_title.setBackground(context.getResources().getDrawable(R.drawable.blue_border));

            }
            else
            {


                rel_hide_desc.setVisibility(View.GONE);
                txt_subject.setTextColor(context.getResources().getColor(R.color.HOBlue));
                txt_termcond_plus.setTextColor(context.getResources().getColor(R.color.HOBlue));
                txt_termcond_plus.setText("+");
                rel_termcond_title.setBackground(context.getResources().getDrawable(R.drawable.simple_border));

            }

        }

    }

 }
}
