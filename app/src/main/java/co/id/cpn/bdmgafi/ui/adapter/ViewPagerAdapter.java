package co.id.cpn.bdmgafi.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import co.id.cpn.bdmgafi.R;
import co.id.cpn.entity.News;
import co.id.cpn.entity.WebScrap;

public class ViewPagerAdapter extends PagerAdapter {

    private List<News> news;
    private Context context;
    private static ClickListener clickListener;

    public ViewPagerAdapter(List<News> meals, Context context) {
        this.news = meals;
        this.context = context;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ViewPagerAdapter.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_view_pager,
                container,
                false
        );

        MaterialCardView row = view.findViewById(R.id.row);
        ImageView thumb = view.findViewById(R.id.mealThumb);
        TextView title = view.findViewById(R.id.mealName);
        TextView date = view.findViewById(R.id.dateText);

//        String strThumb = news.get(position).POST_IMAGE_URL;

        if (position % 2 == 0) {
            thumb.setImageDrawable(this.context.getDrawable(R.drawable.bg_tile_banner_white));
        } else {
            thumb.setImageDrawable(this.context.getDrawable(R.drawable.bg_tile_banner_gray));
        }

        String strTitle = news.get(position).getTitle();
        String strDate = news.get(position).getDate();
        title.setText(strTitle);
        date.setText(strDate);

        row.setOnClickListener(v -> clickListener.onClick(v, position));

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public interface ClickListener {
        void onClick(View v, int position);
    }
}
