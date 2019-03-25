package ca.douglascollege.eatnow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import ca.douglascollege.eatnow.database.product.Product;


class ProductAdapter extends ArrayAdapter<Product> {
    private Context mContext;
    private int mResource;
    DecimalFormat decimalFormat;

    public ProductAdapter(Context context) {
        super(context, R.layout.adapter_product_layout);
        mContext = context;
        mResource = R.layout.adapter_product_layout;
        decimalFormat = new DecimalFormat("$ #,###.##");
    }

    public void setProducts(List<Product> products) {
        addAll(products);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

        String imageString = product.getImage();
        String name = product.getName();
        String description = product.getDescription();
        float unitPrice = product.getUnitPrice();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.imgImage);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtDescription = convertView.findViewById(R.id.txtDescription);
        TextView txtPrice = convertView.findViewById(R.id.txtPrice);

        if (imageString != null)
            imageView.setImageResource(mContext.getResources().getIdentifier(imageString, "drawable", mContext.getPackageName()));
        txtName.setText(name);
        txtDescription.setText(description);
        txtPrice.setText(decimalFormat.format(unitPrice));

        return convertView;
    }
}