package mguell.sample_tmbd.ui.view;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guell.mauricio.sample_tmbd.R;
import mguell.sample_tmbd.model.Movie;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Adapter for managing the movies information that will be displayed to the user.
 *
 * Created by Mauricio on 19/05/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private final List<Movie> mMovies;

    private final String TAG = getClass().getSimpleName();

    private MoviesAdapter(final List<Movie> movies) {
        this.mMovies = movies;
    }

    public MoviesAdapter() {
        this(new ArrayList<Movie>(0));
    }

    public void addMovies(final List<Movie> movies) {
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public void replaceMovies(final List<Movie> movies) {
        mMovies.clear();
        addMovies(movies);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        final Movie movie = mMovies.get(position);
        holder.titleAndYear.setText(
                String.format("%s, %s",
                        movie.getTitle(),
                        movie.getYear()));
        if (movie.getOverview().isEmpty()) {
            holder.overview.setText(holder.noOverviewAvailableText);
        } else {
            holder.overview.setText(movie.getOverview());
        }
        final Transformation transformation = new RoundedCornersTransformation(
                holder.posterCornerRadius, holder.posterCornerMargin);
        if (movie.getPosterURL().isEmpty()) {
            Log.d(TAG, String.format("%s: %s", "Image not found for", movie.getTitle()));
            holder.poster.setImageDrawable(holder.noImageIc);
        } else {
            Picasso.with(holder.poster.getContext())
                    .load(mMovies.get(position).getPosterURL())
                    .transform(transformation)
                    .into(holder.poster);
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindDimen(R.dimen.poster_corner_radius)
        int posterCornerRadius;

        @BindDimen(R.dimen.poster_corner_margin)
        int posterCornerMargin;

        @BindDrawable(R.drawable.ic_no_image)
        Drawable noImageIc;

        @BindString(R.string.no_overview_available)
        String noOverviewAvailableText;

        @BindView(R.id.card_movie_title_and_year)
        TextView titleAndYear;
        @BindView(R.id.card_movie_overview)
        TextView overview;
        @BindView(R.id.card_movie_poster)
        ImageView poster;

        MovieViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
