package ca.douglascollege.eatnow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ca.douglascollege.eatnow.database.orderDetail.OrderDetail;
import ca.douglascollege.eatnow.database.product.Product;
import ca.douglascollege.eatnow.database.product.ProductRepository;
import ca.douglascollege.eatnow.utilities.Helper;


class OrderDetailAdapter extends ArrayAdapter<OrderDetail> {
    private OrderView mContext;
    private HashMap<Integer, Product> productsHashMap;
    private int mResource;

    public OrderDetailAdapter(Context context) {
        super(context, R.layout.adapter_order_detail_layout);
        mContext = (OrderView) context;
        mResource = R.layout.adapter_order_detail_layout;
        productsHashMap = new HashMap<>();
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        getProducts(orderDetails);
        clear();
        addAll(orderDetails);
        notifyDataSetChanged();
    }

    public void getProducts(List<OrderDetail> orderDetails) {
        ProductRepository productRepository = new ProductRepository(mContext.getApplicationContext());
        Product product;
        for (OrderDetail orderDetail : orderDetails) {
            if (!productsHashMap.containsKey(orderDetail.getProductId())) {
                product = productRepository.getProduct(orderDetail.getProductId());
                productsHashMap.put(product.getId(), product);
            }
        }
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        OrderDetail orderDetail = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView txtQuantity = convertView.findViewById(R.id.txtQuantity);
        TextView txtProduct = convertView.findViewById(R.id.txtProduct);
        TextView txtDescription = convertView.findViewById(R.id.txtDescription);
        TextView txtSpecialInstructions = convertView.findViewById(R.id.txtSpecialInstructions);
        TextView txtTotalPrice = convertView.findViewById(R.id.txtTotalPrice);
        ImageButton ibtnClear = convertView.findViewById(R.id.ibtnClear);

        txtQuantity.setText(Integer.toString(orderDetail.getQuantity()));
        if (orderDetail.getSpecialInstructions() == null || orderDetail.getSpecialInstructions().equals(""))
            Helper.hideComponentInConstraintLayout(txtSpecialInstructions);
        else
            txtSpecialInstructions.setText(mContext.getString(R.string.specialInstructionsFormat,orderDetail.getSpecialInstructions()));
        txtTotalPrice.setText(Helper.getCurrencyFormatted(orderDetail.getTotalPrice()));

        Product product = productsHashMap.get(orderDetail.getProductId());
        if (product != null) {
            txtProduct.setText(product.getName());
            if (product.getDescription() == null || product.getDescription().equals(""))
                Helper.hideComponentInConstraintLayout(txtDescription);
            else
                txtDescription.setText(mContext.getString(R.string.productDescriptionFormat, product.getDescription()));
        }

        ibtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.deleteOrderDetail(position);
            }
        });

        return convertView;
    }
}
