package in.ajaysrinivas.basicadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;


import java.util.ArrayList;

/**
 * Created by Ajay Srinivas on 3/16/2018.
 */

public class BasicAdapter<M, B> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<M> list;
    int layout;
    private Binder<M, B> binder;

    public static <M, B> BasicAdapter with(int res, ArrayList<M> list, final Binder<M, B> binder) {
        return new BasicAdapter<M, B>(list, res, binder);
    }

    public static <M, B> BasicAdapter with(int res, ArrayList<M> list, final SmartBinder<M, B> binder) {
        return new BasicAdapter<M, B>(list, res, binder);
    }

    public interface Binder<M, B> {
        void onBind(int position, M model, B binding);
    }

    public static abstract class SmartBinder<M, B> implements Binder<M, B> {
        public void onClick(View view, M model, int position) {

        }

        public void onCheckedChange(View view, M model, int position) {

        }
    }

    public BasicAdapter(ArrayList<M> list, int layout, Binder<M, B> binder) {
        this.list = list;
        this.layout = layout;
        this.binder = binder;
    }

    int[] clickableIds;

    public BasicAdapter setClickableViews(int... ids) {
        this.clickableIds = ids;
        return this;
    }

    int[] checkableIds;

    public BasicAdapter setCheckableViews(int... ids) {
        this.checkableIds = ids;
        return this;
    }

    @Override
    public BasicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BasicViewHolder viewHolder = new BasicViewHolder(parent, layout);
        if(binder instanceof SmartBinder) {
            if (clickableIds != null) {
                for(int clickableId : clickableIds) {
                    viewHolder.binding.getRoot().findViewById(clickableId).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((SmartBinder) binder).onClick(view, list.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                        }
                    });
                }
            }

            if (checkableIds != null) {
                for(int checkableId : checkableIds) {
                    View view = viewHolder.binding.getRoot().findViewById(checkableId);
                    if (view instanceof CompoundButton) {
                        ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                ((SmartBinder) binder).onCheckedChange(compoundButton, list.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                            }
                        });
                    }
                }
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        binder.onBind(holder.getAdapterPosition(), list.get(position), (B) ((BasicViewHolder) holder).binding);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class BasicViewHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding binding;

        public BasicViewHolder(ViewGroup parent, int res) {
            super(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
