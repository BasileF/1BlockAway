package com.learning.a1blockaway;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ObservableCollection mData;
    private LayoutInflater mInflater;
    private Context mContext;

    RecyclerViewAdapter(Context context, ObservableCollection data){
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        if(data!=null) {
            data.setListener(new ObservableCollection.ChangeListener() {
                @Override
                public void onChange() {
                    if (mData.operation == ObservableCollection.Operation.ADD) {
                        RecyclerViewAdapter.this.notifyDataSetChanged();
                    } else {
                        RecyclerViewAdapter.this.notifyItemRemoved(mData.index);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request request = mData.get(position);
        holder.name.setText(request.getName());
        holder.gender.setText("Gender: " + request.getGender());
        holder.age.setText("Age: " + request.getAge());
        holder.height.setText("Height: " + request.getHeight() + "cm");
        holder.information.setText("Information: " + request.getType());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView gender;
        TextView age;
        TextView height;
        TextView information;

        private Button accept;

        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.row_name);
            gender = itemView.findViewById(R.id.row_gender);
            age = itemView.findViewById(R.id.row_age);
            height = itemView.findViewById(R.id.row_height);
            information = itemView.findViewById(R.id.row_info);
            accept = itemView.findViewById(R.id.row_accept);
            if(User.getAccepted()){
                accept.setText("IN PROGRESS");
                accept.setBackgroundColor(itemView.getResources().getColor(R.color.primaryColor));
            }else{
                accept.setText("ACCEPT");
                accept.setBackgroundColor(itemView.getResources().getColor(R.color.secondaryLightColor));
            }
            accept.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Request requester = mData.get(getAdapterPosition());
            User.setRequestor(requester);
            FirebaseDatabase.getInstance().getReference("/Users/" + User.getUserID()).child("Accepted").setValue(true);
            FirebaseDatabase.getInstance().getReference("/Requests/" + requester.getUserID() + "/Helper/").setValue(User.getUserID());
            User.setAccepted(true);
            accept.setText("IN PROGRESS");
            accept.setBackgroundColor(itemView.getResources().getColor(R.color.primaryColor));
            Intent intent = new Intent(mContext, CurrentRespondent.class);
            mContext.startActivity(intent);
        }
    }
}
