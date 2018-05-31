package br.com.fpgaiad.bakingtime.ui.step_detail;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.Recipe;
import br.com.fpgaiad.bakingtime.entities.Step;

public class RecipeStepsFragment extends Fragment {

    private Step mStep;
    private Recipe mRecipe;
    private int mStepIndex;
    private List<Step> stepList;

    // Empty constructor required
    public RecipeStepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater
                .inflate(R.layout.fragment_recipe_steps, container, false);

        ImageView imageMedia = rootView.findViewById(R.id.iv_media);
        TextView stepTitle = rootView.findViewById(R.id.tv_step_title_sequence);
        TextView stepPreLabelSequence = rootView.findViewById(R.id.tv_step_pre_label_sequence);
        TextView stepPostLabelSequence = rootView.findViewById(R.id.tv_step_post_label_sequence);
        TextView stepDescription = rootView.findViewById(R.id.tv_step_description_sequence);
        TextView divisor = rootView.findViewById(R.id.tv_step_divisor_label_sequence);
        View linearLayoutButton = rootView.findViewById(R.id.linear_layout_button);
        Button nextButtonSolo = rootView.findViewById(R.id.btn_next_solo);
        Button prevButtonSolo = rootView.findViewById(R.id.btn_prev_solo);

        //Getting the list of steps
        stepList = mRecipe.getSteps();

        //Hiding the step number in the text "description"
        String fixedDescription = fixDescription();

        //Hiding the last dot in the text "short description"
        String fixedShortDescription = fixShortDescription();

        if (mStepIndex == 0) {
            //Hiding step sequence and recipe description in "Recipe Introduction" layouts
            stepPreLabelSequence.setVisibility(View.GONE);
            stepPostLabelSequence.setVisibility(View.GONE);
            divisor.setVisibility(View.GONE);
            stepDescription.setVisibility(View.GONE);
            linearLayoutButton.setVisibility(View.GONE);
            nextButtonSolo.setVisibility(View.VISIBLE);
        }

        if (mStepIndex == (stepList.size() - 1)) {
            linearLayoutButton.setVisibility(View.GONE);
            prevButtonSolo.setVisibility(View.VISIBLE);
        }

        //Setting values
        stepPreLabelSequence.setText(String.valueOf(mStepIndex));
        stepPostLabelSequence.setText(String.valueOf(stepList.size() - 1));
        imageMedia.setImageResource(R.drawable.place_holder);
        stepTitle.setText(fixedShortDescription);
        stepDescription.setText(fixedDescription);

        return rootView;
    }

    private String fixShortDescription() {
        String rawDescription = mStep.getShortDescription();
        int lastCutPosition = rawDescription.lastIndexOf(".");

        if (lastCutPosition != (-1)) {
            return rawDescription.substring(0, lastCutPosition);
        }
        return rawDescription;
    }

    private String fixDescription() {
        String rawDescription = mStep.getDescription();
        int firstDotIndex = rawDescription.indexOf(".");
        int firstCutPosition = firstDotIndex + 2;
        return rawDescription.substring(firstCutPosition);
    }

    public void setStep(Step step) {
        mStep = step;
    }

    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }

    public void setStepIndex(int stepIndex) {
        mStepIndex = stepIndex;
    }
}
