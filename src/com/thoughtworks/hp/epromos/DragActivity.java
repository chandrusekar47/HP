package com.thoughtworks.hp.epromos;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtworks.hp.R;
import com.thoughtworks.hp.datastore.ShoppingListTable;
import com.thoughtworks.hp.models.ShoppingList;


@SuppressLint({ "ParserError", "ParserError" })
public class DragActivity extends Activity implements View.OnLongClickListener,
		View.OnClickListener, View.OnTouchListener {

	private static final int HIDE_TRASHCAN_MENU_ID = Menu.FIRST;
	private static final int SHOW_TRASHCAN_MENU_ID = Menu.FIRST + 1;
	private static final int ADD_OBJECT_MENU_ID = Menu.FIRST + 2;
	private static final int CHANGE_TOUCH_MODE_MENU_ID = Menu.FIRST + 3;

	private DragController mDragController;
	private DragLayer mDragLayer;
	private DeleteZone mDeleteZone;

	private boolean mLongClickStartsDrag = true;

	public static final boolean Debugging = false;

	public void onClick(View v) {
		if (mLongClickStartsDrag) {
			toast("Press and hold to drag an image.");
		}
	}

	@SuppressWarnings("deprecation")
	private void realizeShoppingLists(LinearLayout layoutToAddComponents) {
		ShoppingListTable databaseInstance = new ShoppingListTable();
		List<ShoppingList> shoppingLists = databaseInstance.findAll();
		int loopVar = 0;
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 300);
		layoutParams.setMargins(24, 10, 24, 10);
		for (ShoppingList list : shoppingLists) {
			LinearLayout tempInstance = new LinearLayout(this);
			tempInstance.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT));
			tempInstance.setOrientation(LinearLayout.VERTICAL);
			ShoppingBasket shoppingCart = new ShoppingBasket(this);
			shoppingCart.setImageDrawable(getResources().getDrawable(R.drawable.list));
			TextView textView = new TextView(this);
			textView.setText(list.getName());
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
			tempInstance.addView(shoppingCart, 0);
			tempInstance.addView(textView, 1);
			layoutToAddComponents.addView(tempInstance, loopVar++, layoutParams);
		}

	}

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.demo);

		TableLayout tableView = (TableLayout) findViewById(R.id.image_grid_view);
		LinearLayout shoppingListsGrid = (LinearLayout) findViewById(R.id.bottom_part);
		if (shoppingListsGrid == null)
			toast("Unable to find Shopping lists");
		else {
			realizeShoppingLists(shoppingListsGrid);
		}

		if (tableView == null)
			toast("Unable to find GridView");
		else {
			new PromoRealizer(this, tableView).realizePromotions();
			tableView.getLayoutParams().width = getWindowManager().getDefaultDisplay().getWidth();
			ScrollView mainScroll = (ScrollView)findViewById(R.id.list_scroll);
			mainScroll.getLayoutParams().height = getWindowManager().getDefaultDisplay().getHeight() - 200;
		}

		mDragController = new DragController(this);
		mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
		mDragLayer.setDragController(mDragController);
		mDragLayer.setTableView(tableView);
		mDragLayer.shoppingCarts = shoppingListsGrid;

		mDragController.setDragListener(mDragLayer);

		Toast.makeText(getApplicationContext(), getResources().getString(R.string.instructions),
				Toast.LENGTH_LONG).show();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(0, HIDE_TRASHCAN_MENU_ID, 0, "Hide Trashcan").setShortcut('1', 'c');
		menu.add(0, SHOW_TRASHCAN_MENU_ID, 0, "Show Trashcan").setShortcut('2', 'c');
		menu.add(0, ADD_OBJECT_MENU_ID, 0, "Add View").setShortcut('9', 'z');
		menu.add(0, CHANGE_TOUCH_MODE_MENU_ID, 0, "Change Touch Mode");

		return true;
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		ImageCell i = (ImageCell) v;
		trace("onItemClick in view: " + i.mCellNumber);
	}

	public boolean onLongClick(View v) {
		if (mLongClickStartsDrag) {

			if (!v.isInTouchMode()) {
				toast("isInTouchMode returned false. Try touching the view again.");
				return false;
			}
			return startDrag(v);
		}

		return false;
	}


	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case HIDE_TRASHCAN_MENU_ID:
			if (mDeleteZone != null)
				mDeleteZone.setVisibility(View.INVISIBLE);
			return true;
		case SHOW_TRASHCAN_MENU_ID:
			if (mDeleteZone != null)
				mDeleteZone.setVisibility(View.VISIBLE);
			return true;
		case CHANGE_TOUCH_MODE_MENU_ID:
			mLongClickStartsDrag = !mLongClickStartsDrag;
			String message = mLongClickStartsDrag ? "Changed touch mode. Drag now starts on long touch (click)."
					: "Changed touch mode. Drag now starts on touch (click).";
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public boolean onTouch(View v, MotionEvent ev) {
		if (mLongClickStartsDrag)
			return false;

		boolean handledHere = false;

		final int action = ev.getAction();

		if (action == MotionEvent.ACTION_DOWN) {
			handledHere = startDrag(v);
		}

		return handledHere;
	}


	public boolean startDrag(View v) {
		DragSource dragSource = (DragSource) v;

		mDragController.startDrag(v, dragSource, dragSource, DragController.DRAG_ACTION_MOVE);

		return true;
	}


	public void toast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	} 



	public void trace(String msg) {
		Log.d("DragActivity", msg);
		if (!Debugging)
			return;
	}

} 
