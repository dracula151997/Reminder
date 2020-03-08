package com.project.semicolon.reminder.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.project.semicolon.reminder.listeners.OnListItemClickListener;

import java.util.List;

public class GenericAdapter<T extends ListItemViewModel> extends
        RecyclerView.Adapter<GenericAdapter<T>.GenericViewHolder<T>> implements SwipeController.ItemTouchHelperViewHolder {
    private List<T> items;
    @LayoutRes
    private int layoutRes;
    private LayoutInflater layoutInflater;
    private OnListItemClickListener onListItemClickListener;

    public GenericAdapter(int layoutRes) {
        this.layoutRes = layoutRes;
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    @NonNull
    @Override
    public GenericViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding dataBinding = DataBindingUtil
                .inflate(layoutInflater, layoutRes, parent, false);
        return new GenericViewHolder<>(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder<T> holder, int position) {
        T t = items.get(position);
        t.adapterPosition = position;
        t.onListItemClickListener = onListItemClickListener;
        holder.bind(t);

    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    @Override
    public void onClearView() {

    }

    public void deleteItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position, T item) {
        items.add(position, item);
        notifyItemChanged(position);
    }

    public class GenericViewHolder<T> extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public GenericViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(T t) {
            binding.setVariable(com.project.semicolon.reminder.BR.obj, t);
            binding.executePendingBindings();
        }
    }
}
