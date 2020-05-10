package helloworld.mmu.ac.uk.resturantrating;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> mResturantData = new ArrayList<>();
    private ArrayList<Integer> images = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mResturantData, ArrayList<Integer> images, Context context) {
        this.mResturantData = mResturantData;
        this.images = images;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.img.setImageResource(images.get(position));
        holder.dataName.setText(mResturantData.get(position));
    }

    @Override
    public int getItemCount() {
        return mResturantData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout parentLayout;
        TextView dataName;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            dataName = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.image);
        }
    }
}



