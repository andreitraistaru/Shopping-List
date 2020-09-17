package com.shoppinglist.activities.chooseListActivity;

import android.content.Context;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppinglist.Constants;
import com.shoppinglist.R;
import com.shoppinglist.activities.editListActivity.EditListActivity;
import com.shoppinglist.database.AppDatabase;
import com.shoppinglist.database.ShoppingList;

import java.util.List;

public class ListNameItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static class ListNameItemViewHolder extends RecyclerView.ViewHolder {
        private TextView listName;
        private TextView shop;
        private TextView info;
        private CardView cardView;

        public ListNameItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.listName = itemView.findViewById(R.id.listName_activity_choose_list_list_entry);
            this.shop = itemView.findViewById(R.id.shop_activity_choose_list_list_entry);
            this.info = itemView.findViewById(R.id.info_activity_choose_list_list_entry);
            this.cardView = itemView.findViewById(R.id.cardView_activity_choose_list_list_entry);
        }

        public CardView getCardView() {
            return cardView;
        }

        public void setListName(String data) {
            this.listName.setText(data);
        }
        public void setShop(String data) {
            this.shop.setText(data);
        }
        public void setInfo(String data) {
            this.info.setText(data);
        }
    }

    private List<ShoppingList> shoppingLists = null;
    private Context context;

    public ListNameItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListNameItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_choose_list_list_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final ShoppingList entry = shoppingLists.get(position);

        ((ListNameItemViewHolder) holder).setListName(entry.getListName());
        ((ListNameItemViewHolder) holder).setShop(context.getString(R.string.shop_field_shopping_list_item, entry.getShopName().isEmpty() ? "-" : entry.getShopName()));
        ((ListNameItemViewHolder) holder).setInfo(context.getString(R.string.info_field_shopping_list_item, entry.getOtherInfo().isEmpty() ? "-" : entry.getOtherInfo()));

        ((ListNameItemViewHolder) holder).getCardView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Context popupContext = new ContextThemeWrapper(context, R.style.popupMenu);
                PopupMenu popupMenu = new PopupMenu(popupContext, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_choose_shopping_list, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.delete_popup_shopping_list_option) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    AppDatabase.getInstance(context).getAppDatabaseDao().deleteItem(entry);
                                }
                            }).start();
                        }

                        return true;
                    }
                });

                popupMenu.show();

                return true;
            }
        });

        ((ListNameItemViewHolder) holder).getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditListActivity.class);
                intent.putExtra(Constants.selectedShoppingListBundleKey, entry.getId());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (shoppingLists == null) {
            return 0;
        }

        return shoppingLists.size();
    }

    public void setShoppingLists(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;

        notifyDataSetChanged();
    }
}
