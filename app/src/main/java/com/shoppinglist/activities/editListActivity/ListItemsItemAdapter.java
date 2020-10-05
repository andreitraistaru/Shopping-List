package com.shoppinglist.activities.editListActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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

import com.shoppinglist.R;
import com.shoppinglist.database.AppDatabase;
import com.shoppinglist.database.Item;
import com.shoppinglist.database.ShoppingList;

public class ListItemsItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static class ListItemsItemViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemQuantity;
        private TextView itemInfo;
        private CardView cardView;

        public ListItemsItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemName = itemView.findViewById(R.id.itemName_activity_edit_list_list_entry);
            this.itemQuantity = itemView.findViewById(R.id.quantity_activity_edit_list_list_entry);
            this.itemInfo = itemView.findViewById(R.id.info_activity_edit_list_list_entry);
            this.cardView = itemView.findViewById(R.id.cardView_activity_edit_list_list_entry);
        }

        public CardView getCardView() {
            return cardView;
        }

        public void setItemName(String data) {
            this.itemName.setText(data);
        }
        public void setItemQuantity(String data) {
            this.itemQuantity.setText(data);
        }
        public void setItemInfo(String data) {
            this.itemInfo.setText(data);
        }
    }

    private ShoppingList shoppingList = null;
    private Context context;

    public ListItemsItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListItemsItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_edit_list_item_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final Item entry = shoppingList.getItems().get(position);

        ((ListItemsItemViewHolder) holder).setItemName(entry.getName());
        ((ListItemsItemViewHolder) holder).setItemQuantity(context.getString(R.string.quantity_field_edit_list_item, entry.getQuantity().isEmpty() ? "-" : entry.getQuantity()));
        ((ListItemsItemViewHolder) holder).setItemInfo(context.getString(R.string.info_field_edit_list_item, entry.getOtherInformation().isEmpty() ? "-" : entry.getOtherInformation()));

        ((ListItemsItemViewHolder) holder).getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context popupContext = new ContextThemeWrapper(context, R.style.popupMenu);
                PopupMenu popupMenu = new PopupMenu(popupContext, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_edit_shopping_list, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.delete_popup_edit_shopping_list_option:
                                shoppingList.getItems().remove(entry);
                                notifyItemRemoved(position);
                                break;
                            case R.id.edit_popup_edit_shopping_list_option:
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                ViewGroup viewGroup = ((Activity) context).findViewById(R.id.content);
                                final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_item, viewGroup, false);

                                builder.setView(dialogView);
                                final AlertDialog alertDialog = builder.create();

                                ((TextView) dialogView.findViewById(R.id.name_dialog_add_item)).setText(entry.getName());
                                ((TextView) dialogView.findViewById(R.id.quantity_dialog_add_item)).setText(entry.getQuantity());
                                ((TextView) dialogView.findViewById(R.id.info_dialog_add_item)).setText(entry.getOtherInformation());
                                dialogView.findViewById(R.id.save_dialog_add_item).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        entry.setName(((TextView) alertDialog.findViewById(R.id.name_dialog_add_item)).getText().toString());
                                        entry.setQuantity(((TextView) alertDialog.findViewById(R.id.quantity_dialog_add_item)).getText().toString());
                                        entry.setOtherInformation(((TextView) alertDialog.findViewById(R.id.info_dialog_add_item)).getText().toString());

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                AppDatabase.getInstance(context).getAppDatabaseDao().updateList(shoppingList);
                                            }
                                        }).start();

                                        alertDialog.dismiss();
                                    }
                                });

                                alertDialog.show();

                                break;
                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (shoppingList == null || shoppingList.getItems() == null) {
            return 0;
        }

        return shoppingList.getItems().size();
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;

        notifyDataSetChanged();
    }
}
