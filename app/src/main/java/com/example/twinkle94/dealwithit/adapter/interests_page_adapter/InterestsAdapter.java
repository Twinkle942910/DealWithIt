package com.example.twinkle94.dealwithit.adapter.interests_page_adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.events.Interest;
import com.example.twinkle94.dealwithit.interests_page.InterestsActivity;

import java.util.ArrayList;
import java.util.List;

public class InterestsAdapter extends BaseAdapter
{
    private static final int INTEREST = 0;
    private static final int INTEREST_HEADER = 1;

    private List<InterestItem> interests = new ArrayList<>();
    private Context context;

    public InterestsAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position)
    {
        return getItem(position).getType();
    }

    @Override
    public int getViewTypeCount()
    {
        return InterestItemType.values().length;
    }

    @Override
    public int getCount()
    {
        return interests.size();
    }

    @Override
    public InterestItem getItem(int position)
    {
        return interests.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return interests.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item type for this position
        int type = getItemViewType(position);

        // Check if an existing view is being reused, otherwise inflate the view
        InterestViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null)
        {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new InterestViewHolder();

            // Inflate XML layout based on the type
            convertView = getInflatedLayoutForType(type, parent);

            initViews(type, convertView, viewHolder);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }
        else
            {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (InterestViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        setViewValues(type, position, viewHolder);

        // Return the completed view to render on screen
        return convertView;
    }

    public void add(InterestItem interest)
    {
        interests.add(interest);
    }

    public void add(int position, InterestItem interest)
    {
        interests.add(position, interest);
    }

    public int headerPosition(String importance)
    {
        for (InterestItem interest : interests)
        {
            if (interest instanceof InterestHeader)
            {
                if(((InterestHeader)interest).toString().equals(importance))
                    return interests.indexOf(interest);
            }
        }
        return -1;
    }

    public int itemPosition(InterestItem interestItem)
    {
        return interests.indexOf(interestItem);
    }

    public void remove(int position)
    {
        interests.remove(position);
    }

    public void updateAll()
    {
        notifyDataSetChanged();
    }

    private View getInflatedLayoutForType(int type, ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // LayoutInflater inflater = LayoutInflater.from(context);// Or like this?
        View inflated_view = null;

        switch(type)
        {
            case INTEREST:
                inflated_view = layoutInflater.inflate(R.layout.interests_list_item, parent, false);
                break;

            case INTEREST_HEADER:
                inflated_view = layoutInflater.inflate(R.layout.interest_list_header, parent, false);
                break;
        }

        return inflated_view;
    }

    private void initViews(int type, View convertView, InterestViewHolder viewHolder)
    {
        switch(type)
        {
            case INTEREST:
                viewHolder.interest_color_v =  convertView.findViewById(R.id.interest_color);
                viewHolder.interest_title_tv =  (TextView) convertView.findViewById(R.id.interest_title);
                viewHolder.interest_importance_tv =  (TextView) convertView.findViewById(R.id.interest_importance);
                viewHolder.interest_menu_iv =  (ImageView) convertView.findViewById(R.id.interest_menu);
                break;

            case INTEREST_HEADER:
                viewHolder.header_text_tv = (TextView) convertView.findViewById(R.id.interest_header_title);
                break;
        }
    }

    private void setViewValues(int type, final int position, InterestViewHolder viewHolder)
    {
        // Get the data item for this position
        InterestItem interestItem = getItem(position);

        switch(type)
        {
            case INTEREST:
                int interest_importance = ((Interest)interestItem).getValue();
                int interest_color;

                if(interest_importance >= 60) interest_color = ContextCompat.getColor(context, R.color.colorInterestsImportant);
                else  interest_color = ContextCompat.getColor(context, R.color.colorInterestsNotImportant);

                viewHolder.interest_color_v.setBackgroundColor(interest_color);
                viewHolder.interest_title_tv.setText(((Interest)interestItem).getTitle());
                viewHolder.interest_importance_tv.setText(Integer.toString(interest_importance) + "%");
                viewHolder.interest_menu_iv.setColorFilter(interest_color);

                viewHolder.interest_menu_iv.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        showPopup(view, position);
                    }
                });
                break;

            case INTEREST_HEADER:
                viewHolder.header_text_tv.setText(interestItem.toString());
                break;
        }
    }

    private void showPopup(View v, final int interest_position)
    {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_interests_item, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.edit:
                        ((InterestsActivity)context).editInterestDialog(interest_position);
                        return true;
                    case R.id.delete:
                        ((InterestsActivity)context).deleteInterestDialog(interest_position);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    private static class InterestViewHolder
    {
        View interest_color_v;
        TextView interest_title_tv;
        TextView interest_importance_tv;
        ImageView interest_menu_iv;

        //Header text
        TextView header_text_tv;
    }
}
