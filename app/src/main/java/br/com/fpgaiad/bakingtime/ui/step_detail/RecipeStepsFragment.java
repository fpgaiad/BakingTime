package br.com.fpgaiad.bakingtime.ui.step_detail;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import br.com.fpgaiad.bakingtime.R;
import br.com.fpgaiad.bakingtime.entities.Recipe;
import br.com.fpgaiad.bakingtime.entities.Step;
import br.com.fpgaiad.bakingtime.ui.recipe_detail.activity.RecipeDetailActivity;

public class RecipeStepsFragment extends Fragment {

    public static final String RECIPE = "recipe";
    public static final String STEP = "step";
    public static final String STEP_INDEX = "stepIndex";
    public Step mStep;
    public Recipe mRecipe;
    public int mStepIndex;
    boolean isUrlEmpty;
    SimpleExoPlayer mExoPlayer;
    SimpleExoPlayerView mPlayerView;


    public static RecipeStepsFragment newInstance(Recipe recipe, Step step, int stepIndex) {
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putParcelable(RECIPE, recipe);
        args.putSerializable(STEP, step);
        args.putInt(STEP_INDEX, stepIndex);
        recipeStepsFragment.setArguments(args);

        return recipeStepsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(RECIPE);
            mStep = (Step) getArguments().getSerializable(STEP);
            mStepIndex = getArguments().getInt(STEP_INDEX);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater
                .inflate(R.layout.fragment_recipe_steps, container, false);

        ImageView imageMedia = rootView.findViewById(R.id.iv_media);
        mPlayerView = rootView.findViewById(R.id.player_view);
        TextView stepTitle = rootView.findViewById(R.id.tv_step_title_sequence);
        TextView stepPreLabelSequence = rootView.findViewById(R.id.tv_step_pre_label_sequence);
        TextView stepPostLabelSequence = rootView.findViewById(R.id.tv_step_post_label_sequence);
        TextView stepDescription = rootView.findViewById(R.id.tv_step_description_sequence);
        TextView divisor = rootView.findViewById(R.id.tv_step_divisor_label_sequence);
        View linearLayoutButton = rootView.findViewById(R.id.linear_layout_button);
        Button nextButtonSolo = rootView.findViewById(R.id.btn_next_solo);
        Button prevButtonSolo = rootView.findViewById(R.id.btn_prev_solo);

        //Getting the list of steps to define its total length
        List<Step> stepList = mRecipe.getSteps();
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

        //Verify if there is an url for the current step
        isUrlEmpty = (mStep.getVideoURL().equals(""));
        if (isUrlEmpty) {
            imageMedia.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
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

//        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.place_holder));
        initializePlayer(Uri.parse(mStep.getVideoURL()));

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null && !isUrlEmpty) {
            //Create an instance of Exoplayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            //Prepare the MediaSource
            String userAgent = Util.getUserAgent(getContext(), "Baking Time");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private String fixShortDescription() {
        String rawDescription = mStep.getShortDescription();
        int lastCutPosition = rawDescription.lastIndexOf(".");

        if (lastCutPosition != (-1)) {
            return rawDescription.substring(0, lastCutPosition);
        }
        return rawDescription;
    }

    @NonNull
    private String fixDescription() {
        String rawDescription = mStep.getDescription();
        int firstDotIndex = rawDescription.indexOf(".");
        int firstCutPosition = firstDotIndex + 2;
        return rawDescription.substring(firstCutPosition);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.current_recipe_key), mRecipe);
        outState.putSerializable(getString(R.string.current_step_key), mStep);
        outState.putInt(getString(R.string.current_step_index_key), mStepIndex);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}
