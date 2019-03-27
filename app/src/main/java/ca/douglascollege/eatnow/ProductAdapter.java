package ca.douglascollege.eatnow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.douglascollege.eatnow.database.product.Product;
import ca.douglascollege.eatnow.utilities.Helper;


class ProductAdapter extends ArrayAdapter<Product> {
    private Context mContext;
    private int mResource;

    public ProductAdapter(Context context) {
        super(context, R.layout.adapter_product_layout);
        mContext = context;
        mResource = R.layout.adapter_product_layout;
    }

    public void setProducts(List<Product> products) {
        addAll(products);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.imgImage);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtDescription = convertView.findViewById(R.id.txtDescription);
        TextView txtPrice = convertView.findViewById(R.id.txtTotalPrice);

        if (product.getImage() != null)
            imageView.setImageResource(mContext.getResources().getIdentifier(product.getImage(), "drawable", mContext.getPackageName()));
        txtName.setText(product.getName());
        txtDescription.setText(product.getDescription());
        txtPrice.setText(Helper.getCurrencyFormatted(product.getUnitPrice()));

        return convertView;
    }
}
