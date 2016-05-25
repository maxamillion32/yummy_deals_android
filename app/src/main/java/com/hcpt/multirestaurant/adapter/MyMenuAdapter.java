package com.hcpt.multirestaurant.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.hcpt.multirestaurant.R;
import com.hcpt.multirestaurant.activity.MyMenuActivity;
import com.hcpt.multirestaurant.activity.MyMenuActivity.MenuListener;
import com.hcpt.multirestaurant.object.Menu;

public class MyMenuAdapter extends BaseAdapter {
	private MyMenuActivity context;
	private ArrayList<Menu> arrMenu;
	private MenuListener listener;
	private static LayoutInflater inflater = null;
	private AQuery aq;

	public MyMenuAdapter(Activity mcontext, ArrayList<Menu> arrMenu,
			MenuListener listener) {
		context = (MyMenuActivity) mcontext;
		this.arrMenu = arrMenu;
		this.listener = listener;
		inflater = (LayoutInflater) mcontext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		aq = new AQuery(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrMenu.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Hoder holder;
		if (convertView == null) {
			holder = new Hoder();
			convertView = inflater.inflate(R.layout.row_list_my_food_2, null);
			holder.imgFood = (ImageView) convertView.findViewById(R.id.imgFood);
			holder.progress = (ProgressBar) convertView
					.findViewById(R.id.progess);
			holder.lblFoodName = (TextView) convertView
					.findViewById(R.id.lblFoodName);
			holder.lblPrice = (TextView) convertView
					.findViewById(R.id.lblPrice);

			holder.lblTotal = (TextView) convertView
					.findViewById(R.id.lblTotalPrice);
			holder.lblNumber = (TextView) convertView
					.findViewById(R.id.lblNumberFood);
			holder.btnAdd = (ImageView) convertView
					.findViewById(R.id.btnAddNumber);
			holder.btnSubtract = (ImageView) convertView
					.findViewById(R.id.btnSubtractNumber);
			holder.btnDelete = (ImageView) convertView
					.findViewById(R.id.btnDelete);
			convertView.setTag(holder);

		} else {
			holder = (Hoder) convertView.getTag();
		}
		final Menu o = arrMenu.get(position);
		if (o != null) {
			double price = o.getPrice()
					- (o.getPrice() * o.getPercentDiscount() / 100);
			aq.id(holder.lblPrice).text("$" + String.format("%.1f", price));
			aq.id(holder.lblFoodName).text(o.getName());
			aq.id(holder.imgFood)
					.progress(holder.progress)
					.image(o.getImage(), true, true, 0,
							R.drawable.no_image_available_horizontal,
							new BitmapAjaxCallback() {
								@SuppressWarnings("deprecation")
								@Override
								public void callback(String url, ImageView iv,
										Bitmap bm, AjaxStatus status) {

									Drawable d = new BitmapDrawable(context
											.getResources(), bm);
									holder.imgFood.setBackgroundDrawable(d);
								}
							});

			if (o.getOrderNumber() == 1) {
				aq.id(holder.lblNumber).text(
						String.format("%02d", o.getOrderNumber()) + " item");
			} else {
				aq.id(holder.lblNumber).text(
						String.format("%02d", o.getOrderNumber()) + " items");
			}

			aq.id(holder.lblTotal).text(
					"$" + String.format("%.1f", o.getOrderNumber() * price));

			holder.btnDelete.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					listener.deleteItem(position);
				}
			});

			holder.btnAdd.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					listener.addQuantity(position, o.getOrderNumber() + 1);
				}
			});
			holder.btnSubtract.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					if (o.getOrderNumber() > 1)
						listener.deleteQuantity(position,
								o.getOrderNumber() - 1);
				}
			});

		}
		return convertView;
	}

	static class Hoder {
		ImageView imgFood, btnAdd, btnSubtract, btnDelete;
		ProgressBar progress;
		TextView lblFoodName, lblTotal, lblPrice, lblNumber;

	}

}
