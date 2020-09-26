package com.sim.localecomapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.material.button.MaterialButton;
import com.sim.localecomapp.network.ImageRequester;
import com.sim.localecomapp.network.ProductEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter used to show a simple grid of products.
 */
public class ProductCardRecyclerViewAdapter extends RecyclerView.Adapter<ProductCardRecyclerViewAdapter.ProductCardViewHolder> {

    private List<ProductEntry> productList;
    private ImageRequester imageRequester;
    private OnItemClickListener mListener;

    ProductCardRecyclerViewAdapter(List<ProductEntry> productList) {
        this.productList = productList;
        imageRequester = ImageRequester.getInstance();
    }

    public interface OnItemClickListener {
        void onItemClick(int postion);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ecom_product_card, parent, false);
        return new ProductCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCardViewHolder holder, int position) {

        if (productList != null && position < productList.size()) {
            ProductEntry product = productList.get(position);
            holder.productTitle.setText(product.title);
            holder.productPrice.setText(product.price);
            imageRequester.setImageFromUrl(holder.productImage, product.url);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductCardViewHolder extends RecyclerView.ViewHolder {

        public NetworkImageView productImage;
        public TextView productTitle;
        public TextView productPrice;
        public MaterialButton order_button;

        public ProductCardViewHolder(@NonNull View itemView) {
            super(itemView);
            //TODO: Find and store views from itemView
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.product_price);
            order_button=itemView.findViewById(R.id.order_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(pos);
                        }
                    }
                }
            });

            order_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number=view.getContext().getString(R.string.whatsapp_number);
                    String message="Salam alicom,\nplease I want *"+productTitle.getText().toString()+"*, Price :"+productPrice.getText().toString();
                    String url = "https://api.whatsapp.com/send?phone="+number+"&text="+message;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    Context context = view.getContext();
                    context.startActivity(i);
                }
            });

        }
    }
}
