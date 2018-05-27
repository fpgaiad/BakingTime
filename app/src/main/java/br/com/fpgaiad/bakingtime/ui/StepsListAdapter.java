package br.com.fpgaiad.bakingtime.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.Step;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsListViewHolder> {

    private List<Step> stepList;

    public StepsListAdapter(List<Step> stepList) {
        this.stepList = stepList;
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
        holder.stepName.setText(step.getShortDescription());

        if (position != 0) {
            holder.stepLabel.setVisibility(View.VISIBLE);
            holder.stepIndex.setVisibility(View.VISIBLE);
            holder.stepIndex.setText(String.valueOf(step.getId()));

        } else {
            holder.stepName.setAllCaps(true);
            holder.stepName.setTypeface(Typeface.DEFAULT_BOLD);
        }

    }

    @Override
    public int getItemCount() {
        return stepList == null ? 0 : stepList.size();
    }

    class StepsListViewHolder extends RecyclerView.ViewHolder {

        TextView stepIndex;
        TextView stepLabel;
        TextView stepName;

        public StepsListViewHolder(View itemView) {
            super(itemView);

            stepName = itemView.findViewById(R.id.tv_step_name);
            stepLabel = itemView.findViewById(R.id.tv_step_label);
            stepIndex = itemView.findViewById(R.id.tv_step_index);
        }
    }
}
