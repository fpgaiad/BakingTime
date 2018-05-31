package br.com.fpgaiad.bakingtime.ui.recipe_detail.adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.Step;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsListViewHolder> {

    private List<Step> stepList;
    private StepsListItemClickListener mOnClickListener;

    public StepsListAdapter(List<Step> stepList, StepsListItemClickListener listener) {
        this.stepList = stepList;
        this.mOnClickListener = listener;
    }

    @NonNull
    @Override
    public StepsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_list_item, parent, false);

        return new StepsListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull StepsListViewHolder holder, int position) {
        Step step = stepList.get(position);
        //Deleting the final dot from "short description"
        String fixedShortDescription = fixShortDescription(step.getShortDescription());
        holder.stepName.setText(fixedShortDescription);

        if (position != 0) {
            holder.stepLabel.setVisibility(View.VISIBLE);
            holder.stepIndex.setVisibility(View.VISIBLE);
            holder.stepIndex.setText(String.valueOf(position));

        } else {
            holder.stepName.setAllCaps(true);
            holder.stepName.setTypeface(Typeface.DEFAULT_BOLD);
            holder.stepName.setTextColor(Color.parseColor("#777777"));
        }

    }

    private String fixShortDescription(String shortDescription) {
        int lastCutIndex = shortDescription.lastIndexOf(".");
        if (lastCutIndex != (-1)) {
            return shortDescription.substring(0, lastCutIndex);
        }
        return shortDescription;
    }

    @Override
    public int getItemCount() {
        return stepList == null ? 0 : stepList.size();
    }

    class StepsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView stepIndex;
        TextView stepLabel;
        TextView stepName;

        public StepsListViewHolder(View itemView) {
            super(itemView);

            stepName = itemView.findViewById(R.id.tv_step_name);
            stepLabel = itemView.findViewById(R.id.tv_step_label);
            stepIndex = itemView.findViewById(R.id.tv_step_index);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onListItemClick(getAdapterPosition());
        }


    }
    public interface StepsListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}
